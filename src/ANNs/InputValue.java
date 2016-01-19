/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ANNs;


public class InputValue
{

    public InputValue()
    {
        x = 0;
        y = 0;
        z = 0;
    }

    public InputValue(int i, int j, int k)
    {
        x = i;
        y = j;
        z = k;
    }

    void setX(int i)
    {
        x = i;
    }

    void setY(int i)
    {
        y = i;
    }

    void setZ(int i)
    {
        z = i;
    }

    int getX()
    {
        return x;
    }

    int getY()
    {
        return y;
    }

    int getZ()
    {
        return z;
    }

    int x;
    int y;
    int z;
}