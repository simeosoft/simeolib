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
package com.simeosoft.swing;

import java.util.ArrayList;

/** Simple data container
 * @author Simeosoft
 * $Id: TransferData.java 2 2006-11-15 09:21:35Z simeo $
 */
public class TransferData {
    private int i = 0;
    private String s = "";
    private ArrayList<String> a;
    private Object o = null;
    private Object o2 = null;    
    private EDataStatus data_status = EDataStatus.DATA_NOT_MODIFIED;
    private ERetValues value_status = ERetValues.OK_VALUE;
    
    public static enum ERetValues { OK_VALUE,CANCEL_VALUE };
    public static enum EDataStatus { DATA_MODIFIED, DATA_NOT_MODIFIED };
    
    public TransferData() {
    }

    public EDataStatus getDataStatus() {
        return data_status;
    }
    public void setDataStatus(EDataStatus status) {
        data_status = status;
    }
    
    public ERetValues getValueStatus() {
        return value_status;
    }
    
    public void setValueStatus(ERetValues status) {
        value_status = status;
    }
    
    public int getIntValue() {
        return i;
    }

    public String getStringValue() {
        return s;
    }

    public ArrayList<String> getStringArrayValue() {
        return a;
    }

    public Object getObjectValue() {
        return o;
    }

    public Object getObject2Value() {
        return o2;
    }

    public void setIntValue(int value) {
        i = value;
    }

    public void setStringValue(String value) {
        s = value;
    }

    public void setStringArrayValue(ArrayList<String> value) {
        a = value;
    }

    public void setObjectValue(Object value) {
        o = value;
    }

    public void setObject2Value(Object value) {
        o2 = value;
    }
}