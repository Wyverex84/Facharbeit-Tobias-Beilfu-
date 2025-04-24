package packages.neuralNetwork;

/**
 * Used to store the connected neurons in a flexible way.
 */
import java.util.ArrayList;

/**
 * The Class Neuron represents a neuron in the neural network.
 * It contains a bias that can be changed in the allowed range,
 * the neurons connected to it and the connections that contain
 * the weight for each neuron.
 */
public class Neuron{
    /**
     * The maximum absolute value of any neurons bias
     */
    static final double MAX_BIAS = 10;

    /**
     * The value of the bias of the neuron. It may also represent
     * the input if the neuron is in the input layer of the
     * network.
     */
    private double bias;

    /**
     * Store the neurons that this neuron is connected to. last
     * contains the neurons that are in a previous layer of the
     * network, next stores the neurons in the following layers.
     */
    private ArrayList<Neuron> last, next;

    /**
     * A list of the weights for all connections. The neuron only
     * stores the weights for the connections to last.
     */
    private ArrayList<Connection> weight;

    /**
     * Creates a new neuron with a random bias in the allowed
     * range and no connections. The bias may be changed afterwards
     * using setBias(double value).
     */
    public Neuron(){
        last = new ArrayList<Neuron>();
        next = new ArrayList<Neuron>();
        weight = new ArrayList<Connection>();
        bias = 0;
        change(1);
    }

    /**
     * If the neuron is in the input layer, it will return its bias.
     * If the neuron is in the output layer, it will return the
     * average of all its inputs multiplied by their weight which
     * will be a value between 0 and 10.
     * In any other case the neuron will return the result of
     * activation(double value) with its input average as input.
     * 
     * @return The output of this neuron
     */
    public double getResult(){
        if(last.size()==0){
            return bias;
        }
        double value = 0;
        for(int i=0; i<last.size(); i++)value+=weight.get(i).getWeight() * last.get(i).getBias();
        if(next.size()==0)return value / last.size();
        return activation(value - bias);
    }

    /**
     * Changes the neurons bias to a ranndom value in the allowed range
     * with the maximum difference to the old bias of the percentage
     * that is the input. To be able to reach the  opposite value you
     * might have to enter '2'.
     * 
     * @param percent The range of the change (1 means full range from
     * value 0)
     * @return The neuron itself
     */
    public Neuron change(double percent){
        setBias(getBias() + (Math.random() * 2 - 1) * percent * MAX_BIAS);
        for (Connection c : weight) {
            c.change(percent);
        }
        return this;
    }

    /**
     * Sets the bias to the given value and checks weather the new value
     * is within the allowed range using checkValue().
     * 
     * @param value The new bias
     * @return The neuron itself
     */
    public Neuron setBias(double value){
        bias = value;
        checkValue();
        return this;
    }

    /**
     * Sets the bias to the given value and does not check weather it is
     * within the allowed range. This is useful to set it to input values
     * ousside the allowed bias range.
     * 
     * @param value The new bias
     * @return The neuron itself
     */
    public Neuron setBiasAsInput(double value){
        bias = value;
        return this;
    }

    /**
     * Returns the bias of the neuron.
     * 
     * @return The bias of the neuron
     */
    public double getBias(){
        return bias;
    }

    /**
     * Checks weather the bias value that is set right now is
     * valid and corrects it if necessary.
     * 
     * @return Weather the old value was in the valid range
     */
    public boolean checkValue(){
        if(bias>MAX_BIAS){
            bias = MAX_BIAS;
            return false;
        }
        else if(bias<-MAX_BIAS){
            bias = -MAX_BIAS;
            return false;
        }
        return true;
    }

    /**
     * Adds the given neuron to the last list and creates a new
     * connection with a random weight for it. It also adds
     * itself to the given neurons next list.
     * 
     * @param neuron The neuron to add
     * @return The neuron itself
     */
    public Neuron addLast(Neuron neuron){
        if(neuron!=null){
            last.add(neuron);
            weight.add(new Connection());
            neuron.next.add(this);
        }
        return this;
    }

    /**
     * Adds the given neurons to the last list and creates new
     * connections with a random weightw for them. It also adds
     * itself to the given neurons next lists.
     * 
     * You can set this so it does not create new weights so
     * they can be added in a custom way.
     * 
     * @param neuron The neurons to add
     * @param addWeight If there should be created weights for
     * the neurons
     * @return The neuron itself
     */
    public Neuron addLast(ArrayList<Neuron> neuron, boolean addWeight){
        if(neuron!=null){
            last.addAll(neuron);
            for (Neuron n : neuron) {
                n.next.add(this);
            }
            if(addWeight)for(int i=0; i<neuron.size(); i++)weight.add(new Connection());
        }
        return this;
    }

    //TODO: fix
    /**
     * Adds the given neuron to the next list. It also adds
     * itself to the given neurons last list.
     * 
     * @param neuron The neuron to add
     * @return The neuron itself
     */
    public Neuron addNext(Neuron neuron){
        if(neuron!=null){
            next.add(neuron);
            neuron.last.add(this);
        }
        return this;
    }

    /**
     * Adds the givem connection with its weight to the
     * connection list.
     * 
     * @param connection The new connection
     * @return The neuron itself
     */
    public Neuron addWeight(Connection connection){
        weight.add(connection);
        return this;
    }

    /**
     * Adds the givem connections with their weights to the
     * connection list.
     * 
     * @param connection The new connections
     * @return The neuron itself
     */
    public Neuron addWeight(ArrayList<Connection> connection){
        weight.addAll(connection);
        return this;
    }

    /**
     * Removes the given neuron from the last list, if contained.
     * 
     * @param neuron The neuron to remove
     * @return The neuron itself
     */
    public Neuron removeLast(Neuron neuron){
        weight.remove(last.indexOf(neuron));
        last.remove(neuron);
        return this;
    }

    /**
     * Removes the given neuron from the next list, if contained.
     * 
     * @param neuron The neuron to remove
     * @return The neuron itself
     */
    public Neuron removeNext(Neuron neuron){
        next.remove(neuron);
        return this;
    }

    /**
     * Returns the last list.
     * 
     * @return last
     */
    public ArrayList<Neuron> getLast(){
        return last;
    }

    /**
     * Returns the next list.
     * 
     * @return next
     */
    public ArrayList<Neuron> getNext(){
        return next;
    }

    /**
     * Returns a list of all connection objects.
     * 
     * @return weight list
     */
    public ArrayList<Connection> getWeights(){
        return weight;
    }

    /**
     * Returns a new Neuron with the same bias and weights as
     * this neuron. The connections to the other neurons have
     * to be created afterwards.
     * 
     * @return The new neuron
     */
    public Neuron clone(){
        return new Neuron().setBias(getBias()).addWeight(getWeights());
    }


    /**
     * The activation function used for the hidden layer nodes.
     * Contains multiple options to use and may be edited.
     * 
     * @param value The value of the neuron
     * @return The output of the function
     */
    public static double activation(double value){
        //if(value < 0)return 0;
        //return value;
        return 2 / (1 + Math.exp(-value)) - 1;
        //return 1 / (1 + Math.exp(-value));
    }
}