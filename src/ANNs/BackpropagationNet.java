/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ANNs;


import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

public class BackpropagationNet extends NeuralNet
{

    public BackpropagationNet()
    {
        super.learningCycle = 0;
        super.maxLearningCycles = -1;
        minimumError = 0.00050000000000000001D;
        super.learningRate = 0.25D;
        error = 1000D;
        multiplier = 0;
        accuracy = 0.20000000000000001D;
        neuronLayerVector = new Vector();
        super.stopLearning = false;
        resetTime();
    }

    void addNeuronLayer(int i)
    {
        neuronLayerVector.addElement(new NeuronLayer(i * multiplier));
    }

    void connectLayers()
    {
        weightMatrixArray = new WeightMatrix[neuronLayerVector.size() - 1];
        neuronLayerArray = new NeuronLayer[neuronLayerVector.size()];
        int i = 0;
        for(Enumeration enumeration = neuronLayerVector.elements(); enumeration.hasMoreElements();)
            neuronLayerArray[i++] = (NeuronLayer)enumeration.nextElement();

        neuronLayerVector = null;
        for(int j = 0; j < weightMatrixArray.length; j++)
        {
            weightMatrixArray[j] = new WeightMatrix(neuronLayerArray[j].size(), neuronLayerArray[j + 1].size(), true);
            weightMatrixArray[j].init();
        }

        lastLayer = neuronLayerArray.length - 1;
        lastMatrix = weightMatrixArray.length - 1;
    }

    void setMinimumError(double d)
    {
        minimumError = d;
    }

    double getMinimumError()
    {
        return minimumError;
    }

    void setAccuracy(double d)
    {
        accuracy = d;
    }

    double getAccuracy()
    {
        return accuracy;
    }

    float[][] getWeightValues(int i)
    {
        return weightMatrixArray[i].getWeights();
    }

    float[] getNeuronOutputs(int i)
    {
        return neuronLayerArray[i].getOutput();
    }

    int getNumberOfLayers()
    {
        return neuronLayerArray.length;
    }

    int getNumberOfNeurons(int i)
    {
        return neuronLayerArray[i].size();
    }

    int getNumberOfWeights()
    {
        int i = 0;
        for(int j = 0; j <= lastMatrix; j++)
            i += weightMatrixArray[j].size();

        return i;
    }

    int getNumberOfWeights(int i)
    {
        return weightMatrixArray[i].size();
    }

    int getNumberOfPatterns()
    {
        return inputPatternArray.length;
    }

    String getInputPattern(int i)
    {
        return inputPatternArray[i].getPatternString();
    }

    String getTargetPattern(int i)
    {
        return targetPatternArray[i].getPatternString();
    }

    String getOutputPattern(int i)
    {
        String s = new String();
        String s1 = new String();
        float f = 0.0F;
        for(int j = 0; j < layerOutputError[0].length; j++)
        {
            float f1 = targetPatternArray[i].getValue(j) - layerOutputError[i][j];
            s1 += (double)f1 >= accuracy / 10D ? "1" : "0";
        }

        s = "";
        for(int k = 0; k < s1.length(); k += multiplier)
            s += getAsciiValue(s1.substring(k, k + multiplier));

        return s;
    }

    float getPatternError(int i)
    {
        float f = 0.0F;
        for(int j = 0; j < layerOutputError[0].length; j++)
            f += Math.abs(layerOutputError[i][j]);

        return f;
    }

    double getError()
    {
        return error;
    }
    
    public void mostrarPesos(){
        //float[][] weight=this.weightMatrixArray[0].getWeights();
        
        int c=0;
        for(int h=0;h<this.weightMatrixArray.length;h++){
            float[][] weight=this.weightMatrixArray[h].getWeights();
            for(int i = 0; i < weight.length; i++)
            {
                for(int j = 0; j < weight[0].length; j++){
                    System.out.println(weight[i][j]);
                    c++;
                }
            }
        }
        System.out.println(weightMatrixArray.length);
        System.out.println("pesos: "+c);    
    }
    void learn()
    {
        if(error > minimumError && (super.learningCycle < super.maxLearningCycles || super.maxLearningCycles == -1))
        {
            super.learningCycle++;
            for(int i = 0; i <= lastPattern; i++)
            {
                neuronLayerArray[0].setInput(inputPatternArray[i]);
                for(int j = 1; j <= lastLayer; j++)
                {
                    neuronLayerArray[j].computeInput(neuronLayerArray[j - 1], weightMatrixArray[j - 1]);
                    neuronLayerArray[j].computeOutput();
                }

                neuronLayerArray[lastLayer].computeLayerError(targetPatternArray[i]);
                layerOutputError[i] = neuronLayerArray[lastLayer].getLayerError();
                for(int k = lastMatrix; k >= 0; k--)
                {
                    weightMatrixArray[k].changeWeights(neuronLayerArray[k].getOutput(), neuronLayerArray[k + 1].getLayerError(), super.learningRate);
                    if(k > 0)
                        neuronLayerArray[k].computeLayerError(neuronLayerArray[k + 1], weightMatrixArray[k]);
                }

            }

            double d = 0.0D;
            for(int l = 0; l < layerOutputError.length; l++)
            {
                for(int i1 = 0; i1 < layerOutputError[0].length; i1++)
                    d += square(layerOutputError[l][i1]);

            }

            error = Math.abs(d * 0.5D);
            return;
        } else
        {
            super.stopLearning = true;
            return;
        }
    }

    float[] recall2(String s)
    {
        Pattern pattern = new Pattern(s, conversionTable);
        float af[] = new float[targetPatternArray[0].size()];
        neuronLayerArray[0].setInput(pattern);
        for(int i = 1; i <= lastLayer; i++)
        {
            neuronLayerArray[i].computeInput(neuronLayerArray[i - 1], weightMatrixArray[i - 1]);
            neuronLayerArray[i].computeOutput();
        }

        af = neuronLayerArray[lastLayer].getOutput();
        
        String s2;
        String s1 = s2 = "";
        //System.out.println("valores de af:");
        for(int j = 0; j < af.length; j++){
            //System.out.println(af[j]);
            s1 += (double)af[j] >= accuracy ? "1" : "0";}

        for(int k = 0; k < s1.length(); k += multiplier)
            s2 += getAsciiValue(s1.substring(k, k + multiplier));

        //return s2;
        return af;
    }

    String recall(String s)
    {
        Pattern pattern = new Pattern(s, conversionTable);
        float af[] = new float[targetPatternArray[0].size()];
        neuronLayerArray[0].setInput(pattern);
        for(int i = 1; i <= lastLayer; i++)
        {
            neuronLayerArray[i].computeInput(neuronLayerArray[i - 1], weightMatrixArray[i - 1]);
            neuronLayerArray[i].computeOutput();
        }

        af = neuronLayerArray[lastLayer].getOutput();
        
        String s2;
        String s1 = s2 = "";
        //System.out.println("valores de af:");
        for(int j = 0; j < af.length; j++){
            //System.out.println(af[j]);
            s1 += (double)af[j] >= accuracy ? "1" : "0";}

        for(int k = 0; k < s1.length(); k += multiplier)
            s2 += getAsciiValue(s1.substring(k, k + multiplier));

        return s2;
    }
    
    synchronized void readConversionFile(String s)
    {
        Object obj = null;
        try
        {
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(s));
            int i = Integer.parseInt(datainputstream.readLine());
            conversionTable = new String[i][2];
            for(int j = 0; j < i; j++)
            {
                String s1 = datainputstream.readLine();
                conversionTable[j][0] = String.valueOf(s1.charAt(0));
                conversionTable[j][1] = s1.substring(1);
            }

            multiplier = conversionTable[0][1].length();
            return;
        }
        catch(FileNotFoundException _ex)
        {
            error(105);
            return;
        }
        catch(IOException _ex)
        {
            error(104);
        }
    }

    String getAsciiValue(String s)
    {
        int i = 0;
        int j = conversionTable.length;
        boolean flag = false;
        boolean flag1 = false;
        while(i < j) 
        {
            int k = i + j >> 1;
            int l = s.compareTo(conversionTable[k][1]);
            if(l == 0)
                return conversionTable[k][0];
            if(l > 0)
                i = k;
            else
                j = k;
        }
        return "*";
    }

    synchronized void readPatternFile(String s)
    {
        Object obj = null;
        try
        {
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(s));
            try
            {
                int i = Integer.parseInt(datainputstream.readLine());
                System.out.println("");
                System.out.println(i);
                int j = Integer.parseInt(datainputstream.readLine());
                System.out.println(j);
                if(j * multiplier != neuronLayerArray[0].size())
                    error(106);
                int k = Integer.parseInt(datainputstream.readLine());
                System.out.println(k);
                if(k * multiplier != neuronLayerArray[lastLayer].size())
                    error(107);
                inputPatternArray = new Pattern[i];
                targetPatternArray = new Pattern[i];
                outputPatternArray = new String[i];
                lastPattern = inputPatternArray.length - 1;
                layerOutputError = new float[lastPattern + 1][neuronLayerArray[lastLayer].size()];
                for(int l = 0; l < i; l++)
                {
                    String s1 = datainputstream.readLine();
                    if(s1 == null)
                        error(102);
                    else
                    if(s1.length() != j + k + 1)
                    {
                        System.out.println(s1.length());
                        System.out.println(s1);
                        System.out.println(l);
                        error(100);
                    } else
                    {
                        inputPatternArray[l] = new Pattern(s1.substring(0, j), conversionTable);
                        targetPatternArray[l] = new Pattern(s1.substring(j + 1), conversionTable);
                        outputPatternArray[l] = new String();
                    }
                }

                return;
            }
            catch(EOFException _ex)
            {
                error(102);
            }
            return;
        }
        catch(FileNotFoundException _ex)
        {
            error(105);
            return;
        }
        catch(IOException _ex)
        {
            error(104);
        }
    }

    
    
    Vector neuronLayerVector;
    NeuronLayer neuronLayerArray[];
    WeightMatrix weightMatrixArray[];
    Pattern inputPatternArray[];
    Pattern targetPatternArray[];
    String outputPatternArray[];
    double minimumError;
    double error;
    double accuracy;
    float layerOutputError[][];
    String conversionTable[][];
    int lastLayer;
    int lastMatrix;
    int lastPattern;
    int multiplier;
}