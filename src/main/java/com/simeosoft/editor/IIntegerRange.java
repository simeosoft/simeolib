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

/**
 * IntegerListener objects are used to set the max and min <code>Integer</code> value in a cell.
 * 
 * <br>$Id: IIntegerRange.java 21 2007-09-05 16:13:25Z mauro $
 */
public interface IIntegerRange {
    
    /**
     * Return the max value.
     * @param row index
     * @param column index
     * @return the max Integer value allowable for the cell
     */
    public int getMaxInteger(int row, int column);
    
    /**
     * Return the min value.
     * @param row index
     * @param column index
     * @return the min Integer value allowable for the cell
     */
    public int getMinInteger(int row, int column);
    
}
