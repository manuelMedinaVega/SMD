/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ANNs;


public class Neuron
{

    public Neuron()
    {
        init(0.0F);
    }

    void init(float f)
    {
        input = f;
        output = f;
        outputError = 0.0F;
    }

    void computeInput(float af[], float af1[])
    {
        input = 0.0F;
        for(int i = 0; i < af.length; i++)
            if(af[i] != 0.0F && af1[i] != 0.0F)
                input += af[i] * af1[i];

        input += af1[af1.length - 1];
    }

    void activateSigmoid()
    {
        output = 1.0F / (1.0F + (float)Math.exp(-input));
    }

    void computeOutputError(float f)
    {
        if(output != 0.0F && output != 1.0F)
            outputError = (f - output) * output * (1.0F - output);
    }

    void computeOutputError(float af[], float af1[])
    {
        outputError = 0.0F;
        for(int i = 0; i < af.length; i++)
            if(af[i] != 0.0F && output != 0.0F && output != 1.0F)
                outputError += af[i] * af1[i] * output * (1.0F - output);

    }

    float getInput()
    {
        return input;
    }

    float getOutput()
    {
        return output;
    }

    float getOutputError()
    {
        return outputError;
    }

    float input;
    float output;
    float outputError;
}