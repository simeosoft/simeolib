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
import java.awt.Component;
import java.util.Map;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 * Combobox editor for table cells; every combo item has a value and a screen
 * representation.
 *
 * <br>$Id: ComboEditor.java 35 2008-04-18 13:52:56Z simeo $
 */
public class ComboEditor extends AbstractCellEditor implements TableCellEditor {

    public static final long serialVersionUID = 2272876L;

    // perhaps keys and values would sound more familiar, but this way it's
    // clearer what's happening behind the scenes
    private Object[] values;
    private Object[] screenValues;
    private JComboBox combo;   

    private IFormListener ifl;
    
    private int lastIndex = -1;
    
    /**
     * Creates a new instance of ComboEditor with specified values, which will
     * be used for representation too.
     *      
     * @param values an array of <code>Object</code>s specifying the combo items
     * @param ifl the form listener
     */
    public ComboEditor(Object[] values, IFormListener ifl) {
        this.values = values;
        this.screenValues = values;        
        this.ifl = ifl;
        combo = new JComboBox(values);
    }

    /**
     * Creates a new instance of ComboEditor by a map.
     *      
     * @param map a <code>Map</code>: its values will be displayed in the combo,
     * @param ifl the form listener
     * but its keys will be returned when asking the value of the selected item.
     */
    public ComboEditor(Map map, IFormListener ifl) {
        this.values = map.keySet().toArray();
        this.screenValues = map.values().toArray();
        this.ifl = ifl;
        combo = new JComboBox(screenValues);
    }

    /**
     * Initializes the combo positioning it to the item displayed as the param
     * <code>value</code>
     */
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        final String sval = (String) value;
        for(int i = 0; i < screenValues.length; ++i) {
            if(value.equals(screenValues[i])) {
                lastIndex = i;
                combo.setSelectedIndex(i);
                break;
            }
        }
        return combo;
    }

    /**
     * returns the value of the selected item
     */
    public Object getCellEditorValue() {
        final int currIndex = combo.getSelectedIndex();
        if(currIndex != lastIndex) {
            lastIndex = currIndex;
            ifl.changed(null);
        }
        if (currIndex > -1) {
            return (String) values[combo.getSelectedIndex()];
        }
        return "";
    }
    
    @Override
    public boolean stopCellEditing() {        
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == CellEditorListener.class) {
                listener = (CellEditorListener) listeners[i + 1];
                listener.editingStopped(changeEvent);                
            }
        }
        return true;
    }

}
