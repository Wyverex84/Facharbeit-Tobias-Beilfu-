package rccookie.data.json;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Map;
import java.util.NoSuchElementException;

import rccookie.data.RuntimeLoadable;
import rccookie.data.Saveable;

public final class Json {
    private Json() {}



    public static final void validateName(String name) {
        if(name == null) throw new NullPointerException("Name can't be null");
        if(name.length() == 0) throw new RuntimeException("Name has to contain at least one character");
    }

    public static final void validateType(Object value) {
        if(value == null) return;
        if(value instanceof Object[]) {
            for(Object o : (Object[])value) validateType(o);
            return;
        }
        if( value instanceof JsonObject ||
            value instanceof Saveable ||
            value instanceof Boolean ||
            value instanceof Integer ||
            value instanceof Long ||
            value instanceof Short ||
            value instanceof Float ||
            value instanceof Double ||
            value instanceof String
        ) return;

        if(value instanceof Map<?,?>) {
            try{
                Map<?,?> valueMap = (Map<?,?>)value;

                for(Object name : valueMap.keySet()) validateName(name.toString());
                for(Object mapValue : valueMap.values()) validateType(mapValue);

                return;
            }
            catch(Exception e) {}
        }

        throw new UnsupportedDataException(value);
    }




    public static final String save(Saveable object) {
        JsonObject data = new JsonObject(object.getSaveData());
        return save(data, object.getSaveDir(), object.getSaveName());
    }

    public static final String save(Map<String, Object> object, String path, String filename) {
        return save(new JsonObject(object), path, filename);
    }

    public static final String save(final JsonObject object, final String path, final String filename) {
        if(object == null) return null;
        File dir = new File(path);
        if(!dir.exists()) dir.mkdirs();
        final String jsonString = object.toString();
        final StringBuilder filePath = new StringBuilder(path);
        if(!filePath.toString().endsWith("\\")) filePath.append("\\");
        filePath.append(filename).append(".json");
        try {
            new Formatter(filePath.toString()).format("%s", jsonString).close();
            return jsonString;
        } catch (final Exception e) {
            System.out.println(e);
            return null;
        }
    }






    /**
     * Loads the data of the loadable into the given loadable.
     * 
     * @param object The object to load the data into
     * @return False only if the object could not be loaded
     */
    public static final boolean load(RuntimeLoadable object) {

        final StringBuilder filePath = new StringBuilder(object.getSaveDir());
        if(!filePath.toString().endsWith("\\")) filePath.append("\\");
        filePath.append(object.getSaveName()).append(".json");

        JsonObject saveData = read(filePath.toString());

        if(saveData == null) return false;

        object.load(saveData);
        return true;
    }

    /**
     * Reads the given json file into a json object.
     * 
     * @param filePath The file to read
     * @return The object represented in the given file, or {@code null}, if the file could not be read.
     */
    public static final synchronized JsonObject read(String filePath) {
        try{
            final String fileText = Files.readString(Path.of(filePath));
            //if(fileText.length() == 0) System.err.println("File is empty");
            return readNextObject(fileText);
        }
        catch(Exception e) {
            //System.err.println("An error occured during loading");
            //e.printStackTrace();
            return null;
        }
    }


    /**
     * Returns the next full object of the given json-formatted string.
     * 
     * @param jsonFileText A string containing at least the for the object relevant part of the json
     * @return The json object
     * @throws NoSuchElementException If the given object could not be read.
     */
    public static final JsonObject readNextObject(String jsonFileText) {
        return readNextObject(new StringBuilder(jsonFileText.replaceAll("[\\n\\t\\r ]", "")));
    }



    private static final void readAndAddNextPair(JsonObject object, StringBuilder remaining) {
        // get the variable name
        String varName = readNextString(remaining);

        // remove the colomn (:)
        remaining.deleteCharAt(0);

        // get the variable value
        Object value = readNextValue(remaining);

        // write pair into json object
        object.put(varName, value);
    }



    private static final boolean isNumChar(char c) {
        return (
            c == '0' ||
            c == '1' ||
            c == '2' ||
            c == '3' ||
            c == '4' ||
            c == '5' ||
            c == '6' ||
            c == '7' ||
            c == '8' ||
            c == '9' ||
            c == '-' ||
            c == '.' ||
            c == 'E'
        );
    }




    private static final Object readNextValue(StringBuilder remaining) {
        char start = remaining.charAt(0);

        if(start == '"') return readNextString(remaining);
        if(start == '[') return readNextArray(remaining);
        if(start == '{') return readNextObject(remaining);

        if(isNumChar(start)) return readNextNumber(remaining);

        if(start == 't') return readNextTrue(remaining);
        if(start == 'f') return readNextFalse(remaining);
        if(start == 'n') return readNextNull(remaining);

        throw new NoSuchElementException();
    }


    private static final JsonObject readNextObject(StringBuilder remaining) {
        final JsonObject object = new JsonObject();

        // Delete opening curly bracket ({)
        remaining.deleteCharAt(0);

        while(remaining.charAt(0) != '}') {

            readAndAddNextPair(object, remaining);

            // Delete the next comma, or end the object
            if(remaining.charAt(0) == ',') remaining.deleteCharAt(0);
            else break;
        }

        // Delete the opening curly bracket (})
        remaining.deleteCharAt(0);

        // Return the json object
        return object;
    }


    private static final Object[] readNextArray(StringBuilder remaining) {
        // Create a list to store the array's objects in
        final ArrayList<Object> array = new ArrayList<>();

        // Delete the opening bracket ([)
        remaining.deleteCharAt(0);

        // Add more objects if the array is not closing
        while(remaining.charAt(0) != ']') {

            // Read out the next value
            array.add(readNextValue(remaining));
            
            // Delete the next comma, or end the array
            if(remaining.charAt(0) == ',') remaining.deleteCharAt(0);
            else break;
        }

        // Delete the closing bracket (])
        remaining.deleteCharAt(0);

        // Return the list as an array
        return array.toArray();
    }


    private static final String readNextString(StringBuilder remaining) {
        StringBuilder string = new StringBuilder();
        remaining.deleteCharAt(0);
        while(remaining.charAt(0) != '"') {
            char next = remaining.charAt(0);
            remaining.deleteCharAt(0);
            if(next == '\\') {
                string.append(remaining.charAt(0));
                remaining.deleteCharAt(0);
            }
            else string.append(next);
        }
        remaining.deleteCharAt(0);
        return string.toString();
    }


    private static final Number readNextNumber(StringBuilder remaining) {
        StringBuilder number = new StringBuilder();
        while(isNumChar(remaining.charAt(0))) {
            number.append(remaining.charAt(0));
            remaining.deleteCharAt(0);
        }
        final String numberString = number.toString();
        try{
            if(numberString.contains(".")) return Double.parseDouble(numberString);
            return Long.parseLong(numberString);
        }
        catch(Exception e) {
            throw new NoSuchElementException();
        }
    }


    private static final boolean readNextTrue(StringBuilder remaining) {
        if(remaining.substring(0, 4).equals("true")) {
            remaining.delete(0, 4);
            return true;
        }
        throw new NoSuchElementException();
    }

    private static final boolean readNextFalse(StringBuilder remaining) {
        if(remaining.substring(0, 5).equals("false")) {
            remaining.delete(0, 5);
            return false;
        }
        throw new NoSuchElementException();
    }

    private static final Object readNextNull(StringBuilder remaining) {
        if(remaining.substring(0, 4).equals("null")) {
            remaining.delete(0, 4);
            return null;
        }
        throw new NoSuchElementException();
    }
}
