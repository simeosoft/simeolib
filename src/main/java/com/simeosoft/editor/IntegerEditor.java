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

import com.simeosoft.form.AddFieldException;
import com.simeosoft.form.FieldException;
import com.simeosoft.form.FormController;
import com.simeosoft.form.IFormListener;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

/**
 * Integer editor for table cells.
 *
 * <br>$Id: IntegerEditor.java 35 2008-04-18 13:52:56Z simeo $
 */
public class IntegerEditor extends DefaultCellEditor{
    static final long serialVersionUID = 12222331;    
    
    protected EventListenerList listenerList = new EventListenerList();
    
    protected ChangeEvent changeEvent = new ChangeEvent(this);
    
    private JTable jtable;
    
    private IFormListener fListener;
    
    private IIntegerRange iRange;
    
    private FormController fc;
    
    private JFormattedTextField jt;
    
    private JLabel jlMessage;
    
    private String descr = "";
    
    private int lenght;
    
    private boolean signed;
    
    private int maxValue = -1;
    
    private int minValue = -1;

    private boolean warning;
    
    /**
     * Creates a new instance of IntegerEditor with a DefaultIntegerRange.
     *
     * @param jtable the cell owner.
     * @param fListener the form listener.     
     * @param jlMesssage the label for displaying errors and messages.
     * @param jt JFormattedTextField component.
     * @param descr field description.     
     * @param lenght aximum size allowed.
     * @param signed true if the field is signed.
     * @param warning true if the check() method does not block the editing.
     */
    public IntegerEditor(JTable jtable,IFormListener fListener,JLabel jlMesssage,
            JFormattedTextField jt,String descr,int lenght, boolean signed, boolean warning) {
        
        super(jt);
        this.jtable = jtable;
        this.fListener = fListener;
        this.iRange = new DefaultIntegerRange(lenght, signed);
        this.jt = jt;
        this.jlMessage = jlMesssage;
        this.descr = descr;
        this.lenght = lenght;
        this.signed = signed;
        this.warning = warning;
    }
    
    /**
     * Creates a new instance of IntegerEditor.
     *
     * @param jtable the cell owner.
     * @param fListener the form listener.
     * @param dRange interface for the range value.
     * @param jlMesssage the label for displaying errors and messages.
     * @param jt JFormattedTextField component.
     * @param descr field description.     
     * @param lenght aximum size allowed.
     * @param signed true if the field is signed.
     * @param warning true if the check() method does not block the editing.
     */
    public IntegerEditor(JTable jtable,IFormListener fListener,IIntegerRange iRange, JLabel jlMesssage,
            JFormattedTextField jt,String descr,int lenght, boolean signed, boolean warning) {
        
        super(jt);
        this.jtable = jtable;
        this.fListener = fListener;
        this.iRange = iRange;
        this.jt = jt;
        this.jlMessage = jlMesssage;
        this.descr = descr;
        this.lenght = lenght;
        this.signed = signed;
        this.warning = warning;
    }
    
    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        
        fc = new FormController(fListener,jlMessage);
        try {
            minValue = iRange.getMinInteger(row,column);
            maxValue = iRange.getMaxInteger(row,column);
            if (minValue == -1 && maxValue == -1) {
                fc.addIntegerField(jt,descr,lenght,false,signed);
            } else {
                fc.addIntegerField(jt,descr,lenght,false,signed,maxValue,minValue);
            }
        } catch (AddFieldException ex) {
            return null;
        }
        fc.setInt(jt, ((Integer) value).intValue());
        jt.selectAll();
        return jt;
    }
    
    @Override
    public Object getCellEditorValue() {
        return fc.getInt(jt);
    }
    
    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }
    
    //viene chiamata appena termina la fase di editing (dopo pressione tasto tab. o muose click)
    @Override
    public boolean stopCellEditing() {
        try {
            fc.check();
        } catch(FieldException fe) {
            if (warning) {
                JOptionPane.showMessageDialog(jtable,fe.getMessage(),"Input Warning",JOptionPane.WARNING_MESSAGE);
            } else {            
                final String msg = fe.getMessage();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        jlMessage.setText(msg);
                    }
                });
                return false;
            }
        }
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
    
    @Override
    public void cancelCellEditing() {
        fireEditingCanceled();
    }
    
    @Override
    public void addCellEditorListener(CellEditorListener l) {
        listenerList.add(CellEditorListener.class, l);
    }
    
    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        listenerList.remove(CellEditorListener.class, l);
    }
    
    @Override
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
