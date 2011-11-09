// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:52:40 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CleanSpaces.java

package create;


public class CleanSpaces
{

    public CleanSpaces()
    {
    }

    public static String clean(String s)
    {
        if(s == null)
            return null;
        StringBuffer sb = (new StringBuffer()).append(s.trim());
        int x = 0;
        for(int i = 0; i < sb.length(); i++)
        {
            if(i + 1 > sb.length())
            {
                if(sb.charAt(i) == ' ')
                    sb.deleteCharAt(i);
                continue;
            }
            if(sb.charAt(i) == ' ' && sb.charAt(i + 1) == ' ')
                sb.deleteCharAt(i);
            if(sb.charAt(i) != ' ')
                x = 1;
            if(sb.charAt(i) == ' ' && x == 0)
                sb.deleteCharAt(i);
        }

        return sb.toString();
    }
}
