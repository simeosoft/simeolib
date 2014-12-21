/*
    Simeolib - a useful general purpose library
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
package com.simeosoft.util;

import java.security.MessageDigest;

/**
 * Some utilities for MD5
 */
public abstract class MD5Utils {
    
    /**
     * Returns text digested in MD5.
     */
    public static String MD5( String text ) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch( Exception e ) {
            System.out.println("***** MD5 EXC: " + e);
        }
        md5.update(text.getBytes());
        return toHexString(md5.digest());
    }
    
    private static String toHexString(byte[] v) {
        StringBuffer sb = new StringBuffer(v.length * 2);
        for (int i = 0; i < v.length; i++) {
            int b = v[i] & 0xFF;
            sb.append(HEX_DIGITS.charAt(b >>> 4))
            .append(HEX_DIGITS.charAt(b & 0xF));
        }
        return sb.toString();
    }
    
    private static final String HEX_DIGITS = "0123456789abcdef";
    
    public static String escapeString(String s) {
        return s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
    
    public static void main(String[] args) {
        System.out.println("***** MD5: input: 'pippo': [" + MD5("pippo") + "]");
        System.out.println("***** ESCAPO: input: '<pippo><>': [" + escapeString("<pippo><>") + "]");
    }
}