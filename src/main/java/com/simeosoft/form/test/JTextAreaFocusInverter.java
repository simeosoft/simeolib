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
package com.simeosoft.form.test;
 
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
 
/**
 * Taken from a javalobby forum.
 * Thanks to Christopher Brown.
 * http://www.javalobby.org/java/forums/m91844774.html 
 */
public class JTextAreaFocusInverter
{
	public static void main(String[] args)
	{
		// create text area
		final JTextArea area = new JTextArea(4,40);
		area.setWrapStyleWord(true);
		area.setLineWrap(true);
		invertFocusTraversalBehaviour(area);
 
		// create buttons, able to clear/fill text are
		JButton fillButton  = new JButton("Fill");
		fillButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt)
			{
				area.setText("Personne n'est jamais assez fort pour ce calcul.");
				area.append("\nJ'apprécie ces rillettes en fut.");
				area.append("\nIl fallait voir quelle bille il faisait.");
				area.append("\nOn l'envoie dans la culture.");
				area.append("\nTu vends de la serge.");
			}
		});
 
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt)
			{
				area.setText("");
			}
		});
 
 
		// create our frame and add our components
		JFrame frame = new JFrame("JTextArea Focus Inverter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(fillButton,            BorderLayout.PAGE_START);
		frame.add(resetButton,           BorderLayout.PAGE_END);
		frame.add(new JScrollPane(area), BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
 
 
	public static void invertFocusTraversalBehaviour(JTextArea textArea)
	{
		Set<AWTKeyStroke> forwardKeys  = textArea.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
		Set<AWTKeyStroke> backwardKeys = textArea.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);
 
		// check that we WANT to modify current focus traversal keystrokes
		if (forwardKeys.size() != 1 || backwardKeys.size() != 1) return;
		final AWTKeyStroke fks = forwardKeys.iterator().next();
		final AWTKeyStroke bks = backwardKeys.iterator().next();
		final int fkm = fks.getModifiers();
		final int bkm = bks.getModifiers();
		final int ctrlMask      = KeyEvent.CTRL_MASK+KeyEvent.CTRL_DOWN_MASK;
		final int ctrlShiftMask = KeyEvent.SHIFT_MASK+KeyEvent.SHIFT_DOWN_MASK+ctrlMask;
		if (fks.getKeyCode() != KeyEvent.VK_TAB || (fkm & ctrlMask) == 0 || (fkm & ctrlMask) != fkm)
		{	// not currently CTRL+TAB for forward focus traversal
			return;
		}
		if (bks.getKeyCode() != KeyEvent.VK_TAB || (bkm & ctrlShiftMask) == 0 || (bkm & ctrlShiftMask) != bkm)
		{	// not currently CTRL+SHIFT+TAB for backward focus traversal
			return;
		}
 
		// bind our new forward focus traversal keys
		Set<AWTKeyStroke> newForwardKeys = new HashSet<AWTKeyStroke>(1);
		newForwardKeys.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB,0));
		textArea.setFocusTraversalKeys(
			KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
			Collections.unmodifiableSet(newForwardKeys)
		);
		// bind our new backward focus traversal keys
		Set<AWTKeyStroke> newBackwardKeys = new HashSet<AWTKeyStroke>(1);
		newBackwardKeys.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB,KeyEvent.SHIFT_MASK+KeyEvent.SHIFT_DOWN_MASK));
		textArea.setFocusTraversalKeys(
			KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
			Collections.unmodifiableSet(newBackwardKeys)
		);
 
		// Now, it's still useful to be able to type TABs in some cases.
		// Using this technique assumes that it's rare however (if the user
		// is expected to want to type TAB often, consider leaving text area's
		// behaviour unchanged...).  Let's add some key bindings, inspired
		// from a popular behaviour in instant messaging applications...
		TextInserter.applyTabBinding(textArea);
 
		// we could do the same stuff for RETURN and CTRL+RETURN for activating
		// the root pane's default button: omitted here for brevity
	}
 
 
	public static class TextInserter extends AbstractAction
	{
                static final long serialVersionUID = 12222328;    
		private JTextArea textArea;
		private String insertable;
 
		private TextInserter(JTextArea textArea, String insertable)
		{
			this.textArea   = textArea;
			this.insertable = insertable;
		}
 
		public static void applyTabBinding(JTextArea textArea)
		{
			textArea.getInputMap(JComponent.WHEN_FOCUSED)
			        .put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB,KeyEvent.CTRL_MASK+KeyEvent.CTRL_DOWN_MASK),"tab");
			textArea.getActionMap()
			        .put("tab",new TextInserter(textArea, "\t"));
		}
 
		public void actionPerformed(ActionEvent evt)
		{
			// could be improved to overtype selected range
			textArea.insert(insertable,textArea.getCaretPosition());
		}
	}
}
