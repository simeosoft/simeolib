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

import javax.swing.*;

/** 
 * GUI Time Component verifier.
 * <br>$Id: TimeVerifier.java 2 2006-11-15 09:21:35Z simeo $
 * 
 */
public class TimeVerifier extends FieldVerifier {
    
    /**
     * Builds a TimeVerifier.
     * @param fc FormController
     * @param isMandatory true if the field is mandatory
     */
    public TimeVerifier(FormController fc, boolean isMandatory) {
        super(fc, isMandatory);
    }
   
    /**
     * Verifies the component
     * @param input JComponent to verify
     */     
    public boolean verify(JComponent input) {
        String s = ((JFormattedTextField) input).getText();
        if (s.equalsIgnoreCase("  :  ") || s.length() == 0) {
            if (isMandatory) {
                return endVerifier(IFormController.validationError.MANDATORY, input);
            }
            return endVerifier(IFormController.validationError.NO_ERROR, input);
        }
        int hour;
        int min;
        String s1 = s.substring(0,2);
        String s2 = s.substring(3,5);        
        try {
            hour = Integer.parseInt(s1);
        } catch (NumberFormatException nfe) {                  
            return endVerifier(IFormController.validationError.TIME_FORM, input);            
        }
        if (hour > 23) {                   
            return endVerifier(IFormController.validationError.TIME_HOUR, input);            
        }
        try {
            min = Integer.parseInt(s2);
        } catch (NumberFormatException nfe) {                  
            return endVerifier(IFormController.validationError.TIME_FORM_MINUTE, input);            
        }
        if (min > 59) {                   
            return endVerifier(IFormController.validationError.TIME_MINUTE, input);            
        }
        return endVerifier(IFormController.validationError.NO_ERROR, input);
    }
}