/**
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

import java.awt.Component;
import java.util.Properties;
import javax.swing.JTable;

/**
 * This class renders normal text cells
 * <br>$Id: TextCellRenderer.java 30 2007-09-11 15:20:38Z mauro $
 */
public class TextCellRenderer extends BaseCellRenderer {

    static final long serialVersionUID = 35716256L;

    /**
     * Default constructor: it uses the default properties
     */
    public TextCellRenderer() {
        super();
    }

    /**
     * Creates a new TextCellRenderer with the specified properties
     */
    public TextCellRenderer(Properties props) {
        super(props);
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        if (value != null) {
            setText(value.toString());
        } else {
            setText("");
        }
        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }
}
