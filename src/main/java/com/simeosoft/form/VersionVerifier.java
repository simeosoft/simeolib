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
package com.simeosoft.form;

import javax.swing.*;
import java.text.*;
import java.util.regex.*;
/** 
 * GUI Text Component verifier.
 * Checks input field length.
 * $Id: VersionVerifier.java 2 2006-11-15 09:21:35Z simeo $
 * 
 */
public class VersionVerifier extends FieldVerifier {
    
    /**
     * Builds a VersionVerifier.
     * @param fc FormController
     * @param isMandatory true if the field is mandatory
     */
    public VersionVerifier(FormController fc, boolean isMandatory) {
        super(fc, isMandatory);
    }
    
    /* FIXME - update to new structure
    public VersionVerifier() {
    }
    public VersionVerifier(JLabel mess) {
        this.mess = mess;
    }
    public boolean verify(JComponent input) {
        Pattern p = Pattern.compile("\\d+.\\d+");
        Matcher m = p.matcher(((javax.swing.text.JTextComponent) input).getText());
        if (m.matches()) {
            doMessage(" ");                        
            return true;
        }
        input.setForeground(SwingUtils.COL_FFORE_ERROR);
        input.setBackground(SwingUtils.COL_FBACK_ERROR);                    
        doMessage("Il campo accetta solo valori del tipo: 99.99");            
        return false;
    }
     */
}