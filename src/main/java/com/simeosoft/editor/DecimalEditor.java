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
import java.math.BigDecimal;
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
 * Decimal editor for table cells.
 *
 * <br>$Id: DecimalEditor.java 35 2008-04-18 13:52:56Z simeo $
 */
public class DecimalEditor extends DefaultCellEditor {
    static final long serialVersionUID = 12222334;    
    
    protected EventListenerList listenerList = new EventListenerList();
    
    protected ChangeEvent changeEvent = new ChangeEvent(this);
    
    private JTable jtable;
    
    private IFormListener fListener;
    
    private IDecimalRange dRange;
    
    private FormController fc;
    
    private JFormattedTextField jt;
    
    private JLabel jlMessage;
    
    private String descr = "";
    
    private int integers = -1;
    
    private int decimals = -1;
    
    private boolean signed;
    
    private BigDecimal minValue;
    
    private BigDecimal maxValue;
    
    private boolean warning;
    
    /**
     * Creates a new instance of DecimalEditor with a DefaultDecimalRange.
     *   
     * @param jtable the cell owner.
     * @param fListener the form listener.
     * @param jlMesssage the label for displaying errors and messages.
     * @param jt JFormattedTextField component.
     * @param descr field description.
     * @param integers maximum number of digits (integer part).
     * @param decimals maximum number of digits (fractional part).
     * @param signed true if the field is signed.
     * @param warning true if the check() method does not block the editing.
     */
    public DecimalEditor(JTable jtable,IFormListener fListener, JLabel jlMesssage,
            JFormattedTextField jt,String descr,int integers, int decimals, 
            boolean signed, boolean warning) {
        
        super(jt);
        this.jtable = jtable;
        this.fListener = fListener;
        this.dRange = new DefaultDecimalRange(integers, decimals, signed);
        this.jt = jt;
        this.jlMessage = jlMesssage;
        this.descr = descr;
        this.integers = integers;
        this.decimals = decimals;
        this.signed = signed;
        this.warning = warning;
    }
    
    /**
     * Creates a new instance of DecimalEditor.
     *   
     * @param jtable the cell owner.
     * @param fListener the form listener.
     * @param dRange interface for the range value.
     * @param jlMesssage the label for displaying errors and messages.
     * @param jt JFormattedTextField component.
     * @param descr field description.
     * @param integers maximum number of digits (integer part).
     * @param decimals maximum number of digits (fractional part).
     * @param signed true if the field is signed.
     * @param warning true if the check() method does not block the editing.
     */
    public DecimalEditor(JTable jtable,IFormListener fListener,IDecimalRange dRange, JLabel jlMesssage,
            JFormattedTextField jt,String descr,int integers, int decimals, boolean signed, boolean warning) {
        
        super(jt);
        this.jtable = jtable;
        this.fListener = fListener;
        this.dRange = dRange;
        this.jt = jt;
        this.jlMessage = jlMesssage;
        this.descr = descr;
        this.integers = integers;
        this.decimals = decimals;
        this.signed = signed;
        this.warning = warning;
    }
    
    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        
        fc = new FormController(fListener,jlMessage);
        try {
            minValue = dRange.getMinDecimal(row,column);
            maxValue = dRange.getMaxDecimal(row,column);
            if (minValue != null && maxValue != null) {
                fc.addDecimalField(jt,descr,integers,decimals,false,signed,maxValue,minValue);
            } else {
                fc.addDecimalField(jt,descr,integers,decimals,false,signed);
            }
        } catch (AddFieldException ex) {
            return null;
        }
        fc.setDecimal(jt, (BigDecimal) value);
        jt.selectAll();
        return jt;
    }
    
    /**
     * Called when the value editated in the filed is correct.
     */
    @Override
    public Object getCellEditorValue() {
        if (jt.getText() == null || jt.getText().length() == 0) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(jt.getText());
    }
    
    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }
       
    /**
     * Called when the editing is complete: check the value in the cell and 
     * return true if it is correct.
     */
    @Override
    public boolean stopCellEditing() {
        try {
            BigDecimal b;
            if (jt.getText() == null || jt.getText().length() == 0) {
                b = new BigDecimal(0);
            } else {
                b = new BigDecimal(jt.getText());
            }            
            fc.check();
        } catch (FieldException fe) { 
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
        } catch(NumberFormatException e) {            
            final String msg = "formato non valido";
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    jlMessage.setText(msg);
                }
            });
            return false;
        }
        // 
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
