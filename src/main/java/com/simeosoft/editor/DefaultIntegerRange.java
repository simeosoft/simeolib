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

import com.simeosoft.string.StringUtils;

/**
 * Default implementation of IIntegerRange returning the maximum and the minimum 
 * values of integer.
 * @author mauro
 * <br>$Id: DefaultIntegerRange.java 24 2007-09-06 08:10:55Z mauro $
 */
public class DefaultIntegerRange implements IIntegerRange {
    
    private int size;
    private boolean signed;
    
    /**
     * Creates a new instance of DefaultIntegerRange
     */
    public DefaultIntegerRange(int size, boolean signed) {
        this.size = size;
        this.signed = signed;
    }

    public int getMaxInteger(int row, int column) {
        if (size > 9) {
            return Integer.MAX_VALUE;
        }
        return new Integer(StringUtils.repeatString(size, "9"));
    }

    public int getMinInteger(int row, int column) {
        if (signed) {
            if (size > 9) {
                return Integer.MIN_VALUE;
            }
            return new Integer("-" + StringUtils.repeatString(size, "9"));
        } else {
            return 0;
        }
    }
}
