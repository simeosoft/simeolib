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
import com.simeosoft.string.StringUtils;
import com.simeosoft.swing.SwingUtils;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * FormController is a component controller. Wraps itself on the
 * form components setting defaults behaviour.
 * There are setters ang getters for correct display of some
 * useful types of data.
 * FIXME - add a generic field verifier(regex pattern)
 * TODO - add setters from resultset (setRsString("FIELD NAME")) with exception
 * <br>$Id: FormController.java 16 2007-06-11 10:40:29Z simeo $
 *
 */
public class FormController implements KeyListener, FocusListener, ItemListener, IFormController {
    
    private IFormListener fl;
    private ArrayList<JComponent> alc = new ArrayList<JComponent>();
    private ArrayList<String> als = new ArrayList<String>();
   
    // should be a preference
    private boolean changeColorOnError = true;
    private String decimal_point = ".";
    private Color col_ffore_in = new Color(139,101,8);
    private Color col_fback_in = new Color(250,235,215);
    private Color col_ffore_out = new Color(113,122,117);
    private Color col_fback_out = new Color(248,248,255);
    private Color col_ffore_error = new Color(255,255,255);
    private Color col_fback_error = new Color(255,0,0);
    private Logger logger = null;
    private JLabel jlmsg = null;
    
    /** 
     * Creates a new instance of FormController.
     * @param fl the form listener.
     * @param jlmsg the optional label for displaying errors and messages.
     */
    public FormController(IFormListener fl, JLabel jlmsg) {
        this.fl = fl;
        this.jlmsg = jlmsg;
        refreshLocale();
    }
    
    /** 
     * Creates a new instance of FormController.
     * @param fl the form listener.
     * @param jlmsg the optional label for displaying errors and messages.
     * @param log the optional logger.
     */
    public FormController(IFormListener fl, JLabel jlmsg, Logger log) {
        this.fl = fl;
        this.jlmsg = jlmsg;
        logger = log;
        refreshLocale();
    }
    
    /**
     * Test constructor
     */
    public FormController() {
        this.fl = null;
        this.jlmsg = null;
        logger = null;
        refreshLocale();
    }
    
    /**
     * Add localized descriptions to error messages.
     * Can be called from form listeners if locale
     * changes dinamically. Be sure to reload locale-dependent stuff;
     */
    public void refreshLocale() {
        validationError.DATE_INVALID.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_DATE_INVALID"));
        validationError.DECIMAL_DECMAXLEN.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_DECIMAL_DECMAXLEN"));
        validationError.DECIMAL_INTMAXLEN.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_DECIMAL_INTMAXLEN"));
        validationError.DECIMAL_MAXLEN.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_DECIMAL_MAXLEN"));        
        validationError.DECIMAL_SEPARATOR.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_DECIMAL_SEPARATOR"));
        validationError.DECIMAL_SPACES.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_DECIMAL_SPACES"));
        validationError.DECIMAL_INVALID.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_DECIMAL_INVALID"));
        validationError.INTEGER_MAXLEN.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_INTEGER_MAXLEN"));
        validationError.INTEGER_SPACES.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_INTEGER_SPACES"));
        validationError.INTEGER_INVALID.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_INTEGER_INVALID"));
        validationError.TEXT_MAXLEN.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_TEXT_MAXLEN"));
        validationError.TIMESTAMP_INVALID.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_TIMESTAMP_INVALID"));
        validationError.TIME_FORM.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_TIME_FORM"));
        validationError.TIME_FORM_MINUTE.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_TIME_FORM_MINUTE"));
        validationError.TIME_HOUR.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_TIME_HOUR"));
        validationError.TIME_MINUTE.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_TIME_MINUTE"));
        validationError.MANDATORY.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_MANDATORY"));
        validationError.TEXT_INVALID.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_TEXT_INVALID"));        
        validationError.REGEX_INVALID.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("REGEX_INVALID"));        
        validationError.MAX_VALUE.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_MAX_VALUE"));        
        validationError.MIN_VALUE.setDesc(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_MIN_VALUE"));        
    }
    
    /**
     * Sets the decimal point
     * @param s decimal point
     */
    public void setDecimalPoint(String s) {
        decimal_point = s;
        for (int i=0;i<alc.size();i++) {
            JComponent jc = alc.get(i);
            if (jc.getClientProperty(IFormController.PROPERTY_TYPE) == IFormController.PROPERTY_VALUE_DECIMAL) {
                String valchars = (String) jc.getClientProperty(IFormController.PROPERTY_VALID_CHARS);
                jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS, valchars.substring(0, valchars.length() -1) + decimal_point);
            }
        }
        
    }

    /**
     * Gets the decimal point
     * @return decimal point
     */
    public String getDecimalPoint() {
        return decimal_point;
    }

    /**
     * Adds a text field to the controller.
     * @param jc JFormattedTextField component.
     * @param desc field description.
     * @param size maximum size allowed.
     * @param mandatory true if the field is mandatory (no blank allowed).
     * @throws AddFieldException
     */
    public void addTextField(JFormattedTextField jc, String desc, int size, boolean mandatory) {
        jc.putClientProperty(IFormController.PROPERTY_LENGTH, new Integer(size));        
        jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS, null);
        jc.putClientProperty(IFormController.PROPERTY_TYPE, IFormController.PROPERTY_VALUE_TEXT);        
        TextVerifier tv = new TextVerifier(this, size, mandatory);
        jc.setCaretPosition(0);
        jc.addKeyListener(this);
        jc.addFocusListener(this);
        jc.setBackground(col_fback_out);
        jc.setForeground(col_ffore_out);
        if (tv != null) {
            jc.setInputVerifier(tv);
        }
        jc.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        
        alc.add(jc);
        als.add(desc);
    }

    /**
     * Adds a text field to the controller.
     * @param jc JTextArea component.
     * @param desc field description.
     * @param size maximum size allowed.
     * @param mandatory true if the field is mandatory (no blank allowed).
     */
    public void addTextField(JTextArea jc, String desc, int size, boolean mandatory) {
        TextVerifier tv = new TextVerifier(this, size, mandatory);
        jc.putClientProperty(IFormController.PROPERTY_LENGTH, new Integer(size));        
        jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS, null);
        jc.putClientProperty(IFormController.PROPERTY_TYPE, IFormController.PROPERTY_VALUE_TEXT);        
        jc.addKeyListener(this);
        jc.addFocusListener(this);
        jc.setBackground(col_fback_out);
        jc.setForeground(col_ffore_out);
        if (tv != null) {
            jc.setInputVerifier(tv);
        }
        jc.setCaretPosition(0);        
        jc.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        alc.add(jc);
        als.add(desc);
        // inverting focus traversal behaviour
		SwingUtils.invertFocusTraversalBehaviour(jc);        
    }
    
    /**
     * Adds a text field to the controller.
     * @param jc JFormattedTextField component.
     * @param desc field description.
     * @param size maximum size allowed.
     * @param mandatory true if the field is mandatory (no blank allowed).
     * @param regexpType type of field
     * @throws AddFieldException
     */
    public void addTextField(JFormattedTextField jc, String desc, int size, boolean mandatory, regexType type) {
        jc.putClientProperty(IFormController.PROPERTY_LENGTH, new Integer(size));        
        jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS, type.getValidChars());
        jc.putClientProperty(IFormController.PROPERTY_TYPE, IFormController.PROPERTY_VALUE_TEXT);        
        RegexVerifier rv = new RegexVerifier(this, size, mandatory, type);
        jc.setCaretPosition(0);
        jc.addKeyListener(this);
        jc.addFocusListener(this);
        jc.setBackground(col_fback_out);
        jc.setForeground(col_ffore_out);
        jc.setInputVerifier(rv);
        jc.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        
        alc.add(jc);
        als.add(desc);
    }

    /**
     * Adds an integer field to the controller.
     * @param jc JFormattedTextField component.
     * @param desc field description.
     * @param size maximum size allowed.
     * @param mandatory true if the field is mandatory (no blank allowed).
     * @param signed true if the field is signed.
     * @throws AddFieldException
     */
    public void addIntegerField(JFormattedTextField jc, String desc, int size, boolean mandatory, boolean signed)
                throws AddFieldException {
        if (size > 10) {
            throw new AddFieldException(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_CANTADD") + desc + java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_SIZE"));
        }
        if (signed) {
            size++;
        }
        IntegerVerifier iv = new IntegerVerifier(this, size, mandatory, signed);
        jc.putClientProperty(IFormController.PROPERTY_LENGTH, new Integer(size));        
        jc.putClientProperty(IFormController.PROPERTY_TYPE, IFormController.PROPERTY_VALUE_INTEGER);        
        if (signed) {
            jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS,"0123456789-+ ");
        } else {
            jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS,"0123456789 ");
        }
        jc.setCaretPosition(0);
        jc.addKeyListener(this);
        jc.addFocusListener(this);
        jc.setInputVerifier(iv);
        jc.setBackground(col_fback_out);
        jc.setForeground(col_ffore_out);
        jc.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        
        alc.add(jc);
        als.add(desc);
    }
    
    /**
     * Adds an integer field to the controller.
     * @param jc JFormattedTextField component.
     * @param desc field description.
     * @param size maximum size allowed.
     * @param mandatory true if the field is mandatory (no blank allowed).
     * @param signed true if the field is signed.
     * @param maxValue max value allowed
     * @param minValue min value allowed
     * @throws AddFieldException 
     */
    public void addIntegerField(JFormattedTextField jc, String desc, 
            int size, boolean mandatory, boolean signed, int maxValue, int minValue) throws AddFieldException {
        if (size > 10) {
            throw new AddFieldException(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_CANTADD") + desc + java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_SIZE"));
        }
        if (signed) {
            size++;
        }
        IntegerVerifier iv = new IntegerVerifier(this, size, mandatory, signed, maxValue, minValue);
        jc.putClientProperty(IFormController.PROPERTY_LENGTH, new Integer(size));        
        jc.putClientProperty(IFormController.PROPERTY_TYPE, IFormController.PROPERTY_VALUE_INTEGER);        
        if (signed) {
            jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS,"0123456789-+ ");
        } else {
            jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS,"0123456789 ");
        }
        jc.setCaretPosition(0);
        jc.addKeyListener(this);
        jc.addFocusListener(this);
        jc.setInputVerifier(iv);
        jc.setBackground(col_fback_out);
        jc.setForeground(col_ffore_out);
        jc.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        
        alc.add(jc);
        als.add(desc);
    }
    
    /**
     * Adds a date field to the controller.
     * @param jc JFormattedTextField component.
     * @param desc field description.
     * @param mandatory true if the field is mandatory (no blank allowed).
     * @throws AddFieldException
     */
    public void addDateField(JFormattedTextField jc, String desc, boolean mandatory)
                throws AddFieldException {
        jc.putClientProperty(IFormController.PROPERTY_LENGTH, 0);        
        jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS, null);
        jc.putClientProperty(IFormController.PROPERTY_TYPE, IFormController.PROPERTY_VALUE_DATE);        
        DateVerifier dv = new DateVerifier(this, mandatory);
        MaskFormatter mf = null;
        if (logger != null) {
            logger.debug("building date Component");
        }
        try {
            // default date...
            String pattern = "##/##/####";
            if (mf != null) {
                mf.uninstall();
            }
            mf = new MaskFormatter(pattern);
            mf.install(jc);
            
        } catch (Exception e) {
            if (logger != null) {
                logger.warn(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_FORMATTING") + e);
            }
            throw new AddFieldException(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_CANTADD") + desc + ". " + e.getLocalizedMessage());
        }
        
        jc.setCaretPosition(0);        
        jc.addKeyListener(this);
        jc.addFocusListener(this);
        jc.setInputVerifier(dv);
        jc.setBackground(col_fback_out);
        jc.setForeground(col_ffore_out);
        jc.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        
        alc.add(jc);
        als.add(desc);
    }
    
    /**
     * Adds a decimal field to the controller.
     * @param jc JFormattedTextField component.
     * @param desc field description.
     * @param integers maximum number of digits (integer part).
     * @param decimals maximum number of digits (fractional part).
     * @param mandatory true if the field is mandatory (no blank allowed).
     * @param signed true if the field is signed.
     * @throws AddFieldException
     */
    public void addDecimalField(JFormattedTextField jc, String desc, int integers, int decimals,
                                boolean mandatory, boolean signed)
                throws AddFieldException {
        if (integers == 0 || decimals == 0 ||
            integers > 10 || decimals > 5) {
            throw new AddFieldException(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_CANTADD") + desc + java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("INTEGERS") + integers +
                    java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("DECIMALS") + decimals + java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("CAPTION_INT_DEC"));
        }
        DecimalVerifier dv = new DecimalVerifier(this,integers, decimals, mandatory, signed);
        jc.putClientProperty(IFormController.PROPERTY_INTEGERS, integers);
        jc.putClientProperty(IFormController.PROPERTY_DECIMALS, decimals);
        jc.putClientProperty(IFormController.PROPERTY_LENGTH, integers + decimals + 1 + (signed ? 1 : 0));        
        jc.putClientProperty(IFormController.PROPERTY_TYPE, IFormController.PROPERTY_VALUE_DECIMAL);    
        if (signed) {            
            jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS, "0123456789+- " + decimal_point);
        } else {
            jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS, "0123456789 " + decimal_point);
        }        
        jc.setCaretPosition(0);        
        jc.addKeyListener(this);
        jc.addFocusListener(this);
        jc.setInputVerifier(dv);
        jc.setBackground(col_fback_out);
        jc.setForeground(col_ffore_out);
        jc.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        alc.add(jc);
        als.add(desc);
    }
    
    /**
     * Adds a decimal field to the controller.
     * @param jc JFormattedTextField component.
     * @param desc field description.
     * @param integers maximum number of digits (integer part).
     * @param decimals maximum number of digits (fractional part).
     * @param mandatory true if the field is mandatory (no blank allowed).
     * @param signed true if the field is signed.
     * @param maxValue max value allowed
     * @param minValue min value allowed
     * @throws AddFieldException 
     */
    public void addDecimalField(JFormattedTextField jc, String desc, int integers, int decimals,
                                boolean mandatory, boolean signed, BigDecimal maxValue, BigDecimal minValue)
                throws AddFieldException {
        if (integers == 0 || decimals == 0 ||
            integers > 10 || decimals > 5) {
            throw new AddFieldException(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_CANTADD") + desc + java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("INTEGERS") + integers +
                    java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("DECIMALS") + decimals + java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("CAPTION_INT_DEC"));
        }
        DecimalVerifier dv = new DecimalVerifier(this,integers, decimals, mandatory, signed,maxValue,minValue);
        jc.putClientProperty(IFormController.PROPERTY_INTEGERS, integers);
        jc.putClientProperty(IFormController.PROPERTY_DECIMALS, decimals);
        jc.putClientProperty(IFormController.PROPERTY_LENGTH, integers + decimals + 1 + (signed ? 1 : 0));        
        jc.putClientProperty(IFormController.PROPERTY_TYPE, IFormController.PROPERTY_VALUE_DECIMAL);   
        jc.putClientProperty(IFormController.PROPERTY_MAX_VALUE, maxValue);
        jc.putClientProperty(IFormController.PROPERTY_MIN_VALUE, minValue);
        if (signed) {            
            jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS, "0123456789+- " + decimal_point);
        } else {
            jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS, "0123456789 " + decimal_point);
        }        
        jc.setCaretPosition(0);        
        jc.addKeyListener(this);
        jc.addFocusListener(this);
        jc.setInputVerifier(dv);
        jc.setBackground(col_fback_out);
        jc.setForeground(col_ffore_out);
        jc.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        alc.add(jc);
        als.add(desc);
    }
    
    /**
     * Adds a time field to the controller.
     * @param jc JFormattedTextField component.
     * @param desc field description.
     * @param mandatory true if the field is mandatory (no blank allowed).
     * @throws AddFieldException
     */
    public void addTimeField(JFormattedTextField jc, String desc, boolean mandatory)
                throws AddFieldException {
        jc.putClientProperty(IFormController.PROPERTY_LENGTH, 0);        
        jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS, null);
        jc.putClientProperty(IFormController.PROPERTY_TYPE, IFormController.PROPERTY_VALUE_DATE);        
        TimeVerifier tv = new TimeVerifier(this, mandatory);
        MaskFormatter mf = null;
        if (logger != null) {
            logger.debug(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("BUILD_TIME"));
        }
        try {
            String pattern = "##:##";
            if (mf != null) {
                mf.uninstall();
            }
            mf = new MaskFormatter(pattern);
            mf.install(jc);
        } catch (Exception e) {
            if (logger != null) {
                logger.warn(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_FORMATTING") + e);
            }
            throw new AddFieldException(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_CANTADD") + desc + ". " + e.getLocalizedMessage());
        }
        jc.setCaretPosition(0);        
        jc.addKeyListener(this);
        jc.addFocusListener(this);
        jc.setInputVerifier(tv);
        jc.setBackground(col_fback_out);
        jc.setForeground(col_ffore_out);
        jc.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        
        alc.add(jc);
        als.add(desc);
    }
    
    /**
     * Adds a timestamp field to the controller.
     * @param jc JFormattedTextField component.
     * @param desc field description.
     * @param mandatory true if the field is mandatory (no blank allowed).
     * @throws AddFieldException
     */
    public void addTimestampField(JFormattedTextField jc, String desc, boolean mandatory)
                throws AddFieldException {
        jc.putClientProperty(IFormController.PROPERTY_LENGTH, 0);        
        jc.putClientProperty(IFormController.PROPERTY_VALID_CHARS, null);
        jc.putClientProperty(IFormController.PROPERTY_TYPE, IFormController.PROPERTY_VALUE_DATE);        
        TimestampVerifier tsv = new TimestampVerifier(this, mandatory);
        MaskFormatter mf = null;
        if (logger != null) {
            logger.debug(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("BUILD_TIMESTAMP"));
        }
        try {
            String pattern = "##/##/#### ##:##:##";
            if (mf != null) {
                mf.uninstall();
            }
            mf = new MaskFormatter(pattern);
            mf.install(jc);
        } catch (Exception e) {
            if (logger != null) {
                logger.warn(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_FORMATTING") + e);
            }
            throw new AddFieldException(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_CANTADD") + desc + ". " + e.getLocalizedMessage());
        }
        jc.setCaretPosition(0);        
        jc.addKeyListener(this);
        jc.addFocusListener(this);
        jc.setInputVerifier(tsv);
        jc.setBackground(col_fback_out);
        jc.setForeground(col_ffore_out);
        jc.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        alc.add(jc);
        als.add(desc);
    }
    
    /**
     * Clears all fields of form.
     */
    public void clear() {
        for (JComponent jc : alc) {
            ((JTextComponent) jc).setText("");
            ((JTextComponent) jc).setCaretPosition(0);
            jc.setBackground(col_fback_out);
            jc.setForeground(col_ffore_out);
        }
    }
    
    /**
     * Enables od disables all fields of form.
     * @param enable enable
     */
    public void setEnabled(boolean enable) {
        for (JComponent jc : alc) {
            jc.setEnabled(enable);
        }
    }
    
    /**
     * Checks formal validity of form fields.
     * If MultipleFieldException is thrown, errors can be obtained
     * calling <code>getErrors()</code>.
     * @throws SingleFieldException if error on a field.
     * @throws MultipleFieldException if multiple errors. 
     */
    public void check() throws FieldException {
        JComponent focusComp = null;
        ArrayList<String> err = new ArrayList<String>();
        ArrayList<String> desc = new ArrayList<String>();
        for (int i=0;i<alc.size();i++) {
            JComponent jc = alc.get(i);
            FieldVerifier fv = (FieldVerifier) jc.getInputVerifier();
            fv.verify(jc);
            if(fv.getRetCode() != IFormController.validationError.NO_ERROR ) {
                if (focusComp == null) {
                    focusComp = jc;
                }
                err.add(fv.getRetCode().getDesc());
                desc.add(als.get(i));
            }
        }
        
        if(err.size()>0) {
            throw new FieldException(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ERR_FIELD"),
                    err, desc, focusComp);
        }
    }
    
    /**
     * Displays the error message if the target component is
     * defined. Change the source component background and
     * foreground according to preferences.
     * @param ve validation error.
     * @param j source component origin of error.
     */
    public void doMessage(validationError ve, JComponent j) {
        if(ve == validationError.NO_ERROR) {
            if (jlmsg != null) jlmsg.setText("");
        } else {
            if (jlmsg != null) {
                jlmsg.setText(ve.getDesc());
            }
            if (changeColorOnError) {
                j.setForeground(col_ffore_error);
                j.setBackground(col_fback_error);
            }
        }
    }
    
    /**
     * Sets the text of the component.
     * @param jc JFormattedTextField target.
     * @param text the new text to be set.
     */
    public void setString(JFormattedTextField jc, String text) {
        jc.setText(text);
        jc.setCaretPosition(0);
    }
    
    /**
     * Gets the text of the component as String.
     * @param jc JFormattedTextField source.
     */
    public String getString(JFormattedTextField jc) {
        return StringUtils.rtrim(jc.getText());
    }
    
    /**
     * Sets the text of the component.
     * @param jc JTextArea target.
     * @param text the new text to be set.
     */
    public void setString(JTextArea jc, String text) {
        jc.setText(text);
        jc.setCaretPosition(0);
    }
    
    /**
     * Gets the text of the component as String.
     * @param jc JTextArea source.
     */
    public String getString(JTextArea jc) {
        return StringUtils.rtrim(jc.getText());
    }
    
    /**
     * Sets the text of the component as integer.
     * @param jc JFormattedTextField target.
     * @param i the int value to be set.
     */
    public void setInt(JFormattedTextField jc, int i) {
        int size = (Integer) jc.getClientProperty(IFormController.PROPERTY_LENGTH);
        String text = formatIntToString(i, size);
        jc.setText(text);
        jc.setCaretPosition(0);
    }
    
    /**
     * Gets the text of the component as int.
     * @param jc JFormattedTextField source.
     */
    public int getInt(JFormattedTextField jc) {
        String text = jc.getText();
        return formatStringToInt(text);
    }
    
    /**
     * Sets the text of the component as time.
     * @param jc JFormattedTextField target.
     * @param d the date value to be set.
     */
    public void setTime(JFormattedTextField jc, Date d) {
        if (d == null) {
            jc.setText("");
        } else {         
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            jc.setText(format.format(d));
        }
        jc.setCaretPosition(0);
    }

    /*
      * Sets the text of the component as time.
      * @param jc JFormattedTextField target.
      * @param t the time value to be set.
      */
     public void setTime(JFormattedTextField jc, Time t) {
        if (t == null) {
            jc.setText("");
        } else {         
             SimpleDateFormat format = new SimpleDateFormat("HH:mm");
             jc.setText(format.format(t));
        }
        jc.setCaretPosition(0);
     }
    
    /**
     * Gets the text of the component as time.
     * @param jc JFormattedTextField source.
     */
    public Time getTime(JFormattedTextField jc) {
        String text = jc.getText();
        return formatStringToTime(text);
    }
    
    /**
     * Sets the text of the component as timestamp.
     * @param jc JFormattedTextField target.
     * @param d the date value to be set.
     */
    public void setTimestamp(JFormattedTextField jc, Date d) {
        if (d == null) {
            jc.setText("");
        } else {        
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            jc.setText(format.format(d));
        }
        jc.setCaretPosition(0);
    }
    
    /**
     * Sets the text of the component as timestamp.
     * @param jc JFormattedTextField target.
     * @param t the timestamp value to be set.
     */
    public void setTimestamp(JFormattedTextField jc, Timestamp t) {
        if (t == null) {
            jc.setText("");
        } else {        
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            jc.setText(format.format(t));
        }
        jc.setCaretPosition(0);
    }

    /**
     * Gets the text of the component as timestamp.
     * @param jc JFormattedTextField source.
     */
    public Timestamp getTimestamp(JFormattedTextField jc) {
        String text = jc.getText();
        return formatStringToTimestamp(text);
    }
    
    /**
     * Sets the text of the component as date.
     * @param jc JFormattedTextField target.
     * @param d the date value to be set.
     */
    public void setDate(JFormattedTextField jc, Date d) {
        if (d == null) {
            jc.setText("");
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            jc.setText(format.format(d));
            jc.setCaretPosition(0);
        }
    }
    
    /**
     * Sets the text of the component as date.
     * @param jc JFormattedTextField target.
     * @param s the string containing the date value.
     */
    public void setDate(JFormattedTextField jc, String s) {
        jc.setText(s);
        jc.setCaretPosition(0);
    }
    
    /**
     * Gets the text of the component as Date.
     * @param jc JFormattedTextField source.
     */
    public Date getDate(JFormattedTextField jc) {
        String text = jc.getText();
        return formatStringToDate(text);
    } 

    /**
     * Gets the text of a date component as String.
     * @param jc JFormattedTextField source.
     */
    public String getDateAsString(JFormattedTextField jc) {
        return jc.getText();
    } 
    
    /**
     * Sets the text of the component as decimal.
     * @param jc JFormattedTextField target.
     * @param bd the BigDecimal value to be set.
     */
    public void setDecimal(JFormattedTextField  jc, java.math.BigDecimal bd) {
        if (bd == null) {
            jc.setText("");
        } else {
            int integers = (Integer) jc.getClientProperty(IFormController.PROPERTY_INTEGERS);
            int decimals = (Integer) jc.getClientProperty(IFormController.PROPERTY_DECIMALS);
            String text = formatBigDecimalToString(bd, integers, decimals);
            jc.setText(text);
        }
        jc.setCaretPosition(0);
    }
    
    /**
     * Gets the text of the component as BigDecimal.
     * @param jc JFormattedTextField source.
     */
    public BigDecimal getDecimal(JFormattedTextField jc) {
        int integers = (Integer) jc.getClientProperty(IFormController.PROPERTY_INTEGERS);
        int decimals = (Integer) jc.getClientProperty(IFormController.PROPERTY_DECIMALS);
        String text = jc.getText();
        return formatStringToBigDecimal(text, integers + decimals);
    }
    
    /**
     * Formats a BigDecimal with specified integers and decimal digits.
     * @param bd <code>BigDecimal</code> value.
     * @param integers number of integer digits.
     * @param decimals number of decimal digits.
     * @return Number formatted as String
     */
    public String formatBigDecimalToString(java.math.BigDecimal bd,int integers,int decimals) {
        if (logger != null) {
            logger.debug(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("ENTER_BIG_DECIMAL") + bd + java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("INTEGERS") + integers + java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("DECIMALS") + decimals);
        }
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        double dval = 0;
        if (bd != null) {
            dval = bd.doubleValue();
        }
        if (bd.signum() == -1) {
            sb1.append('#');
        }
        for (int i = 0; i < integers; i++) {
            if (i < integers - 1) {
                sb1.append('#');
            } else {
                sb1.append("0");
            }
        }
        if (decimals > 0) {
            for (int i = 0; i < decimals; i++) {
                sb2.append('0');
            }
        }
        DecimalFormat nf = new DecimalFormat();
        DecimalFormatSymbols dfs = nf.getDecimalFormatSymbols();
        dfs.setDecimalSeparator(decimal_point.charAt(0));
        nf.setDecimalFormatSymbols(dfs);
        if (logger != null) {
            logger.debug("il double: " + dval);
        }
        String pattern = sb1.toString() + "." + sb2.toString();
        if (logger != null) {
            logger.debug("pattern: " + pattern);
        }
        nf.applyPattern(pattern);
        return nf.format(dval);
    }
    
    /**
     * Formats an int.
     * @param i <code>int</code> value.
     * @param size number of digits.
     * @return Number formatted as String
     */
    public String formatIntToString(int i,int size) {
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setGroupingUsed(false);
        return nf.format(i);
    }
    
    /**
     * Formats a Date from a text.
     * @param s <code>String</code> String text.
     * @return Date 
     */
    public java.sql.Date formatStringToDate(String s) {
        if (logger != null) {
            logger.debug(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("TRYING") + s + ")");
        }
        String s2 = s.replaceAll("[ _/:-]","");
        if (logger != null) {
            logger.debug(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("AFTER_NORMALIZE") + s2 + ")");
        }
        if (s2.length() == 0) {
            return null;
        }
        DateFormat format = new SimpleDateFormat("ddMMyyyy");
        try {
            java.util.Date sd = format.parse(s2);
            return new java.sql.Date(sd.getTime());
        } catch (Exception e) {
            if (logger != null) {
                logger.warn("exc: (" + e + ")");
            }
            return null;
        }
    }
    
    /**
     * Formats a Timestamp from a text.
     * @param s <code>String</code> String text.
     * @return Timestamp
     */
    public java.sql.Timestamp formatStringToTimestamp(String s) {
        if (logger != null) {
            logger.debug(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("TRYING") + s + ")");
        }
        String s2 = s.replaceAll("[ _/:-]","");
        if (logger != null) {
            logger.debug(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("AFTER_NORMALIZE") + s2 + ")");
        }
        if (s2.length() == 0) {
            return null;
        }
        DateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");
        try {
            java.util.Date sd = format.parse(s2);
            return new java.sql.Timestamp(sd.getTime());
        } catch (Exception e) {
            if (logger != null) {
                logger.warn("exc: (" + e + ")");
            }
            return null;
        }
    }
    
    /**
     * Formats a Time from a text.
     * @param s <code>String</code> String text.
     * @return Time
     */
    public java.sql.Time formatStringToTime(String s) {
        if (logger != null) {
            logger.debug(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("TRYING") + s + ")");
        }
        String s2 = s.replaceAll("[ _/:-]","");
        if (logger != null) {
            logger.debug(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("AFTER_NORMALIZE") + s2 + ")");
        }
        if (s2.length() == 0) {
            return null;
        }
        DateFormat format = new SimpleDateFormat("HHmm");
        try {
            java.util.Date sd = format.parse(s2);
            return new java.sql.Time(sd.getTime());
        } catch (Exception e) {
            if (logger != null) {
                logger.warn("exc: (" + e + ")");
            }
            return null;
        }
    }

    /**
     * Formats a BigDecimal from a text.
     * @param s <code>String</code> String text.
     * @param precision <code>int</code> precision.
     * @return BigDecimal
     */
    public BigDecimal formatStringToBigDecimal(String s,int precision) {
        s = s.trim();
        if (s.length() == 0) {
            return new BigDecimal(0);
        }
        if (logger != null) {
            logger.debug(java.util.ResourceBundle.getBundle("com/simeosoft/form/resources/form").getString("TRYING") + s + ")");
        }

        DecimalFormat nf = new DecimalFormat();
        DecimalFormatSymbols dfs =  new DecimalFormatSymbols();
        dfs.setDecimalSeparator(decimal_point.charAt(0));

        nf.setDecimalFormatSymbols(dfs);
        try {
            Number n = nf.parse(s);
            return new BigDecimal(n.doubleValue(),new MathContext(precision,RoundingMode.HALF_UP));
        } catch (Exception e) {
            if (logger != null) {
                logger.warn("exc: (" + e + ")");
            }
            return new BigDecimal(0);
        }
    }
    
    /**
     * Formats an int from a text.
     * @param s <code>String</code> String text.
     * @return int
     */
    public int formatStringToInt(String s) {
        s = s.trim();
        if (s.length() == 0) {
            return 0;
        }
        DecimalFormat format = new DecimalFormat();
        try {
            Number n = format.parse(s);
            return n.intValue();
        } catch (Exception e) {
            if (logger != null) {
                logger.warn("formatStringToInt exc: (" + e + ")");
            }
            return 0;
        }
    }

    /**
     * Interface KeyListener
     * @param ke Keyevent
     */
    public void keyPressed(KeyEvent ke) {
    }
    
    /**
     * Interface KeyListener
     * @param ke KeyEvent
     */
    public void keyReleased(KeyEvent ke) {
    }
    
    /**
     * Interface KeyListener, If it's a JTextArea or JFormattedTextField checks length.
     * Calls the FormListener method <code>changed</code>
     * @param ke KeyEvent
     */
    public void keyTyped(KeyEvent ke) {
        
//        System.out.println("-------- key event start (" + new Timestamp(System.currentTimeMillis()) + ") ---------");
//        System.out.println("key code: " + ke.getKeyCode());
//        System.out.println("key char: " + (int) ke.getKeyChar());
//        System.out.println("escape char: " + ke.VK_ESCAPE);        
//        System.out.println("id: " + ke.getID());        
//        System.out.println("modifiers: " + ke.getModifiers());        
//        System.out.println("isControlDown: " + ke.isControlDown());        
//        System.out.println("-------- key event end ---------");

        JTextComponent jt = (JTextComponent) ke.getComponent();        
        if (jt.getClientProperty(IFormController.PROPERTY_TYPE) == IFormController.PROPERTY_VALUE_DATE) {
            fl.changed((JComponent) ke.getComponent());
            return;
        }        
        
        //
        if (ke.getKeyChar() == KeyEvent.VK_DELETE || ke.getKeyChar() == KeyEvent.VK_BACK_SPACE) {   // delete/bs
            fl.changed((JComponent) ke.getComponent());
            return;
        }
        //
        if (ke.isControlDown() && ke.getKeyChar() == KeyEvent.VK_C) {       // Ctrl-C
            return;
        }
        if (ke.isControlDown() && ke.getKeyChar() == KeyEvent.VK_V) {       // Ctrl-V
            return;
        }
        //
        if (ke.getKeyChar() == KeyEvent.VK_ESCAPE) {
            if (changeColorOnError) {
                jt.setBackground(col_fback_in);
                jt.setForeground(col_ffore_in);
            }                
            return;
        }
        //
        int size = (Integer) jt.getClientProperty(IFormController.PROPERTY_LENGTH);
        String valid_chars = (String) jt.getClientProperty(IFormController.PROPERTY_VALID_CHARS);
        if (jt.getText().length() < size && (valid_chars == null || valid_chars.indexOf(ke.getKeyChar()) > -1)) {
            fl.changed((JComponent) ke.getComponent());
            if (changeColorOnError) {
                jt.setBackground(col_fback_in);
                jt.setForeground(col_ffore_in);
            }                
            return;
        }
        //
        ke.consume();
        Toolkit.getDefaultToolkit().beep();
        if (jlmsg != null) {
            if(jt.getText().length() >= size) {
                jt.setText(jt.getText().substring(0, size));
                jlmsg.setText(validationError.TEXT_MAXLEN.getDesc());
            } else {
                jlmsg.setText(validationError.TEXT_INVALID.getDesc() + " " + valid_chars);
            }
        }
        if (changeColorOnError) {
            jt.setForeground(col_ffore_error);
            jt.setBackground(col_fback_error);
        }
    }
    
    /**
     * Interface FocusListener
     * @param e FocusEvent
     */
    public void focusGained(FocusEvent e) {
        e.getComponent().setBackground(col_fback_in);
        e.getComponent().setForeground(col_ffore_in);
    }
    /**
     * Interface FocusListener
     * @param e FocusEvent
     */
    public void focusLost(FocusEvent e) {
        e.getComponent().setBackground(col_fback_out);
        e.getComponent().setForeground(col_ffore_out);
    }
    
    /**
     * Interface ItemListener
     * @param ie ItemEvent
     */
    public void itemStateChanged(ItemEvent ie) {
    }

    /**
     * Main method. Tests some methods.
     */
    public static void main(String[] argv) {
        FormController fc = new FormController();
        System.out.println("DECIMAL POINT ->: " + fc.getDecimalPoint());         
        System.out.println("--------------------------:");                        
        System.out.println("formatBigDecimalToString -> ('-123,12'): " + 
                    fc.formatBigDecimalToString(new BigDecimal("-123.12"),3,2));
        System.out.println("formatBigDecimalToString -> ('-123456789,12345'): " + 
                    fc.formatBigDecimalToString(new BigDecimal("-123456789.12345"),9,5));
        System.out.println("formatBigDecimalToString -> ('-0'): " + 
                    fc.formatBigDecimalToString(new BigDecimal("-0"),3,2));
        System.out.println("--------------------------:");        
        System.out.println("formatStringToBigDecimal -> ('123456,234'): " + fc.formatStringToBigDecimal("123456,234",9));
        System.out.println("formatStringToBigDecimal -> ('-123456789,234'): " + fc.formatStringToBigDecimal("-123456789,234",12));
        System.out.println("formatStringToBigDecimal -> ('123456789,234'): " + fc.formatStringToBigDecimal("123456789,234",12));
        System.out.println("formatStringToBigDecimal -> ('123,234'): " + fc.formatStringToBigDecimal("123,234",6));
        System.out.println("formatStringToBigDecimal -> ('-123,234'): " + fc.formatStringToBigDecimal("-123,234",6));
        //System.out.println("formatStringToBigDecimal -> ('123.234'): " + fc.formatStringToBigDecimal("123.234",6));
        System.out.println("formatStringToBigDecimal -> ('123'): " + fc.formatStringToBigDecimal("123",6));
        System.out.println("formatStringToBigDecimal -> (''): " + fc.formatStringToBigDecimal("",6));
        System.out.println("formatStringToBigDecimal -> ('-'): " + fc.formatStringToBigDecimal("",6));
    }
}
