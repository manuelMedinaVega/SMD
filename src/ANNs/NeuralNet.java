/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ANNs;


import java.io.PrintStream;

abstract class NeuralNet
{

    boolean finishedLearning()
    {
        return stopLearning;
    }

    void error(int i)
    {
        String s = null;
        String s1 = null;
        switch(i)
        {
        case 105: // 'i'
            s = "The specified pattern file couldn't be found.";
            s1 = "Check path and filename of your pattern file.";
            break;

        case 104: // 'h'
            s = "A general IO error occurred while reading a file.";
            s1 = "Check the accessed file, maybe it's corrupted.";
            break;

        case 100: // 'd'
            s = "A pattern in the specified pattern file has the wrong number of values.";
            s1 = "The amount of values in one row must be: value in second line + value in third line + 1.";
            break;

        case 106: // 'j'
            s = "The number of input values in the specified pattern file is not the same as the number of neurons in the input neuron layer.";
            s1 = "Set the value in the second line of your pattern file to the number of input neurons or change the number of neurons in the input layer.";
            break;

        case 107: // 'k'
            s = "The number of target values in the specified pattern file is not the same as the number of neurons in the output neuron layer.";
            s1 = "Set the value in the third line of your pattern file to the number of output neurons.";
            break;

        case 101: // 'e'
            s = "A pattern value in the specified pattern file was wrong.";
            s1 = "Use pattern values that are 0 or 1.";
            break;

        case 102: // 'f'
            s = "The number of patterns in the specified pattern file does not match the given number.";
            s1 = "Correct the number in the first line of your pattern file.";
            break;

        case 108: // 'l'
            s = "There are too few maximum learning cycles defined.";
            s1 = "Increase the value of the maximum learning cycles or set it to -1 if the net should learn infinitely.";
            break;
        }
        System.out.println("Error [" + i + "]:\n\r" + s);
        System.out.println("Try this: " + s1 + "\n\r");
        System.exit(0);
    }

    public double square(double d)
    {
        return d * d;
    }

    void setLearningRate(double d)
    {
        learningRate = d;
    }

    double getLearningRate()
    {
        return learningRate;
    }

    void setDisplayStep(int i)
    {
        displayStep = i;
    }

    boolean displayNow()
    {
        return learningCycle % displayStep == 0;
    }

    void resetTime()
    {
        startTime = System.currentTimeMillis();
    }

    String getElapsedTime()
    {
        return String.valueOf((float)((double)(System.currentTimeMillis() - startTime) * 0.001D));
    }

    void setMaxLearningCycles(int i)
    {
        if(i == -1)
        {
            learnInfinitely = true;
            maxLearningCycles = -1;
            maxLearningCyclesString = "infinite";
            return;
        }
        if(i > 0)
        {
            maxLearningCycles = i;
            learnInfinitely = false;
            maxLearningCyclesString = String.valueOf(i);
            return;
        } else
        {
            error(108);
            return;
        }
    }

    int getMaxLearningCycles()
    {
        return maxLearningCycles;
    }

    void incLearningCycle()
    {
        learningCycle++;
    }

    int getLearningCycle()
    {
        return learningCycle;
    }

    NeuralNet()
    {
        maxLearningCycles = -1;
        learnInfinitely = false;
        stopLearning = false;
    }

    final int PATTERN_LENGTH = 100;
    final int PATTERN_VALUE = 101;
    final int PATTERNFILE_LENGTH = 102;
    final int GENERAL_IO = 104;
    final int FILE_NOT_FOUND = 105;
    final int NUMBER_OF_IVALUES = 106;
    final int NUMBER_OF_TVALUES = 107;
    final int TOO_FEW_LEARNINGCYCLES = 108;
    double learningRate;
    int learningCycle;
    int maxLearningCycles;
    String maxLearningCyclesString;
    int displayStep;
    long startTime;
    boolean learnInfinitely;
    boolean stopLearning;
}