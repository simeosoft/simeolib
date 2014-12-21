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

package com.simeosoft.form;

import java.util.ArrayList;
import javax.swing.JComponent;

/** 
 * Field exception.
 * <br>$Id: FieldException.java 16 2007-06-11 10:40:29Z simeo $
 * 
 */
public class FieldException extends Exception{
    static final long serialVersionUID = 12222332;    
    private ArrayList<String> al = new ArrayList<String>();
    private ArrayList<String> desc = new ArrayList<String>();
    private JComponent jc = null;
    
    /**
     * Creates a new instance of FieldException
     */
    public FieldException() {
    }
    
    /**
     * Creates a new instance of FieldException.
     * Error messages can be obtained concatenating
     * the n-th string on al with the n-th string on desc.
     * @param msg main error message
     * @param al contains the errors
     * @param desc contains the field descriptions
     */
    public FieldException(String msg, ArrayList<String> al, ArrayList<String> desc) {
        this.al = al;
        this.desc = desc;
     }
    /**
     * Creates a new instance of FieldException.
     * Error messages can be obtained concatenating
     * the n-th string on al with the n-th string on desc.
     * @param msg main error message
     * @param al contains the errors
     * @param desc contains the field descriptions
     * @param jc first (or only) component that should receive focus
     */
    public FieldException(String msg, ArrayList<String> al, ArrayList<String> desc, JComponent jc) {
        this.al = al;
        this.desc = desc;
        this.jc = jc;
     }
    
    public String getMessage() {
        StringBuilder sb = new StringBuilder(150);
        for (int i=0; i<al.size(); i++) {
            sb.append(al.get(i) + ": " + desc.get(i) + "\n");
        }
        return sb.toString();
    }
    
    public String getLocalizedMessage() {
        return getMessage();
    }
    /**
     * Returns the error messages
     * @return ArrayList<String> errors messages
     */
    public ArrayList<String> getErrors() {
        return al;
    }
    
    /**
     * Returns the field descriptions
     * @return ArrayList<String> field descriptions
     */
    public ArrayList<String> getDesc() {
        return desc;
    }
    
    public JComponent getFocusableComponent() {
        return jc;
    }
}
