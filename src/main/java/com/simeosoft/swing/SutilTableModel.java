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

import javax.swing.table.AbstractTableModel;

/** 
 * Base class for table and indexes table model.
 * @author Simeosoft
 * $Id: SutilTableModel.java 2 2006-11-15 09:21:35Z simeo $
 */
public abstract class SutilTableModel extends AbstractTableModel {
    public abstract String getColumnName(int col);
    public abstract int getColumnCount();
    public abstract Class getColumnClass(int c);
    public abstract boolean isCellEditable(int r, int c);
    public abstract void setValueAt(Object val,int r, int c);
    public abstract Object getValueAt(int r, int c);
    public abstract int getRowCount();
    public abstract void insertItem(int r,boolean before,boolean cloning);
    public abstract void deleteItem(int r);
    public abstract void setDefaultItems();
}