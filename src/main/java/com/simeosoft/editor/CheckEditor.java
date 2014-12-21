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

package com.simeosoft.editor;

import com.simeosoft.form.IFormListener;
import java.awt.Color;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;

/**
 * Checkbox editor for table cells; the field is a String of values 'Y'/'N'
 *
 * <br>$Id: CheckEditor.java 30 2007-09-11 15:20:38Z mauro $
 */
public class CheckEditor extends AbstractCellEditor implements TableCellEditor {

    private IFormListener ifl;
    
    public static final long serialVersionUID = 8838234L;
    JCheckBox check;
    
    /**
     * used to trigger a change event when exiting from the cell after really
     * modifying the state of the check
     */
    private boolean lastValue;

    /**
     * Create a new instance of CheckEditor.
     * @param ifl the form listener
     */
    public CheckEditor(IFormListener ifl) {
        this.ifl = ifl;
        check = new JCheckBox();
        check.setBackground(Color.WHITE);
        check.setHorizontalAlignment(SwingConstants.CENTER);
    }
    /**
     * Sets the initial checkbox state: if the value of the field is 'Y', the
     * checkbox is selected; any other value ('N', '' being the most probable)
     * leaves the component unselected
     */
    public Component getTableCellEditorComponent(JTable table,
            Object value,
            boolean isSelected,
            int row,
            int column) {

        lastValue = value.toString().equalsIgnoreCase("Y");
        check.setSelected(lastValue);
        return check;
    }

    public Object getCellEditorValue() {
        final boolean currValue = check.isSelected();
        if(lastValue != currValue) {
            lastValue = currValue;
            ifl.changed(null);
        }
        if (currValue) {
            return "Y";
        }
        return "N";
    }
}
