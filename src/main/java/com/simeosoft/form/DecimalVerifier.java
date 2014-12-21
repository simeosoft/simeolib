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

import java.math.BigDecimal;
import javax.swing.*;


/** 
 * GUI Decimal Component verifier.
 * <br>$Id: DecimalVerifier.java 18 2007-06-15 14:40:16Z mauro $
 *
 */
public class DecimalVerifier extends FieldVerifier {
    int integers = 0;
    int decimals = 0;
    boolean signed = false;
    BigDecimal maxValue = null;
    BigDecimal minValue = null;
    
    
    public DecimalVerifier(FormController fc, int integers,int decimals, boolean isMandatory, boolean signed) {
        super(fc, isMandatory);
        this.integers = integers;
        this.decimals = decimals;
        this.signed = signed;
    }
    
    public DecimalVerifier(FormController fc, int integers,int decimals, 
            boolean isMandatory, boolean signed, BigDecimal maxValue, BigDecimal minValue) {
        super(fc, isMandatory);
        this.integers = integers;
        this.decimals = decimals;
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
        int len = integers + decimals + 1 + (signed ? 1 : 0);
        if (s.length() > len) {
            return endVerifier(IFormController.validationError.DECIMAL_INVALID, input);
        }
        for (int i = 0; i<s.length(); i++) {
            if (valid_chars.indexOf(s.charAt(i)) == -1) {
                return endVerifier(IFormController.validationError.DECIMAL_INVALID, input);    
            }
        }
        if (s.startsWith("+") || s.startsWith("-")) {
            s = s.substring(1);
        }
        if (s.indexOf("+") > -1 || s.indexOf("-") > -1) {
            return endVerifier(IFormController.validationError.DECIMAL_INVALID, input);                        
        }
        // s has now the content without sign
        if (s.length() == 0) {
            if (isMandatory) {
                return endVerifier(IFormController.validationError.MANDATORY, input);
            }
            return endVerifier(IFormController.validationError.NO_ERROR, input);
        }
        // spaces between digits
        if (s.indexOf(" ") > 0) {
            return endVerifier(IFormController.validationError.DECIMAL_SPACES, input);
        }
        // more than one decimal point
        int dec_offset = s.indexOf(fc.getDecimalPoint());
        if (dec_offset != s.lastIndexOf(fc.getDecimalPoint())) {
            return endVerifier(IFormController.validationError.DECIMAL_SEPARATOR, input);
        }
        String as[] = s.split("\\" + fc.getDecimalPoint());
        if (as.length > 0) {
            if (as[0].length() > integers) {
                return endVerifier(IFormController.validationError.DECIMAL_INTMAXLEN, input);
            }
        }
        if (as.length > 1) {
            if (as[1].length() > decimals) {
                return endVerifier(IFormController.validationError.DECIMAL_DECMAXLEN, input);
            }
        }
        // just put a trailing 0 or trailing ,0 if needed
        if (as.length == 0) {
            ((JFormattedTextField) input).setText("0" + fc.getDecimalPoint() + "0");
        }
        if (as.length == 1) {
            ((JFormattedTextField) input).setText(as[0] + fc.getDecimalPoint() + "0");
        }
        // min value excedeed
        BigDecimal value = new BigDecimal(((JFormattedTextField) input).getText());
        if (minValue != null) {
            if (value.compareTo(minValue) < 0) {
                return endVerifier(IFormController.validationError.MIN_VALUE, input);
            }
        }
        // max value excedeed
        if (maxValue != null) {
            if (value.compareTo(maxValue) > 0) {
                return endVerifier(IFormController.validationError.MAX_VALUE, input);
            }
        }
        return endVerifier(IFormController.validationError.NO_ERROR, input);
    }
}
