package packages.neuralNetwork;

/**
 * Used to store the neurons in a flexible way.
 */
import java.util.ArrayList;

/**
 * A class to host the connection between the Neurons
 * in a simple neural network.
 */
public abstract class Network {

    /**
     * A list of all neurons in this network. The outer ArrayList
     * contains all the layers, the inner ArrayLists contain the
     * actual neurons.
     */
    private ArrayList<ArrayList<Neuron>> layer;

    /**
     * Creates a new neural network with random weights and biases.
     * The parameters determine the layout and complexity of the 
     * network.
     * 
     * The number of neurons in each hidden layer is the average
     * number of neurons from the input an output layer.
     * 
     * @param inputNeurons The number of neurons in the first layer
     * (determines the number of inputs)
     * @param outputNeurons The number of neurons in the final layer
     * (determines the number of outputs)
     * @param hiddenLayers The number of hidden layers additionally
     * to the input and output layer
     */
    public Network(int inputNeurons, int outputNeurons, int hiddenLayers){
        layer = new ArrayList<ArrayList<Neuron>>(hiddenLayers + 2);
        for(int i=0; i<hiddenLayers + 2; i++){
            layer.add(new ArrayList<Neuron>());
        }
        for(int i=0; i<inputNeurons; i++){
            layer.get(0).add(new Neuron());
        }
        for(int i=1; i<hiddenLayers + 1; i++){
            for(int j=0; j<(inputNeurons + outputNeurons) / 2; j++){
                layer.get(i).add(new Neuron().addLast(layer.get(i - 1), true));
            }
        }
        for(int i=0; i<outputNeurons; i++){
            layer.get(layer.size() - 1).add(new Neuron().addLast(layer.get(layer.size() - 2), true));
        }
    }

    /**
     * Creates a new network from a given one by copying all setings,
     * weights and biases. This means that it will output the same
     * results with the same input.
     * 
     * This is very useful when creating a new generation from an old
     * best network. Simply create new ones from it using clone() and
     * change them with the change() method.
     * 
     * This means:
     * Network x;
     * x == clone(x);
     * will be false, and changes will only be made to one of them.
     * 
     * @param clone The network to create a copy from
     */
    public Network(Network clone){
        int inputNeurons = clone.getInputLayer().size();
        int outputNeurons = clone.getOutputLayer().size();
        int hiddenLayers = clone.getLayers().size() - 2;
        layer = new ArrayList<ArrayList<Neuron>>(hiddenLayers + 2);
        for(int i=0; i<hiddenLayers + 2; i++){
            layer.add(new ArrayList<Neuron>());
        }
        for(int i=0; i<inputNeurons; i++){
            layer.get(0).add(clone.layer.get(0).get(i).clone());
        }
        for(int i=1; i<hiddenLayers + 1; i++){
            for(int j=0; j<(inputNeurons + outputNeurons) / 2; j++){
                layer.get(i).add(clone.layer.get(i).get(j).clone().addLast(layer.get(i - 1), true));
            }
        }
        for(int i=0; i<outputNeurons; i++){
            layer.get(layer.size() - 1).add(clone.layer.get(layer.size() - 1).get(i).clone().addLast(layer.get(layer.size() - 2), true));
        }
    }

    /**
     * Runs the network and returns the output of all neurons.
     * To gather the input the method getInput(int index) will
     * be called for each input neuron.
     * 
     * @return An array of doubles with the output of each neuron.
     */
    public double[] getResults(){
        for(int i=0; i<getInputLayer().size(); i++){
            getInputLayer().get(i).setBiasAsInput(getInput(i));
        }
        double[] result = new double[getOutputLayer().size()];
        for(int i=0; i<result.length; i++){
            result[i] = getOutputLayer().get(i).getResult();
        }
        return result;
    }

    /**
     * Runs the network for one output neuron and returns its
     * result. To gather the input the method getInput(int index)
     * will be called for each neuron.
     * 
     * This method should be used rather than getResults(), as it
     * will return the values much quicker. Also if you need to
     * get half of the neurons outputs, this method will be
     * quicker.
     * 
     * @param index The index of the output neuron the result
     * should be returned from
     * @return The output of that neuron
     */
    public double getResult(int index){
        for(int i=0; i<getInputLayer().size(); i++){
            getInputLayer().get(i).setBiasAsInput(getInput(i));
        }
        return getOutputLayer().get(index).getResult();
    }

    /**
     * Changes all weights and biases in the network randomly in
     * given range. To be able to reach the opposite values you
     * might have to enter '2'.
     * 
     * The actual changing will be done by the change() methods
     * in the neurons and connections themselfes.
     * 
     * @param percent The range of the change
     * 
     * @see        Connection#change(double)
     * @see        Neuron#change(double)
     */
    public void change(double percent){
        for (int i=1; i<layer.size(); i++) {
            for (Neuron n : layer.get(i)) {
                n.change(percent);
            }
        }
    }

    /**
     * This method will be called for each input neurons index
     * whenever getResult(int index) or getResults() is called.
     * The neuron on each index will be set to the value returnd
     * for that index by this method.
     * 
     * Here you have to implement the way that the input is being
     * set.
     * 
     * @param index The index of the neuron that the value is for
     * @return The value the neuron at the given index should
     * contain.
     */
    protected abstract double getInput(int index);

    /**
     * Returns an ArrayList of all neurons in the input layer.
     * May be used with size() to recieve the input layer size.
     * 
     * @return All input layer neurons
     */
    public ArrayList<Neuron> getInputLayer(){
        return layer.get(0);
    }

    /**
     * Returns an ArrayList of all neurons in the output layer.
     * May be used with size() to recieve the output layer size.
     * 
     * @return All outpur layer neurons
     */
    public ArrayList<Neuron> getOutputLayer(){
        return layer.get(layer.size() - 1);
    }

    /**
     * Returns an ArrayList containing an ArrayList for each layer
     * with the neurons of that layer. May be used with size() to
     * revieve the hidden layers sizes.
     * 
     * @return All neurons sorted in ArrayLists
     */
    public ArrayList<ArrayList<Neuron>> getLayers(){
        return layer;
    }


    public static void main(String[] args) {
        Network n1 = new Network(2, 1, 0){
            @Override
            protected double getInput(int index){
                return index;
            }
        };
        Network n2 = new Network(n1){
            @Override
            protected double getInput(int index){
                return index;
            }
        };
        n2.change(1);

        double out1 = n1.getResult(0);
        double out2 = n2.getResult(0);
        Neuron neuron1 = n1.layer.get(1).get(0);
        Neuron neuron2 = n2.layer.get(1).get(0);
        System.out.println(neuron1.getBias());
        System.out.println(neuron2.getBias());
        n2.change(1);
        System.out.println(neuron1.getBias());
        System.out.println(neuron2.getBias());
        System.out.println(out1);
        System.out.println(out2);
        if(cost(out1)<cost(out2))System.out.println("n1 is better");
        else System.out.println("n2 is better");
    }
    public static double cost(double in){
        return Math.abs(in - 1);
    }
}