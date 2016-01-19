/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ANNs;

public class KohonenFeatureMap extends NeuralNet
{

    public KohonenFeatureMap()
    {
        super.learningCycle = 0;
        super.maxLearningCycles = -1;
        initLearningRate = 0.59999999999999998D;
        super.learningRate = initLearningRate;
        initActivationArea = xSize < ySize ? ySize / 2 : xSize / 2;
        activationArea = initActivationArea;
        stopArea = initActivationArea / 10D;
        super.stopLearning = false;
        resetTime();
    }

    void createMapLayer(int i)
    {
        if(mapLayer != null)
            mapLayer = null;
        mapLayer = new NeuronMatrix(i);
        xSize = i;
        ySize = 0;
    }

    void createMapLayer(int i, int j)
    {
        if(mapLayer != null)
            mapLayer = null;
        mapLayer = new NeuronMatrix(i, j);
        xSize = i;
        ySize = j;
    }

    void connectLayers(InputMatrix inputmatrix)
    {
        inputMatrix = inputmatrix;
        if(inputLayer != null)
            inputLayer = null;
        inputLayer = new NeuronLayer(inputmatrix.getDimension());
        int i = mapLayer.size();
        if(weightMatrix != null)
            weightMatrix = null;
        weightMatrix = new WeightMatrix(inputLayer.size(), i, false);
        if(iv != null)
            iv = null;
        iv = new InputValue[i];
        for(int j = 0; j < iv.length; j++)
            iv[j] = inputmatrix.getRandomInput();

        weightMatrix.init(iv, inputmatrix.getDimension());
        mapLayer.init(iv);
    }

    void setInitLearningRate(double d)
    {
        initLearningRate = d;
        super.learningRate = d;
    }

    double getInitLearningRate()
    {
        return initLearningRate;
    }

    void setInitActivationArea(double d)
    {
        initActivationArea = d;
        activationArea = d;
    }

    double getInitActivationArea()
    {
        return initActivationArea;
    }

    void setActivationArea(double d)
    {
        activationArea = d;
    }

    void setStopArea(double d)
    {
        stopArea = d;
    }

    double getStopArea()
    {
        return stopArea;
    }

    double getActivationArea()
    {
        return activationArea;
    }

    int getMapSizeX()
    {
        return xSize;
    }

    int getMapSizeY()
    {
        return ySize;
    }

    int getNumberOfWeights()
    {
        return weightMatrix.size();
    }

    float[][] getWeightValues()
    {
        return weightMatrix.getWeights();
    }

    MapNeuron[] getMapNeurons()
    {
        return mapLayer.getMapNeurons();
    }

    InputValue[] getInputValues()
    {
        return inputMatrix.getInputValues();
    }

    void decreaseActivationArea()
    {
        double d = (double)super.learningCycle * 0.00020000000000000001D;
        setLearningRate(initLearningRate * Math.exp(-2D * d));
        setActivationArea(initActivationArea * Math.exp(-5D * d));
    }

    void learn()
    {
        if(activationArea > stopArea && (super.learningCycle < super.maxLearningCycles || super.maxLearningCycles == -1))
        {
            super.learningCycle++;
            inputLayer.setInput(inputMatrix.getRandomInput());
            MapNeuron mapneuron = mapLayer.computeActivationCenter(inputLayer, weightMatrix);
            weightMatrix.changeWeightsKFM(inputLayer.getOutput(), mapLayer.getFeedback(mapneuron, activationArea), super.learningRate);
            decreaseActivationArea();
            return;
        } else
        {
            super.stopLearning = true;
            return;
        }
    }

    NeuronMatrix mapLayer;
    NeuronLayer inputLayer;
    WeightMatrix weightMatrix;
    InputMatrix inputMatrix;
    InputValue iv[];
    int xSize;
    int ySize;
    double activationArea;
    double stopArea;
    double initActivationArea;
    double initLearningRate;
}