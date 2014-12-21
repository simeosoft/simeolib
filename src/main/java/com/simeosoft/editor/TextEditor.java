/**
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

import com.simeosoft.form.FieldException;
import com.simeosoft.form.FormController;
import com.simeosoft.form.IFormListener;
import java.awt.Color;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

/**
 * Text editor for table cells.
 *
 * <br>$Id: TextEditor.java 25 2007-09-06 08:18:24Z mauro $
 */
public class TextEditor extends DefaultCellEditor {
    static final long serialVersionUID = 12222330;    
    
    protected EventListenerList listenerList = new EventListenerList();
    
    protected ChangeEvent changeEvent = new ChangeEvent(this);
    
    private JTable jtable;
    
    private IFormListener fListener;
    
    private ITextChanged tChanged;
    
    private FormController fc;
    
    private JFormattedTextField jt;
    
    private JLabel jlMessage;
    
    private String descr = "";
    
    private int size = 0;
    
    private String startValue = "";
    
    /**
     * Creates a new instance of TextEditor with a DefaultTextChanged.
     *
     * @param jtable the table owner.
     * @param fListener the form listener.
     * @param tChanged interface for value changed (null if no action is required after cell editing)
     * @param jlMesssage the label for displaying errors and messages.
     * @param jt JFormattedTextField component.
     * @param descr field description.
     * @param size maximum size allowed.
     */
    public TextEditor(JTable jtable,IFormListener fListener, JLabel jlMesssage,
            JFormattedTextField jt,String descr,int size) {
        
        super(jt);
        this.jtable = jtable;
        this.fListener = fListener;
        this.tChanged = new DefaultTextChanged();
        this.jt = jt;
        this.jlMessage = jlMesssage;
        this.descr = descr;
        this.size = size;
    }
    
    /**
     * Creates a new instance of TextEditor.
     *
     * @param jtable the table owner.
     * @param fListener the form listener.
     * @param tChanged interface for value changed.
     * @param jlMesssage the label for displaying errors and messages.
     * @param jt JFormattedTextField component.
     * @param descr field description.
     * @param size maximum size allowed.
     */
    public TextEditor(JTable jtable,IFormListener fListener, ITextChanged tChanged,
            JLabel jlMesssage,JFormattedTextField jt,String descr,int size) {
        
        super(jt);
        this.jtable = jtable;
        this.fListener = fListener;
        this.tChanged = tChanged;
        this.jt = jt;
        this.jlMessage = jlMesssage;
        this.descr = descr;
        this.size = size;
    }
    
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        
        fc = new FormController(fListener,jlMessage);
        fc.addTextField(jt,descr,size,false);
        if (value != null) {
            startValue = value.toString();
        } else {
            startValue = "";
        }
        fc.setString(jt,startValue);
        jt.selectAll();
        return jt;
    }
    
    public Object getCellEditorValue() {
        if (jt.getText() == null || jt.getText().length() == 0) {
            return "";
        }
        return jt.getText();
    }
    
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }
    
    //viene chiamata appena termina la fase di editing (dopo pressione tasto tab. o muose click)
    public boolean stopCellEditing() {
        String value = "";
        if (jt.getText() != null) {
            value = jt.getText();
        }
        try {
            fc.check();
        } catch (FieldException ex) {
            // valore non corretto
            final String msg = ex.getMessage();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    jlMessage.setText(msg);
                }
            });
            return false;
        }
//        if (!startValue.equalsIgnoreCase(value) && tChanged != null) {            
//            if (!tChanged.checkTextChange(startValue,value)) {
//                return false;
//            }
//        }
        if (!startValue.equalsIgnoreCase(value) && !tChanged.checkTextChange(startValue,value)) {            
            return false;
        }
        // tutto ok, posso fermare la sessione di editing
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
    
    public void cancelCellEditing() {
        fireEditingCanceled();
    }
    
    public void addCellEditorListener(CellEditorListener l) {
        listenerList.add(CellEditorListener.class, l);
    }
    
    public void removeCellEditorListener(CellEditorListener l) {
        listenerList.remove(CellEditorListener.class, l);
    }
    
    protected void fireEditingCanceled() {
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == CellEditorListener.class) {
                listener = (CellEditorListener) listeners[i + 1];
                listener.editingCanceled(changeEvent);
            }
        }
    }
}
