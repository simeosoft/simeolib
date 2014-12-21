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

import com.simeosoft.string.StringUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Map;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * This is the default implementation of value renderers in a JTable. It handles
 * all the color stuff of the cells and some other behaviour.
 * The default colors and behaviours can be overridden by creating the object
 * with a <code>Properties</code> object (see the relevant constructor for the
 * values).
 * <br>$Id: BaseCellRenderer.java 33 2007-11-27 14:03:54Z mauro $
 */
public class BaseCellRenderer extends DefaultTableCellRenderer {

    static final long serialVersionUID = 7684224L;

    // foreground colors
    protected Color cFgWritableSelected;
    protected Color cFgWritableUnselected;
    protected Color cFgWritableDisabled;
    protected Color cFgReadonlySelected;
    protected Color cFgReadonlyUnselected;
    protected Color cFgReadonlyDisabled;

    // background colors
    protected Color cBgWritableSelected;
    protected Color cBgWritableUnselected;
    protected Color cBgWritableDisabled;
    protected Color cBgReadonlySelected;
    protected Color cBgReadonlyUnselected;
    protected Color cBgReadonlyDisabled;

    // borders, when a cell is selected and has focus
    protected Color cBorderWritable;
    protected Color cBorderReadonly;

    // se true, selezionando una cella si evidenzia tutta la linea
    protected boolean highlightAllRow;

    /**
     * Default constructor: it uses the default properties
     */
    public BaseCellRenderer() {
        super();
        init(new Properties());
    }

    /**
     * Creates a BaseCellRenderer with the specified properties; colors must be
     * specified as "r,g,b" <code>String</code>s), boolean values as "true"/"false".
     * <br>The properties are:<br>
     * <ul>
     * <li><code>COL_FG_WRITABLE_SELECTED</code> foreground color of editable cells when selected</li>
     * <li><code>COL_FG_WRITABLE_UNSELECTED</code> foreground color of editable cells when NOT selected</li>
     * <li><code>COL_FG_WRITABLE_DISABLED</code> foreground color of editable cells when the table is disabled</li>
     * </ul><ul>
     * <li><code>COL_FG_READONLY_SELECTED</code> foreground color of read-only cells when selected</li>
     * <li><code>COL_FG_READONLY_UNSELECTED</code> foreground color of read-only cells when NOT selected</li>
     * <li><code>COL_FG_READONLY_DISABLED</code> foreground color of read-only cells when the table is disabled</li>
     * </ul><ul>
     * <li><code>COL_BG_WRITABLE_SELECTED</code> background color of editable cells when selected</li>
     * <li><code>COL_BG_WRITABLE_UNSELECTED</code> background color of editable cells when NOT selected</li>
     * <li><code>COL_BG_WRITABLE_DISABLED</code> background color of editable cells when the table is disabled</li>
     * </ul><ul>
     * <li><code>COL_BG_READONLY_SELECTED</code> background color of read-only cells when selected</li>
     * <li><code>COL_BG_READONLY_UNSELECTED</code> background color of read-only cells when NOT selected</li>
     * <li><code>COL_BG_READONLY_DISABLED</code> background color of read-only cells when the table is disabled</li>
     * </ul><ul>
     * <li><code>COL_BORDER_WRITABLE</code> color of editable cell border when the cell has focus</li>
     * <li><code>COL_BORDER_READONLY</code> color of read-only cell border when the cell has focus</li>
     * </ul><ul>
     * <li><code>HIGHLIGHT_ALL_ROW</code> whether selecting a cell highlights all its row</li>
     * </ul><ul>
     * <li><code>FONT_NAME</code> the name of the font</li>
     * <li><code>FONT_STYLE</code> the style of the font</li>
     * <li><code>FONT_SIZE</code> the size of the font</li>
     * </ul>
     * Any font property can be left empty: it will be inherited from the panel
     * containing the table
     */
    public BaseCellRenderer(Properties userProps) {
        super();
        init(userProps);
    }

    private void init(Properties userProps) {
        Properties defProps = getDefaultProperties();
        
        cFgWritableSelected = getColor("COL_FG_WRITABLE_SELECTED", userProps, defProps);
        cFgWritableUnselected = getColor("COL_FG_WRITABLE_UNSELECTED", userProps, defProps);
        cFgWritableDisabled = getColor("COL_FG_WRITABLE_DISABLED", userProps, defProps);

        cFgReadonlySelected = getColor("COL_FG_READONLY_SELECTED", userProps, defProps);
        cFgReadonlyUnselected = getColor("COL_FG_READONLY_UNSELECTED", userProps, defProps);
        cFgReadonlyDisabled = getColor("COL_FG_READONLY_DISABLED", userProps, defProps);

        cBgWritableSelected = getColor("COL_BG_WRITABLE_SELECTED", userProps, defProps);
        cBgWritableUnselected = getColor("COL_BG_WRITABLE_UNSELECTED", userProps, defProps);
        cBgWritableDisabled = getColor("COL_BG_WRITABLE_DISABLED", userProps, defProps);

        cBgReadonlySelected = getColor("COL_BG_READONLY_SELECTED", userProps, defProps);
        cBgReadonlyUnselected = getColor("COL_BG_READONLY_UNSELECTED", userProps, defProps);
        cBgReadonlyDisabled = getColor("COL_BG_READONLY_DISABLED", userProps, defProps);

        cBorderWritable = getColor("COL_BORDER_WRITABLE", userProps, defProps);
        cBorderReadonly = getColor("COL_BORDER_READONLY", userProps, defProps);

        highlightAllRow = getBool("HIGHLIGHT_ALL_ROW", userProps, defProps);

        String fontName = getString("FONT_NAME", userProps, defProps);
        int fontStyle = getInt("FONT_STYLE", userProps, defProps);
        int fontSize = getInt("FONT_SIZE", userProps, defProps);
        if(fontName.length() > 0 || fontStyle != -1 || fontSize != -1) {
            Font font = getFont();
            if(fontName.length() == 0) {
                fontName = font.getName();
            }
            if(fontStyle == -1) {
                fontStyle = font.getStyle();
            }
            if(fontSize == -1) {
                fontSize = font.getSize();
            }
            setFont(new Font(fontName, fontStyle, fontSize));
        }
    }

    /**
     * Returns the default properties: the default behaviour is to highlight all
     * the row and to leave the font as it is
     * @return the default properties:
     * <ul>
     * <li><code>COL_FG_WRITABLE_SELECTED</code>: "0,0,0"</li>
     * <li><code>COL_FG_WRITABLE_UNSELECTED</code>: "0,0,0"</li>
     * <li><code>COL_FG_WRITABLE_DISABLED</code>: "102,102,102"</li>
     * </ul><ul>
     * <li><code>COL_FG_READONLY_SELECTED</code>: "0,0,0"</li>
     * <li><code>COL_FG_READONLY_UNSELECTED</code>: "0,0,0"</li>
     * <li><code>COL_FG_READONLY_DISABLED</code>: "102,102,102"</li>
     * </ul><ul>
     * <li><code>COL_BG_WRITABLE_SELECTED</code>: "184,207,229"</li>
     * <li><code>COL_BG_WRITABLE_UNSELECTED</code>: "255,255,255"</li>
     * <li><code>COL_BG_WRITABLE_DISABLED</code>: "255,255,255"</li>
     * </ul><ul>
     * <li><code>COL_BG_READONLY_SELECTED</code>: "184,207,229"</li>
     * <li><code>COL_BG_READONLY_UNSELECTED</code>: "255,255,255"</li>
     * <li><code>COL_BG_READONLY_DISABLED</code>: "255,255,255"</li>
     * </ul><ul>
     * <li><code>COL_BORDER_WRITABLE</code>: "99,130,191"</li>
     * <li><code>COL_BORDER_READONLY</code>: "99,130,191"</li>
     * </ul><ul>
     * <li><code>HIGHLIGHT_ALL_ROW</code>: "true"</li>
     * </ul><ul>
     * <li><code>FONT_NAME</code>: ""</li>
     * <li><code>FONT_STYLE</code>: ""</li>
     * <li><code>FONT_SIZE</code>: ""</li>
     * </ul>
     */
    protected Properties getDefaultProperties() {
        Properties props = new Properties();
        // foreground color of editable cells when selected
        props.setProperty("COL_FG_WRITABLE_SELECTED", "0,0,0");

        // foreground color of editable cells when NOT selected
        props.setProperty("COL_FG_WRITABLE_UNSELECTED", "0,0,0");

        // foreground color of editable cells when the table is disabled
        props.setProperty("COL_FG_WRITABLE_DISABLED", "102,102,102");

        // foreground color of read-only cells when selected
        props.setProperty("COL_FG_READONLY_SELECTED", "0,0,0");

        // foreground color of read-only cells when NOT selected
        props.setProperty("COL_FG_READONLY_UNSELECTED", "0,0,0");

        // foreground color of read-only cells when the table is disabled
        props.setProperty("COL_FG_READONLY_DISABLED", "102,102,102");

        // background color of editable cells when selected
        props.setProperty("COL_BG_WRITABLE_SELECTED", "184,207,229");

        // background color of editable cells when NOT selected
        props.setProperty("COL_BG_WRITABLE_UNSELECTED", "255,255,255");

        // background color of editable cells when the table is disabled
        props.setProperty("COL_BG_WRITABLE_DISABLED", "255,255,255");

        // background color of read-only cells when selected
        props.setProperty("COL_BG_READONLY_SELECTED", "184,207,229");

        // background color of read-only cells when NOT selected
        props.setProperty("COL_BG_READONLY_UNSELECTED", "255,255,255");

        // background color of read-only cells when the table is disabled
        props.setProperty("COL_BG_READONLY_DISABLED", "255,255,255");

        // color of editable cell border when the cell has focus
        props.setProperty("COL_BORDER_WRITABLE", "99,130,191");

        // color of read-only cell border when the cell has focus
        props.setProperty("COL_BORDER_READONLY", "99,130,191");

        // whether selecting a cell highlights all its row
        props.setProperty("HIGHLIGHT_ALL_ROW", "true");

        props.setProperty("FONT_NAME", "");
        props.setProperty("FONT_STYLE", "-1");
        props.setProperty("FONT_SIZE", "-1");

        return props;
    }

    protected Color getColor(String propName, Properties userProps, Properties defProps) {
        Color result = StringUtils.getColorFromRGB(userProps.getProperty(propName));
        if(result == null) {
            result = StringUtils.getColorFromRGB(defProps.getProperty(propName));
        }
        return result;
    }

    private boolean getBool(String propName, Properties userProps, Properties defProps) {
        return userProps.getProperty(propName, defProps.getProperty(propName))
                .equalsIgnoreCase("true");
    }
    
    private String getString(String propName, Properties userProps, Properties defProps) {
        return userProps.getProperty(propName, defProps.getProperty(propName));
    }

    private int getInt(String propName, Properties userProps, Properties defProps) {
        int result;
        try {
            result = Integer.parseInt(userProps.getProperty(propName));
        } catch(NumberFormatException e) {
            result = Integer.parseInt(defProps.getProperty(propName));
        }
        return result;
    }
    
    /**
     * The default behaviour when rendering the cell. It sets all colours
     */
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column)
    {
        setOpaque(true);
        setBorder(null);
        if(table.getModel().isCellEditable(row, column)) {
            if (isSelected) {
                if(hasFocus || highlightAllRow) {
                    setForeground(cFgWritableSelected);
                    setBackground(cBgWritableSelected);
                } else {
                    setForeground(cFgWritableUnselected);
                    setBackground(cBgWritableUnselected);
                }
                if (hasFocus) {
                    setBorder(BorderFactory.createLineBorder(cBorderWritable));
                }
            } else {
                if (table.isEnabled()) {
                    setForeground(cFgWritableUnselected);
                    setBackground(cBgWritableUnselected);
                } else {
                    setForeground(cFgWritableDisabled);
                    setBackground(cBgWritableDisabled);
                }
            }
        } else {
            if (isSelected) {
                if(hasFocus || highlightAllRow) {
                    setForeground(cFgReadonlySelected);
                    setBackground(cBgReadonlySelected);
                } else {
                    setForeground(cFgReadonlyUnselected);
                    setBackground(cBgReadonlyUnselected);
                }
                if (hasFocus) {
                    setBorder(BorderFactory.createLineBorder(cBorderReadonly));
                }
            } else {
                if (table.isEnabled()) {
                    setForeground(cFgReadonlyUnselected);
                    setBackground(cBgReadonlyUnselected);
                } else {
                    setForeground(cFgReadonlyDisabled);
                    setBackground(cBgReadonlyDisabled);
                }
            }
        }
        return this;
    }
}