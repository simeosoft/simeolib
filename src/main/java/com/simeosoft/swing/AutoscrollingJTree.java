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

import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.Autoscroll;
import javax.swing.JTree;

/**
 * AutoscrcollJTree is a <code>JTree</code> 
 * that implements the <code>Autoscroll<code> interface 
 *
 * @author mauro
 */
public class AutoscrollingJTree extends JTree implements Autoscroll {
    
    private static final int AUTOSCROLL_MARGIN = 12;
    static final long serialVersionUID = 12222327;    
    
    
    /** Creates a new instance of AutoscrollJTree */
    public AutoscrollingJTree() {
        super();
    }
    
    /**
     * Autoscroll Interface...
     * The following code was borrowed from the book:
     *		Java Swing
     *		By Robert Eckstein, Marc Loy & Dave Wood
     *		Paperback - 1221 pages 1 Ed edition (September 1998) 
     *		O'Reilly & Associates; ISBN: 156592455X 
     *
     * The relevant chapter of which can be found at:
     *      http://www.oreilly.com/catalog/jswing/chapter/dnd.beta.pdf
     */
    public Insets getAutoscrollInsets() {
        Rectangle raOuter = getBounds();
		Rectangle raInner = getParent().getBounds();
		return new Insets(
			raInner.y - raOuter.y + AUTOSCROLL_MARGIN, raInner.x - raOuter.x + AUTOSCROLL_MARGIN,
			raOuter.height - raInner.height - raInner.y + raOuter.y + AUTOSCROLL_MARGIN,
			raOuter.width - raInner.width - raInner.x + raOuter.x + AUTOSCROLL_MARGIN);
    }

    public void autoscroll(Point pt) {
        // Figure out which row were on.
            int nRow = getRowForLocation(pt.x, pt.y);

            // If we are not on a row then ignore this autoscroll request
            if (nRow < 0)
                    return;

            Rectangle raOuter = getBounds();
            // Now decide if the row is at the top of the screen or at the
            // bottom. We do this to make the previous row (or the next
            // row) visible as appropriate. If were at the absolute top or
            // bottom, just return the first or last row respectively.

            nRow = (pt.y + raOuter.y <= AUTOSCROLL_MARGIN)                // Is row at top of screen? 
                             ?	
                            (nRow <= 0 ? 0 : nRow - 1)	                  // Yes, scroll up one row
                             :
                            (nRow < getRowCount() - 1 ? nRow + 1 : nRow); // No, scroll down one row

            scrollRowToVisible(nRow);
    }
}
