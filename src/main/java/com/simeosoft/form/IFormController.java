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

import javax.swing.JComponent;

/** 
 * This interface defines the methods of a generic FormController
 * and some enums.
 * <br>$Id: IFormController.java 7 2007-02-05 16:47:08Z mauro $
 *
 */
public interface IFormController {
    
    public static final String PROPERTY_LENGTH = "length";
    public static final String PROPERTY_VALID_CHARS = "valid_chars";
    public static final String PROPERTY_INTEGERS = "int";
    public static final String PROPERTY_DECIMALS = "dec";
    public static final String PROPERTY_TYPE = "type";
    public static final String PROPERTY_VALUE_TEXT = "text";    
    public static final String PROPERTY_VALUE_INTEGER = "num";    
    public static final String PROPERTY_VALUE_DECIMAL = "dec";    
    public static final String PROPERTY_VALUE_DATE = "date";  
    
    public static final String PROPERTY_MAX_VALUE = "max_value";    
    public static final String PROPERTY_MIN_VALUE = "min_value";  
    
    public enum regexType {
        IP_ADDRESS ("^\\b((25[0-5]|2[0-4]\\d|[01]\\d\\d|\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|[01]\\d\\d|\\d?\\d)\\b$",
                    " 0123456789.","xxx.xxx.xxx.xxx"),
        EMAIL ("^[\\w-]+(?:\\.[\\w-]+)*@(?:[\\w-]+\\.)+[a-zA-Z]{2,7}$",null,"aaa@domain.ext"),
        URL ("^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:]])?$",
                    null,"protocol://aaa"),
        CREDIT_CARD ("^((4\\d{3})|(5[1-5]\\d{2})|(6011))-?\\d{4}-?\\d{4}-?\\d{4}|3[4,7]\\d{13}$",null,"4556-1234-1234-1234");
        private String regex;
        private String valid_chars;
        private String hint;
        regexType(String regex,String valid_chars,String hint) {
            this.regex = regex;
            this.valid_chars = valid_chars;
            this.hint = hint;
        }
        public String getPattern() {
            return regex;
        }
        public String getValidChars() {
            return valid_chars;
        }
        // not used yet
        public String getHint() {
            return hint;
        }
    }
    public enum validationError {
        NO_ERROR,
        INTEGER_SPACES,
        INTEGER_MAXLEN,
        INTEGER_INVALID,
        DATE_INVALID,
        DECIMAL_SPACES,
        DECIMAL_SEPARATOR,
        DECIMAL_INTMAXLEN,
        DECIMAL_DECMAXLEN,
        DECIMAL_INVALID,
        DECIMAL_MAXLEN,
        TEXT_MAXLEN,
        TEXT_INVALID,
        TIME_FORM,
        TIME_HOUR,
        TIME_FORM_MINUTE,
        TIME_MINUTE,
        TIMESTAMP_INVALID,
        REGEX_INVALID,
        MANDATORY,
        MAX_VALUE,
        MIN_VALUE;
        private String desc;
        /**
         * Sets the enum description
         * @param desc error description
         */
        public void setDesc(String desc) {
            this.desc = desc;
        }
        /**
         * Returns the enum description
         * @return error description 
         */
        public String getDesc() {
            return desc;
        }
    };
    
    /**
     * Displays a message.
     * @param ve validation error
     * @param jc JComponent source
     */
    public void doMessage(validationError ve, JComponent jc);
    
    /**
     * Clears the form.
     */
    public void clear();    
    
    /**
     * Formal check of the form fields.
     * @throws FieldException if validation errors
     */
    public void check() throws FieldException;
    
    /**
     * Enable or disable all form fields
     * @param enable set to enable or disable
     */
    public void setEnabled(boolean enable);
}
