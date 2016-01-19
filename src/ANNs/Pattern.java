/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ANNs;


public class Pattern
{

    public Pattern(String s, String as[][])
    {
        patString = s;
        pat = new int[s.length() * as[0][1].length()];
        String s1 = new String();
        String s2 = new String();
        for(int i = 0; i < s.length(); i++)
        {
            for(int j = 0; j < as.length; j++)
            {
                String s3 = String.valueOf(s.charAt(i));
                if(s3.compareTo(" ") == 0)
                {
                    s1 += as[0][1];
                    break;
                }
                if(s3.compareTo(as[j][0]) != 0)
                    continue;
                s1 += as[j][1];
                break;
            }

        }

        char ac[] = new char[s1.length()];
        ac = s1.toCharArray();
        for(int k = 0; k < pat.length; k++)
            pat[k] = Character.digit(ac[k], 10);

    }

    String getPatternString()
    {
        return patString;
    }

    float getValue(int i)
    {
        return (float)pat[i];
    }

    int size()
    {
        return pat.length;
    }

    boolean checkValues()
    {
        for(int i = 0; i < pat.length; i++)
            if(pat[i] < 0 || pat[i] > 1)
                return false;

        return true;
    }

    int pat[];
    int nr;
    String type;
    String patString;
}