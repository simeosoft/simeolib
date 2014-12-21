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
import java.math.BigDecimal;

/**
 * Default implementation of IDecimalRenge returning the maximum and the minimum 
 * values of decimal composed by a certain number of integer and decimal digits. 
 * @author mauro
 * <br>$Id: DefaultDecimalRange.java 24 2007-09-06 08:10:55Z mauro $
 */
public class DefaultDecimalRange implements IDecimalRange {
    
    private int integers;
    private int decimals;
    private boolean signed;
 
    /**
     * @param integers number of integer digits.
     * @param decimals number of decimals digits.
     * @param signed true if the field is signed.
     */
    public DefaultDecimalRange(int integers, int decimals, boolean signed) {
        this.integers = integers;
        this.decimals = decimals;
        this.signed = signed;
    }

    public BigDecimal getMaxDecimal(int row, int column) {
        return new BigDecimal(StringUtils.repeatString(integers, "9") + 
                "." + StringUtils.repeatString(decimals, "9"));
    }

    public BigDecimal getMinDecimal(int row, int column) {
        if (signed) {
            return new BigDecimal("-" + StringUtils.repeatString(integers, "9") + 
                "." + StringUtils.repeatString(decimals, "9"));
        } else {
            return BigDecimal.ZERO;
        }        
    }
    
}
