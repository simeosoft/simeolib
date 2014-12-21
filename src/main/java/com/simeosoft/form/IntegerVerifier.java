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

import java.text.DecimalFormat;
import javax.swing.*;

/** 
 * GUI Integer Component verifier.
 * <br>$Id: IntegerVerifier.java 7 2007-02-05 16:47:08Z mauro $
 */
public class IntegerVerifier extends FieldVerifier {
    int len = 10;
    boolean signed = false;
    int maxValue = Integer.MAX_VALUE;
    int minValue = Integer.MIN_VALUE;
    
    /**
     * Builds a IntegerVerifier whih the default lenght (10 characters)
     * @param fc FormController to use for error messages
     * @param isMandatory true if leaving field blank is not allowed
     * @param signed true if field is signed
     */
    public IntegerVerifier(FormController fc, boolean isMandatory, boolean signed) {
        super(fc, isMandatory);
        this.signed = signed;
    }
    /**
     * Builds a IntegerVerifier given the field length;
     * @param fc FormController to use for error messages
     * @param len field len (if 0 uses default (10)) 
     * @param isMandatory true if leaving field blank is not allowed
     * @param signed true if field is signed
     */
    public IntegerVerifier(FormController fc, int len, boolean isMandatory, boolean signed) {
        super(fc, isMandatory);
        if (len > 0) {
            this.len = len;
        }
        this.signed = signed;        
    }
    
    /**
     * Builds a IntegerVerifier given the field length;
     * @param fc FormController to use for error messages
     * @param len field len (if 0 uses default (10))
     * @param isMandatory true if leaving field blank is not allowed
     * @param signed true if field is signed
     * @param maxValue max value
     * @param minValue min value
     */
    public IntegerVerifier(FormController fc, int len, boolean isMandatory, boolean signed,int maxValue, int minValue) {
        super(fc, isMandatory);
        if (len > 0) {
            this.len = len;
        }
        this.signed = signed;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }
    
    /**
     * Verifies the component
     * @param input JComponent to verify
     */
    public boolean verify(JComponent input) {
        String s = ((JFormattedTextField) input).getText().trim();
        String valid_chars = (String) ((JFormattedTextField) input).getClientProperty(IFormController.PROPERTY_VALID_CHARS);
        if (s.length() > len) {
            return endVerifier(IFormController.validationError.INTEGER_MAXLEN, input);
        }
        for (int i = 0; i<s.length(); i++) {
            if (valid_chars.indexOf(s.charAt(i)) == -1) {
                return endVerifier(IFormController.validationError.INTEGER_INVALID, input);    
            }
        }
        if (s.startsWith("+") || s.startsWith("-")) {
            s = s.substring(1);
        }
        if (s.indexOf("+") > -1 || s.indexOf("-") > -1) {
            return endVerifier(IFormController.validationError.INTEGER_INVALID, input);
        }
        // s has now the content without sign
        if (s.length() == 0) {
            if (isMandatory) {
                return endVerifier(IFormController.validationError.MANDATORY, input);
            }
            return endVerifier(IFormController.validationError.NO_ERROR, input);
        }
        // spaces between digits
        if (s.indexOf(" ") > -1) {                    
            return endVerifier(IFormController.validationError.INTEGER_SPACES, input);
        }
        if (s.length() > len) {                    
            return endVerifier(IFormController.validationError.INTEGER_MAXLEN, input);            
        }
        Integer n = new Integer(s);
        // min value excedeed
        if (n < minValue) {
            return endVerifier(IFormController.validationError.MIN_VALUE, input);
        }
        // max value excedeed
        if (n > maxValue) {
            return endVerifier(IFormController.validationError.MAX_VALUE, input);
        }
        return endVerifier(IFormController.validationError.NO_ERROR, input);
    }
}
