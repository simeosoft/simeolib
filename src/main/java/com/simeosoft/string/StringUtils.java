/*
    The GUFF - The GNU Ultimate Framework Facility
    Copyright (C) Simeosoft di Carlo Simeone
	
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA	
 */
package com.simeosoft.string;

import java.awt.Color;
import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.*;
import java.sql.Timestamp;

/**
 * Some utilities on strings.
 */
public abstract class StringUtils {
    
    public static final int FORMAT_YYYY_MM_DD_HH_MM_SS = 0;
    public static final int FORMAT_DD_MM_YYYY_HH_MM_SS = 1;
    public static final int FORMAT_DD_MM_YYYY_SLASH = 2;    
    /**
     * ACME date formatter
     * @param d  date - if null uses current date
     * @return formatted date
     */
    public static String formatDate(Date d, int type) {
        DateFormat format = null;
        switch (type) {
            case FORMAT_DD_MM_YYYY_HH_MM_SS:
                format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                break;            
            case FORMAT_YYYY_MM_DD_HH_MM_SS:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case FORMAT_DD_MM_YYYY_SLASH:
                format = new SimpleDateFormat("dd/MM/yyyy");
                break;
        }
        if (d == null) {
            d = new Date(System.currentTimeMillis());
        }
        return format.format(d);
    }
    /**
     * ACME timestamp formatter
     * @param t  jva.sql.Timestamp - if null uses current date
     * @return formatted timestamp
     */
    public static String formatTimestamp(Timestamp t, int type) {
        DateFormat format = null;
        switch (type) {
            case FORMAT_DD_MM_YYYY_HH_MM_SS:
                format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                break;            
            case FORMAT_YYYY_MM_DD_HH_MM_SS:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case FORMAT_DD_MM_YYYY_SLASH:
                format = new SimpleDateFormat("dd/MM/yyyy");
                break;
        }
        if (t == null) {
            t = new Timestamp(System.currentTimeMillis());
        }
        return format.format(t);
    }

    /**
     * ACME String general cleaner (spaces and tabs)
     * @param s stringa da ripulire
     * @return Stringa ripulita
     */
    public static String stringCleaner(String s) {
        String st = new String(s);
        st = s.replace('\t',' ');
        return(st.trim());
    }
    
    /**
     * Cuts off placeholders and spaces from a string
     * @param pVal normalizing value
     * @return normalized value
     */
    public static String normString(String pVal) {
        String s1 = pVal.replace('_',' ');
        return s1.trim();
    }
    
    /**
     * Capitalize a string
     * @param s stringa to capitalize
     * @return capitalized string
     */
    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        char[] ac = { s.charAt(0) };
        String f = new String(ac).toUpperCase();
        return f + s.substring(1).toLowerCase();
    }
    
    /**
     * Returns a stack trace string from an exception
     * @param e exception
     * @return stack trace string
     */
    public static String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        StringBuffer error = sw.getBuffer();
        return error.toString();
    }
    
    /**
     * translate some HTML characters (similar to same PHP function)
     * The translations performed are:
     * '&' (ampersand) becomes '&amp;' NOT YET IMPLEMENTED
     * '"' (double quote) becomes '&quot;' NOT YET IMPLEMENTED
     *''' (single quote) becomes '&#039;' NOT YET IMPLEMENTED
     *'<' (less than) becomes '&lt;'
     *'>' (greater than) becomes '&gt;'
     */
    public static String htmlspecialchars(String s) {
        s = s.replaceAll("<", "&lt;");
        s = s.replaceAll(">", "&gt;");
        return s;
    }
    
    /** 
     * Return file suffix portion of given file.
     * @param file the file to be queried
     * @return suffix (eg: jpg,gif,etc) or null
     */
    public static String getFileSuffix(File file) {
        String suffix = null;
        // get suffix
        if (file.isFile()) {
            String filename = file.getName();
            int indexOf = filename.lastIndexOf(".");
            if (indexOf > 0) {
                suffix = filename.substring(indexOf+1).toLowerCase();
            }
        }
        return suffix;
    }

    /** 
     * Return file portion without suffix of given file.
     * @param file the file to be queried
     * @return filename withouth suffix (if present)
     */
    public static String removeFileSuffix(String file) {
        int indexOf = file.lastIndexOf(".");
        if (indexOf > 0) {
            return file.substring(0,indexOf);
        }
        return file;
    }    
    /** 
     * Puts a path separator at end of a string if missing.
     * @param string a string
     * @return the string with path separator at end
     */
    public static String normPathSeparator(String string) {
        if (string == null) {
            return "";
        }
        if (! string.endsWith(System.getProperty("file.separator"))) {
            return string + System.getProperty("file.separator");
        }
        return string;
    }
    
    /**
     * Returns a string for all array values separated by separator
     * @param array string to pack
     * @param separator separator
     * @return packed string
     */
    public static String pack(String[] array, String separator) {
        StringBuilder app = new StringBuilder();
        if (array.length == 0 || array == null) { return ""; }
        separator = separator == null ? "" : separator;
        for (int i=0; i< array.length; i++) {
            app.append(array[i]);
            if (i != (array.length - 1)) {
                app.append(separator);
            }
        }
        return app.toString();
    }
    
    /**
     * Strips all whitespaces from the end of a string
     * @param s string to trim
     * @return rtrimmed string 
     */
    public static String rtrim(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        for (int i = s.length() - 1; i>=0; i--) {
            if (! Character.isWhitespace(s.charAt(i))) {
                return s.substring(0, i + 1);
            }
        }
        return "";
    }

    /**
     * Returns a StringBuffer with methods and results.<br>
     * Only methods beginning with "get" and "is" are returned.<br>
     * Note: ignore methods with parameters and not returning strings
     * @param target object to dump
     * @param html if true put html tags
     * @return a string buffer 
     */
    public static StringBuffer dumpObject(Object target,boolean html) {
        TreeMap<String,Object> map = new TreeMap<String,Object>();
        try {
            Class cls = target.getClass();
            Method methlist[] = cls.getDeclaredMethods();
            for (int i = 0; i < methlist.length; i++) {  
                Method m = methlist[i];
                if (m.getName().startsWith("get") || m.getName().startsWith("is")) {
                    if (m.getReturnType().getName().equalsIgnoreCase("java.lang.String") && 
                        m.getParameterTypes().length == 0) {
                       map.put(m.getName(), m.invoke(target,(Object[]) null));
                    } else if (m.getReturnType().getName().equalsIgnoreCase("boolean") && 
                        m.getParameterTypes().length == 0) {
                        Boolean b = (Boolean) m.invoke(target,(Object[]) null);
                       map.put(m.getName(),  b.booleanValue() ? "true" : "false" );
                    } else if (m.getReturnType().getName().equalsIgnoreCase("int") && 
                        m.getParameterTypes().length == 0) {
                        Integer integer = (Integer) m.invoke(target,(Object[]) null);
                       map.put(m.getName(), integer.toString());
                    }
                }
            }
        }
        catch (Throwable e) {
            map.put("ERROR!!!", e);
        }
        StringBuffer sb = new StringBuffer(2000);
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            if (html) {
                sb.append(formatTableRow((String) entry.getKey(),(String) entry.getValue()));
            } else {
                sb.append((String) entry.getKey() + "\t" + (String) entry.getValue() + "\n");
            }
        }
        return sb;
    }
    
    /**
     * ACME standard table row formatting
     * component registered by RegisterLogComponent
     * @param c1 first column data
     * @param c2 second column data
     * @return string formatted table row element
     */
    public static String formatTableRow(String c1,String c2) {
        return("<tr><td><font face=arial size=3>" + c1 + "<font></td>" +
        "<td><font face=arial size=3><b>" + c2 + "</b></font></td></tr>");
    }

    /**
     * ACME StringBuffer htmlizer. Makes a new table element on tabs and
     * a new row on linefeeds.
     * @param sb StringBuffer containing text to htmlize
     * @return StringBuffer formatted as html
     */
    public static StringBuffer htmlize(StringBuffer sb) {
        StringBuffer out = new StringBuffer(sb.length() + 100);
        out.insert(0,"<table border=\"1\"><tr><td>");
        for (int i=0;i<sb.length();i++) {
            char c = sb.charAt(i);
            if (c == '\t') {
                out.append("</td><td>");
            } else if (c == '\n') {
                out.append("</td></tr>\n<tr><td>");
            } else {
                out.append(c);
            }
        }
        out.append("</td></tr></table>");        
        return out;
    }
    
    /**
     * Returns the String <code>s</code> repeated <code>count</code> times.
     * @param count number of repetitions
     * @param s the String to repeat
     * @return <code>s + s + ...</code>
     */
    public static String repeatString(int count, String s) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result = result.concat(s);
        }
        return result;
    }
    
    /**
     * Returns a <code>Color</code> from a <code>String</code> containing a
     * comma separated RGB info
     * @param rgb the <code>String</code> in the format "r,g,b"
     * @return the corresponding <code>Color</code>, or <code>null</code> if rgb
     * is not well-formed
     */
    public static Color getColorFromRGB(String rgb) {
        try {
            final String[] arr = rgb.replace(" ", "").split(",");
            final int r = Integer.parseInt(arr[0]);
            final int g = Integer.parseInt(arr[1]);
            final int b = Integer.parseInt(arr[2]);
            return new Color(r, g, b);
        } catch(Exception ex) {
            return null;
        }
    }
    
    /**
     * Main. Contains some tests
     */
    public static void main(String args[]) {
        // some tests
        System.out.println("capitalize -> (sghaHGHGHG): " + capitalize("sghaHGHGHG"));
        System.out.println("capitalize -> (a): " + capitalize("a"));
        System.out.println("htmlspecialchars -> (a string <b>BOLD</b> <HTML><BODY>): " +
                            htmlspecialchars("a string <b>BOLD</b> <HTML><BODY>"));
        System.out.println("removeFileSuffix -> (hello.txt): " + removeFileSuffix("hello.txt"));
        System.out.println("removeFileSuffix -> (hello): " + removeFileSuffix("hello"));        
        String[] as = { "aaa", "bbb", "ccc"};
        System.out.println("pack -> " + pack(as,","));                        
        System.out.println("removeFileSuffix -> (hello): " + removeFileSuffix("hello"));                
        System.out.println("rtrim -> (hello     ): [" + rtrim("hello     ") + "]");        
        System.out.println("rtrim -> (hello hello ): [" + rtrim("hello hello ") + "]");        
        System.out.println("rtrim -> (     hello): [" + rtrim("    hello") + "]");        
        System.out.println("rtrim -> (    ): [" + rtrim("    ") + "]");        
        System.out.println("rtrim -> (): [" + rtrim("") + "]");        
        StringBuffer sb = new StringBuffer();
        sb.append("a\nb\nc\n\n\n1\t2\t3\t4\nd\ne");
        System.out.println("htmlize -> (a\nb\nc\n\n\n1\t2\t3\t4\nd\ne):\n[" + htmlize(sb) + "]");                
    }
}
