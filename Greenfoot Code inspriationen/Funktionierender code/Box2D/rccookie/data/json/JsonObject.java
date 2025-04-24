package rccookie.data.json;

import static rccookie.data.json.Json.*;

import java.util.HashMap;
import java.util.Map;

import rccookie.data.Saveable;

public class JsonObject extends HashMap<String, Object> {
    private static final long serialVersionUID = -67871725067261693L;

    public JsonObject() {}

    /**
     * @param data Expected type: {@code Map} of any type (first type will be concerted into string using {@code toString()})
     */
    public JsonObject(Object data) {
        this((Map<?, ?>)data);
    }

    /**
     * Creates a new json object from the given map. The keys will be converted to strings using {@code toString()}.
     * 
     * @param data The data map for the json object
     */
    public JsonObject(Map<?,?> data) {

        if(data == null) return;
        for(Map.Entry<?,?> dataPair : data.entrySet()) {
            put(dataPair.getKey().toString(), dataPair.getValue());
        }
    }




    @Override
    public String toString() {

        if(isEmpty()) return "{}";

        StringBuilder string = new StringBuilder();
        string.append('{');
        for(Map.Entry<String, Object> pair : entrySet()) {
            string.append('"').append(pair.getKey()).append('"');
            string.append(':');
            string.append(stringOf(pair.getValue()));
            string.append(',');
        }
        if(string.length() > 1) string.deleteCharAt(string.length() - 1);
        string.append('}');
        return string.toString();
    }


    private static final String stringOf(Object value) {
        if(value == null) return null;
        if(value instanceof String) {
            StringBuilder string = new StringBuilder().append('"');
            StringBuilder valueString = new StringBuilder((String)value);
            for(int i=0; i<valueString.length(); i++) {
                if(valueString.charAt(i) == '\\' || valueString.charAt(i) == '\"') string.append("\\");
                string.append(valueString.charAt(i));
            }
            string.append('"');
            return string.toString();
        }
        if(value instanceof Object[]) {
            if(((Object[])value).length == 0) return "[]";
            StringBuilder string = new StringBuilder().append('[');
            for(final Object element : (Object[])value) string.append(stringOf(element)).append(',');
            string.deleteCharAt(string.length() - 1).append(']');
            return string.toString();
        }
        return value.toString();
    }


    @Override
    public Object put(String varName, Object value) {

        validateName(varName.toString());
        validateType(value);

        if(value instanceof Saveable) {
            return super.put(varName.toString(), ((Saveable)value).getSaveData());
        }
        else if(value instanceof Map<?,?>) {
            return super.put(varName.toString(), new JsonObject(value));
        }
        else return super.put(varName.toString(), value);
    }


    @Override
    public Object putIfAbsent(String varName, Object value) {
        validateName(varName);
        validateType(value);
        return super.putIfAbsent(varName, value);
    }






    @Override
    public Object get(Object varName) {
        if(varName == null) return null;
        return super.get(varName);
    }



    public JsonObject getJson(String varName) {
        return (JsonObject)get(varName);
    }
    public Object[] getArray(String varName) {
        return (Object[])get(varName);
    }
    public boolean getBoolean(String varName) {
        return (boolean)get(varName);
    }
    public int getInt(String varName) {
        return (int)get(varName);
    }
    public long getLong(String varName) {
        return (long)get(varName);
    }
    public short getShort(String varName) {
        return (short)get(varName);
    }
    public float getFloat(String varName) {
        return (float)get(varName);
    }
    public double getDouble(String varName) {
        return (double)get(varName);
    }
    public String getString(String varName) {
        return (String)get(varName);
    }
}
