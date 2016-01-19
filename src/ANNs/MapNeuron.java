/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ANNs;

public class MapNeuron extends Neuron
{

    public MapNeuron()
    {
        init(0, 0);
    }

    public MapNeuron(int i, int j)
    {
        init(i, j);
    }

    void init(int i, int j)
    {
        x = i;
        y = j;
        feedback = 0.0F;
    }

    int getXPos()
    {
        return x;
    }

    int getYPos()
    {
        return y;
    }

    float getFeedback()
    {
        return feedback;
    }

    float computeFeedback(MapNeuron mapneuron, double d)
    {
        int i = Math.abs(x - mapneuron.getXPos());
        int j = Math.abs(y - mapneuron.getYPos());
        return (float)Math.exp((double)(-(i * i + j * j)) / d);
    }

    int x;
    int y;
    float feedback;
}