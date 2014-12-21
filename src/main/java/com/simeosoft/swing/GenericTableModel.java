/*
    The GUFF - The GNU Ultimate Framework Facility
    Copyright (C) Simeosoft di Carlo Simeone
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
*/

package com.simeosoft.swing;

import javax.swing.table.AbstractTableModel;

/** 
 * Base class for table and indexes table model.<br>
 * @author Simeosoft
 * @version $Id: GenericTableModel.java 2 2006-11-15 09:21:35Z simeo $
 */
public abstract class GenericTableModel extends AbstractTableModel {
    /**
     * Returns the column name.
     * @param col column number
     * @return column name
     */
    @Override
    public abstract String getColumnName(int col);
    /**
     * Returns the number of columns of the table.
     * @return column name
     */
    public abstract int getColumnCount();
    /**
     * Returns the class of the columns.
     * @param c column number
     * @return column name
     */
    @Override
    public abstract Class getColumnClass(int c);
    /**
     * Returns true if the cell is editable.
     * @param r row number
     * @param c column number
     * @return column name
     */
    @Override
    public abstract boolean isCellEditable(int r, int c);
    /**
     * Sets a value in a cell.
     * @param val new value
     * @param r row number
     * @param c column number
     */
    @Override
    public abstract void setValueAt(Object val,int r, int c);
    /**
     * Returns a cell value.
     * @param r row number
     * @param c column number
     */
    public abstract Object getValueAt(int r, int c);
    /**
     * Returns the number of rows.
     * @return number of rows
     */
    public abstract int getRowCount();
    /**
     * Inserts a new row.
     * @param r row number
     * @param before inserts the row before row <i>r</i>
     * @param cloning if true the row content is copied from row <i>r</i>
     */
    public abstract void insertItem(int r,boolean before,boolean cloning);
    /**
     * Deletes a row.
     * @param r row number
     */
    public abstract void deleteItem(int r);
    /**
     * Sets the values as defaults.
     */
    public abstract void setDefaultItems();
}