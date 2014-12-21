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

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * This class renders text cells having 'Y'/'N' values using a checkbox
 * <br>$Id: CheckCellRenderer.java 34 2007-11-27 14:18:28Z mauro $
 */
public class CheckCellRenderer extends BaseCellRenderer {

    static final long serialVersionUID = 9563214L;

    protected JCheckBox check = new JCheckBox();
    private JPanel panel = new JPanel(new BorderLayout());

    /**
     * Default constructor: it uses the default properties
     */
    public CheckCellRenderer() {
        super();
        check.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(check, BorderLayout.CENTER);
        panel.setOpaque(true);
    }

    /**
     * Creates a new CheckCellRenderer with the specified properties
     */
    public CheckCellRenderer(Properties props) {
        super(props);
        check.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(check, BorderLayout.CENTER);
        panel.setOpaque(true);
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        // we call BaseCellRenderer's implementation and then set the panel
        // colors and border
        check.setSelected(value != null && value.toString().equalsIgnoreCase("Y"));
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                row, column);
        LineBorder border = (LineBorder)getBorder();
        if(border != null) {
            panel.setBorder(BorderFactory.createLineBorder(border.getLineColor()));
        } else {
            panel.setBorder(null);
        }
        check.setEnabled(false);
        check.setBackground(getBackground());
        return panel;
    }
    
}
