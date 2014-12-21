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
import java.util.Date;
import java.text.*;

/** 
 * GUI Timestamp Component verifier.
 * <br>$Id: TimestampVerifier.java 2 2006-11-15 09:21:35Z simeo $
 * 
 */
public class TimestampVerifier extends FieldVerifier {

    /**
     * Builds a TimestampVerifier.
     * @param fc FormController
     * @param isMandatory true if the field is mandatory
     */
    public TimestampVerifier(FormController fc, boolean isMandatory) {
        super(fc, isMandatory);
    }
   
    /**
     * Verifies the component
     * @param input JComponent to verify
     */  
    public boolean verify(JComponent input) {
        String s = ((JFormattedTextField) input).getText();
        if (s.equalsIgnoreCase("  /  /       :  :  ") || s.length() == 0) {
            if (isMandatory) {
                return endVerifier(IFormController.validationError.MANDATORY, input);
            }
            return endVerifier(IFormController.validationError.NO_ERROR, input);
        }
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            format.setLenient(false);
            Date d = format.parse(((JFormattedTextField) input).getText());
        } catch (Exception e) {
            return endVerifier(IFormController.validationError.TIMESTAMP_INVALID, input);                   
        }
        return endVerifier(IFormController.validationError.NO_ERROR, input);
    }
}