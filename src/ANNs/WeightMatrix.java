/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ANNs;

public class WeightMatrix
{

    public WeightMatrix(int i, int j, boolean flag)
    {
        weight = new float[i][j];
        bias = new float[j];
        for(int k = 0; k < j; k++)
            bias[k] = 1.0F;

        size = i * j;
        if(flag)
            size += j;
    }

    void init()
    {
        for(int i = 0; i < weight.length; i++)
        {
            for(int j = 0; j < weight[0].length; j++)
                weight[i][j] = (float)Math.random() * 2.0F - 1.0F;

        }

        for(int k = 0; k < weight[0].length; k++)
            bias[k] = (float)Math.random() * 2.0F - 1.0F;

    }

    void init(float af[][])
    {
        for(int i = 0; i < weight.length; i++)
        {
            for(int j = 0; j < weight[0].length; j++)
                weight[i][j] = af[i][j];

        }

    }

    void init(InputValue ainputvalue[], int i)
    {
        switch(i)
        {
        case 1: // '\001'
            for(int j = 0; j < weight[0].length; j++)
                weight[0][j] = ainputvalue[j].getX();

            return;

        case 2: // '\002'
            for(int k = 0; k < weight[0].length; k++)
            {
                weight[0][k] = ainputvalue[k].getX();
                weight[1][k] = ainputvalue[k].getY();
            }

            return;

        case 3: // '\003'
            for(int l = 0; l < weight[0].length; l++)
            {
                weight[0][l] = ainputvalue[l].getX();
                weight[1][l] = ainputvalue[l].getY();
                weight[2][l] = ainputvalue[l].getZ();
            }

            return;
        }
    }

    void changeWeights(float af[], float af1[], double d)
    {
        for(int i = 0; i < weight.length; i++)
            if(af[i] != 0.0F)
            {
                for(int j = 0; j < weight[0].length; j++)
                    if(af1[j] != 1.0F && af1[j] != 0.0F)
                    {
                        float f = weight[i][j];
                        weight[i][j] = 0.0F;
                        weight[i][j] = f + af[i] * af1[j] * (1.0F - af1[j]) * (float)d;
                    }

            }

        for(int k = 0; k < bias.length; k++)
            if(af1[k] != 1.0F && af1[k] != 0.0F)
            {
                float f1 = bias[k];
                bias[k] = 0.0F;
                bias[k] = f1 + af1[k] * (1.0F - af1[k]) * (float)d;
            }

    }

    void changeWeightsKFM(float af[], float af1[], double d)
    {
        for(int i = 0; i < weight.length; i++)
        {
            for(int j = 0; j < weight[0].length; j++)
                if(af[i] != weight[i][j] && af1[j] != 0.0F)
                {
                    float f = weight[i][j];
                    weight[i][j] = 0.0F;
                    weight[i][j] = f + af1[j] * (af[i] - f) * (float)d;
                }

        }

    }

    float[] getInputWeights(int i)
    {
        float af[] = new float[weight.length + 1];
        for(int j = 0; j < weight.length; j++)
            af[j] = weight[j][i];

        af[weight.length] = bias[i];
        return af;
    }

    float[] getOutputWeights(int i)
    {
        float af[] = new float[weight[0].length];
        for(int j = 0; j < weight[0].length; j++)
            af[j] = weight[i][j];

        return af;
    }

    int size()
    {
        return size;
    }

    float[][] getWeights()
    {
        return weight;
    }

    float[] getBiases()
    {
        return bias;
    }

    float weight[][];
    float bias[];
    int size;
}