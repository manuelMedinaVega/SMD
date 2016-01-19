/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ANNs;

public class NeuronMatrix
{

    public NeuronMatrix(int i)
    {
        xSize = i;
        ySize = 1;
        mapNeuron = new MapNeuron[xSize * ySize];
        for(int j = 0; j < i; j++)
            mapNeuron[j] = new MapNeuron();

    }

    public NeuronMatrix(int i, int j)
    {
        xSize = i;
        ySize = j;
        mapNeuron = new MapNeuron[i * j];
        int k = 0;
        for(int l = 0; l < i; l++)
        {
            for(int i1 = 0; i1 < j; i1++)
                mapNeuron[k++] = new MapNeuron();

        }

    }

    void init(InputValue ainputvalue[])
    {
        int i = 0;
        for(int j = 0; j < xSize; j++)
        {
            for(int k = 0; k < ySize; k++)
            {
                mapNeuron[i].init(j, k);
                i++;
            }

        }

    }

    int size()
    {
        return mapNeuron.length;
    }

    int xSize()
    {
        return xSize;
    }

    int ySize()
    {
        return ySize;
    }

    MapNeuron[] getMapNeurons()
    {
        return mapNeuron;
    }

    MapNeuron computeActivationCenter(NeuronLayer neuronlayer, WeightMatrix weightmatrix)
    {
        float f = 1E+020F;
        int i = 0;
        float af[] = new float[weightmatrix.size()];
        float af2[] = new float[neuronlayer.size()];
        af2 = neuronlayer.getOutput();
        for(int j = 0; j < mapNeuron.length; j++)
        {
            float af1[] = weightmatrix.getInputWeights(j);
            float f1 = 0.0F;
            for(int k = 0; k < af2.length; k++)
                if(af2[k] != af1[k])
                    f1 += (af2[k] - af1[k]) * (af2[k] - af1[k]);

            if(f1 < f)
            {
                f = f1;
                i = j;
            }
        }

        return mapNeuron[i];
    }

    float[] getFeedback(MapNeuron mapneuron, double d)
    {
        float af[] = new float[mapNeuron.length];
        float f = (float)(2D * d * d);
        for(int i = 0; i < mapNeuron.length; i++)
            af[i] = mapNeuron[i].computeFeedback(mapneuron, f);

        return af;
    }

    MapNeuron mapNeuron[];
    int nr;
    int xSize;
    int ySize;
}