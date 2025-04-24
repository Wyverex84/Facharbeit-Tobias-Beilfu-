package packages.neuralNetwork;

/**
 * Symbolizes the weight between two neurons
 */
public class Connection {
    /**
     * The maximum absolute value of any connections weight
     */
    public static double MAX_WEIGHT = 10;


    /**
     * The weight of the connection for the next Neuron. This
     * is not the 'official' weight but a value between -2
     * and 2. If the value is greater than 1 or less than -1
     * the output will be scaled to fit the whole range.
     */
    private double weight;

    /**
     * Creates a Connection with a weight. The weight is
     * set randomly to a value the allowed range and can be
     * changed later.
     */
    public Connection(){
        setWeight(0);
        change(1);
    }

    /**
     * Changes the value of the weight. Percent determines the
     * absolue range of the change. To be able to reach the
     * opposite value you might have to enter '2'.
     * 
     * The propability of all possible values between -1 and 1
     * is just as high as all values outside of this range.
     * This allowes to have the same popability to have the
     * input scaled up as to have it scaled down.
     * 
     * @param percent The range of the change (1 means full range
     * from value 0)
     * @return The connection itself
     */
    public Connection change(double percent){
        setRealWeight(weight + (Math.random() * 2 - 1) * percent * 2);
        return this;
    }

    /**
     * Sets the weigth to the given value. Value will be corrected
     * to fit in the allowed weight range.
     * 
     * @param value The new weight
     * @return The connection itself
     */
    public Connection setWeight(double value){
        if(value>1)setRealWeight((value + (MAX_WEIGHT - 2)) / (MAX_WEIGHT - 1));
        else if(value<-1)setRealWeight((value - (MAX_WEIGHT - 2)) / (MAX_WEIGHT - 1));
        else setRealWeight(value);
        return this;
    }

    /**
     * Sets the saved weight to the given weight. Value will be
     * corrected to fit in the allowed saved weight range of 2 to -2.
     * 
     * @param value The new saved weight
     * @return The connection itself
     */
    public Connection setRealWeight(double value){
        weight = value;
        checkValue();
        return this;
    }

    /**
     * Returns the official weight of the connection. Will be in the
     * range defined by the maximum weight.
     * 
     * @return The weight of the connection
     */
    public double getWeight(){
        if(weight>1)return (weight - 1) * (MAX_WEIGHT - 1) + 1;
        else if(weight<-1)return (weight + 1) * (MAX_WEIGHT - 1) - 1;
        return weight;
    }

    /**
     * Checks weather the weight value that is set right now is
     * valid and corrects it if necessary.
     * 
     * @return Weather the old value was in the valid range
     */
    public boolean checkValue(){
        if(weight>2){
            weight = 2;
            return false;
        }
        else if(weight<-2){
            weight = -2;
            return false;
        }
        return true;
    }





    public static void main(String[] args) {
        for(int i=0; i<10; i++){
            Connection c = new Connection();
            System.out.println(c.getWeight() + " (" + c.weight + ")");
        }
    }
}