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

import com.simeosoft.form.IFormController.regexType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

/** 
 * GUI Text Component verifier from regular expressions.
 * <br>$Id: RegexVerifier.java 2 2006-11-15 09:21:35Z simeo $
 * 
 */
public class RegexVerifier extends FieldVerifier {
    int len = 0;
    Pattern pattern;
    String hint = null;
    
    /**
     * Builds a RegexVerifier.
     * @param fc FormController
     * @param len Field length
     * @param isMandatory true if the field is mandatory
     * @param regexType type of field
     */
    public RegexVerifier(FormController fc, int len, boolean isMandatory, regexType type) {
        super(fc, isMandatory);
        this.len = len;
        pattern = Pattern.compile(type.getPattern());
    }
   
    /**
     * Verifies the component
     * @param input JComponent to verify
     */    
    public boolean verify(JComponent input) {
        String s = ((javax.swing.text.JTextComponent) input).getText().trim();

        if (s.length()==0) {
            if (isMandatory) {
                return endVerifier(IFormController.validationError.MANDATORY, input);
            }
            return endVerifier(IFormController.validationError.NO_ERROR, input);
        }
        if (s.length() <= len) {
            Matcher matcher = pattern.matcher(s);
            if (! matcher.matches()) {
                return endVerifier(IFormController.validationError.REGEX_INVALID, input);
            }
            return endVerifier(IFormController.validationError.NO_ERROR, input);
        }                  
        return endVerifier(IFormController.validationError.TEXT_MAXLEN, input);            
    }
}