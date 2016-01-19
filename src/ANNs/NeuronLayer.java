/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ANNs;

public class NeuronLayer
{

    NeuronLayer(int i)
    {
        neuron = new Neuron[i];
        for(int j = 0; j < i; j++)
            neuron[j] = new Neuron();

    }

    void setInput(Pattern pattern)
    {
        for(int i = 0; i < neuron.length; i++)
            neuron[i].init(pattern.getValue(i));

    }

    void setInput(InputValue inputvalue)
    {
        float af[] = new float[3];
        af[0] = inputvalue.getX();
        af[1] = inputvalue.getY();
        af[2] = inputvalue.getZ();
        for(int i = 0; i < neuron.length; i++)
            neuron[i].init(af[i]);

    }

    void computeInput(NeuronLayer neuronlayer, WeightMatrix weightmatrix)
    {
        for(int i = 0; i < neuron.length; i++)
            neuron[i].computeInput(neuronlayer.getOutput(), weightmatrix.getInputWeights(i));

    }

    void computeOutput()
    {
        for(int i = 0; i < neuron.length; i++)
            neuron[i].activateSigmoid();

    }

    void computeLayerError(Pattern pattern)
    {
        for(int i = 0; i < neuron.length; i++)
            neuron[i].computeOutputError(pattern.getValue(i));

    }

    void computeLayerError(NeuronLayer neuronlayer, WeightMatrix weightmatrix)
    {
        for(int i = 0; i < neuron.length; i++)
            neuron[i].computeOutputError(neuronlayer.getLayerError(), weightmatrix.getOutputWeights(i));

    }

    float[] getOutput()
    {
        float af[] = new float[neuron.length];
        for(int i = 0; i < neuron.length; i++)
            af[i] = neuron[i].getOutput();

        return af;
    }

    float[] getLayerError()
    {
        float af[] = new float[neuron.length];
        for(int i = 0; i < neuron.length; i++)
            af[i] = neuron[i].getOutputError();

        return af;
    }

    int size()
    {
        return neuron.length;
    }

    Neuron neuron[];
}