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

import com.simeosoft.form.IFormController.validationError;
import javax.swing.InputVerifier;
import javax.swing.JComponent;

/**
 * GUI Component verifier superclass.
 * <br>$Id: FieldVerifier.java 2 2006-11-15 09:21:35Z simeo $
 * 
 */
public abstract class FieldVerifier extends InputVerifier {
   
    FormController fc = null;
    validationError retCode = validationError.NO_ERROR;
    boolean isMandatory = false;
    
    /**
     * Builds a FieldVerifier.
     * @param fc FormController
     * @param isMandatory true if the field is mandatory
     */
    public FieldVerifier(FormController fc, boolean isMandatory) {
        this.fc = fc;
        this.isMandatory = isMandatory;
    }
    
    /**
     * Verifies the component
     * @param input JComponent to verify
     */    
    public boolean verify(JComponent input) {
        return true;
    }
    
    /**
     * Returns the validation error
     * @return validation error
     */    
    public validationError getRetCode() {
        return retCode;
    }

    /**
     * Called when the verifier completes process.
     * @param ve validation error
     * @param input JComponent source
     * @return true if validation passed
     */
    public boolean endVerifier(FormController.validationError ve, JComponent input) {
        retCode = ve;
        fc.doMessage(ve, input);
        if(ve == IFormController.validationError.NO_ERROR) {
            return true;
        }
        return false;
    }
}
