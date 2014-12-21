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
package com.simeosoft.util;

import javax.swing.table.*;
/**
 *
 * @author  simeo
 */
public class PropItem {
    public static final int PI_TYPE_STRING = 0;
    public static final int PI_TYPE_BOOLEAN = 1;
    public static final int PI_TYPE_DATE = 2;
    public static final int PI_TYPE_NATSELECT = 3;     // native/selectmax
    public static final int PI_TYPE_FILENAME = 4;
    int datatype = PI_TYPE_STRING;
    public static final int PI_MODE_DEFAULT = 0;
    public static final int PI_MODE_NOEDIT = 1;
    public static final int PI_MODE_COMMAND = 2;
    int mode = PI_MODE_DEFAULT;
    String descr = "";
    String value = null;
    TableCellRenderer tcr;
    TableCellEditor tce;
    public PropItem(String descr, String value) {
        this.datatype = 0;
        this.mode = 0;
        this.descr = descr;
        this.value = value;
    }
    public PropItem(String descr, boolean value) {
        this.datatype = 1;
        this.mode = 0;
        this.descr = descr;
        this.value = value ? "true" : "false";
    }
    public void setMode(int mode) {
        this.mode = mode;
    }
    public void setDatatype(int datatype) {
        this.datatype = datatype;
    }
    public int getDatatype() {
        return datatype;
    }
    public String getDescr() {
        return descr;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
            /*
            switch (this.datatype) {
                case 0:
                case 2:
                    this.value = value;
                    break;
                case 1:
                    this.value = Boolean.valueOf(value).booleanValue();
            }
             **/
    }
    public void setCellRenderer(TableCellRenderer tcr) {
        this.tcr = tcr;
    }
    public TableCellRenderer getCellRenderer() {
        return tcr;
    }
    public void setCellEditor(TableCellEditor tce) {
        this.tce = tce;
    }
    public TableCellEditor getCellEditor() {
        return tce;
    }
}
