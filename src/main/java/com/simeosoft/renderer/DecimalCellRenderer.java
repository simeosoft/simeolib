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

package com.simeosoft.renderer;

import com.simeosoft.string.StringUtils;
import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * This class renders cells containing BigDecimal values in a JTable.
 * @author mauro
 */
public class DecimalCellRenderer extends BaseCellRenderer {
    
    static final long serialVersionUID = 7781224L;
    
    private int decimals;
        
    public DecimalCellRenderer(int decimals) {
        super();
        this.decimals = decimals;
    }
    
    public DecimalCellRenderer(int decimals, Properties props) {
        super(props);
        this.decimals = decimals;
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
            
        setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BigDecimal bd = (BigDecimal) value;
        // un bug di Java lancia un'eccezione quando tentiamo di formattare
        // alcuni BigDecimal (forse solo lo zero) che hanno un numero di decimali
        // superiori a quanti ne vogliamo stampare
        if (bd == null || bd.compareTo(BigDecimal.ZERO) == 0) {
            bd = BigDecimal.ZERO;
        }
        setText(String.format(Locale.US, "%." + decimals + "f", bd));
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
