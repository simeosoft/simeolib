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
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.text.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** The SwingUtils class contains some swing utility methods<br>
 * Images must reside in a directory called (guess?) <i>"images"</i>
 * on the application home directory.<br>
 * To enable logging, assing a valid java.util.logging.Logger to the
 * public logger field LOGGER.
 * @author Simeosoft
 * $Id: SwingUtils.java 16 2007-06-11 10:40:29Z simeo $
 */
public abstract class SwingUtils {
    /* ******************************************************* */
    public static String DECIMAL_POINT = ",";       // could be overridden by other locales or operator preference
    public static java.util.logging.Logger LOGGER = null;
    // PREFERENCES
    public static int prefs = 0;
    public static final int USE_PLACEHOLDERS = 1;
    // build component constants
    public final static int CNTL_TEXT = 0;
    public final static int CNTL_TEXTAREA = 1;
    public final static int CNTL_DATE = 2;
    public final static int CNTL_TIME = 3;
    public final static int CNTL_TIMESTAMP = 4;
    public final static int CNTL_LIST = 5;
    public final static int CNTL_COMBO = 6;
    public final static int CNTL_CHECK = 7;
    public final static int CNTL_INTEGER = 8;
    public final static int CNTL_DECIMAL = 9;
    public final static int CNTL_LABEL = 10;
    public final static int CNTL_BUTTON = 11;
    public final static int CNTL_TABLE = 12;
    // build component styles
    public final static int STYLE_EDIT = 0;
    public final static int STYLE_OK = 1;
    public final static int STYLE_CANCEL = 2;
    //
    public final static int STYLE_CONFERMA = 3;
    public final static int STYLE_ANNULLA = 4;
    public final static int STYLE_ELIMINA = 5;
    public final static int STYLE_STAMPA = 6;
    public final static int STYLE_ESCI = 7;
    public final static int STYLE_HELP = 8;
    public final static int STYLE_NAVFIRST = 9;
    public final static int STYLE_NAVPREV = 10;
    public final static int STYLE_TBSTATUS = 11;
    public final static int STYLE_NAVNEXT = 12;
    public final static int STYLE_NAVLAST = 13;
    public final static int STYLE_GENERIC = 14;
    public final static int STYLE_RICERCA = 15;
    //
    public final static int STYLE_INSBEFOREROW = 16;
    public final static int STYLE_INSAFTERROW = 17;
    public final static int STYLE_DELETEROW = 18;
    public final static int STYLE_CLONEBEFOREROW = 19;
    public final static int STYLE_CLONEAFTERROW = 20;
    public final static int STYLE_DEFAULTROWS = 21;
    // field style
    public static final Color COL_FFORE_IN = new Color(139,101,8);
    public static final Color COL_FBACK_IN = new Color(250,235,215);
    public static final Color COL_FFORE_OUT = new Color(113,122,117);
    public static final Color COL_FBACK_OUT = new Color(248,248,255);
    public static final Color COL_FFORE_ERROR = new Color(255,255,255);
    public static final Color COL_FBACK_ERROR = new Color(255,0,0);
    // image storage
    public static Icon ICON_FIELD = null;
    public static Icon ICON_FIELD_KEY = null;
    public static Icon ICON_FIELD_UNIQUE = null;
    public static Icon ICON_FIELD_INDEX = null;
    public static Icon ICON_TABLE = null;
    public static Icon ICON_INDEX = null;
    public static Icon ICON_SMALLINDEX = null;
    public static Icon ICON_LOGO = null;
    public static Icon ICON_HELP_SMALL = null;
    public static Icon ICON_INSBEFOREROW = null;
    public static Icon ICON_INSAFTERROW = null;
    public static Icon ICON_DELETEROW = null;
    public static Icon ICON_CLONEBEFOREROW = null;
    public static Icon ICON_CLONEAFTERROW = null;
    public static Icon ICON_DEFAULTROWS = null;
    public final static String IMAGES_PATH = "images/";
    public final static String metalClassName =
    "javax.swing.plaf.metal.MetalLookAndFeel";
    public final static String motifClassName =
    "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    public final static String windowsClassName =
    "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    public final static String compiereClassName =
    "org.compiere.plaf.CompiereLookAndFeel";
    
    // MODIF - LOCALIZZARE E RICAVARE DA FILE DI PROPRIETA' O DATABASE
    final static String ERR_TITLE = "Errore!";
    final static String WARNING_TITLE = "Attenzione!";
    final static String ALERT_TITLE = "Segnalazione!";
    final static String MSG_UNKNOWN = "** messaggio sconosciuto **";
    final static String[][] taberr = {
        { "ERR-DB-01","Errore accesso al database!" },
        { "ERR-DB-02","Errore istanza tabella database!" },
        { "ERR-DB-03","Errore esecuzione query primaria!" },
        { "ERR-DB-04","Errore esecuzione query!" },
    };
    final static String[][] tabwarn = {
        { "WAR-GEN-01","<b>ATTENZIONE!</B><br><b>RECORD MODIFICATO</b><br>Vuoi annullare l'operazione ?" },
        { "WAR-GEN-02","<b>ATTENZIONE!</B><br><b>CONFERMA ELIMINAZIONE!</b><br>Confermi l'operazione ?" },
        { "WAR-GEN-03","<b>ATTENZIONE!</B><br><b>Questa operazione eliminera' le righe esistenti.</b><br>Confermi l'operazione ?" },
    };

    /**
     * Returns true if the preference is enabled
     * @param pref preference
     */
    public static boolean getPreference(int pref) {
        return (prefs & pref) != 0;
    }
    /**
     * Sets a preference
     * @param pref preference
     * @param enabling true if preference must be enabled
     */
    public static void setPreference(int pref, boolean enabling) {
        if (enabling) {
            prefs |= pref; 
        } else {
            prefs &= ~pref; 
        }
    }
    /**
     * Formats a date as string.<br>
     * @param d date
     * @param type type of format (date/time/tmestamp)
     */
    public static String formatFieldToString(Date d, int type) {
        if (d == null) {
            return "";
        }
        /*
            switch (type) {
                case CNTL_DATE:
                    return "";
                case CNTL_TIME:
                    return "__:__";
                case CNTL_TIMESTAMP:
                    return "__/__/____ __:__:__";
                default:
                    return "????";
            }
        }
         */
        DateFormat format;
        switch (type) {
            case CNTL_DATE:
                format = new SimpleDateFormat("dd/MM/yyyy");
                break;
            case CNTL_TIME:
                format = new SimpleDateFormat("HH:mm");
                break;
            case CNTL_TIMESTAMP:
                format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                break;
            default:
                format = new SimpleDateFormat("dd/MM/yyyy");
        }
        return format.format(d);
   }
    /**
     * Formats a BigDecimal
     */
    public static String formatFieldToString(java.math.BigDecimal bd,int size,int dec) {
        if (LOGGER != null) {
            LOGGER.fine("(BigDecimal): entering: value: " + bd + " size: " + size + " dec: " + dec);        
        }
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        double dval = 0;
        if (bd != null) {        
            dval = bd.doubleValue();
        }
        int len1 = size - dec;
        for (int i = 0; i < len1; i++) {
            sb1.append('0');
        }
        if (dec > 0) {
            for (int i = 0; i < dec; i++) {
                sb2.append('0');
            }
        }
        DecimalFormat nf = new DecimalFormat();
        if (LOGGER != null) {
            LOGGER.fine("il double: " + dval);
        }
        String pattern = sb1.toString() + "." + sb2.toString();
        if (LOGGER != null) {
            LOGGER.fine("pattern: " + pattern);        
        }
        nf.applyPattern(pattern);
        return nf.format(dval);
   }

    /**
     * Formats an int (must be casted as Integer)
     */
    public static String formatFieldToString(Integer i,int size) {
        long lval = 0;
        if (i != null) {        
            lval = i.longValue();
        }
        StringBuffer sb = new StringBuffer(size);
        for (int j = 0; j < size; j++) {
            sb.append('0');
        }
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setGroupingUsed(false);
        if (LOGGER != null) {
            LOGGER.fine("il long: " + lval);
        }
        String pattern = sb.toString();
        if (LOGGER != null) {
            LOGGER.fine("pattern: " + pattern);        
        }
        // nf.applyPattern("pattern");
        return nf.format(lval);
   }

    /**
     * Formats a string as date
     */
    public static java.sql.Date formatStringToDate(String s) {
        if (LOGGER != null) {
            LOGGER.fine("trying: (" + s + ")");                
        }
        String s2 = s.replaceAll("[ _/:-]","");
        if (LOGGER != null) {
            LOGGER.fine("after normalize: (" + s2 + ")");                
        }
        if (s2.length() == 0) {
            return null;
        }
        DateFormat format = new SimpleDateFormat("ddMMyyyy");
        try {
            java.util.Date sd = format.parse(s2);
            return new java.sql.Date(sd.getTime()); 
        } catch (Exception e) {
            if (LOGGER != null) {
                LOGGER.warning("exc: (" + e + ")");
            }
            return null;
        }
    }    

    /**
     * Formats a string as timestamp.
     */
    public static java.sql.Timestamp formatStringToTimestamp(String s) {
        if (LOGGER != null) {
            LOGGER.fine("trying: (" + s + ")");                
        }
        String s2 = s.replaceAll("[ _/:-]","");
        if (LOGGER != null) {
            LOGGER.fine("after normalize: (" + s2 + ")");                
        }
        if (s2.length() == 0) {
            return null;
        }
        DateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");
        try {
            java.util.Date sd = format.parse(s2);
            return new java.sql.Timestamp(sd.getTime()); 
        } catch (Exception e) {
            if (LOGGER != null) {
                LOGGER.warning("exc: (" + e + ")");
            }
            return null;
        }
    }   
    /**
     * formats a string as big decimal
     */
    public static BigDecimal formatStringToBigDecimal(String s) {
        // MODIF -gestire null ? O ZERO ?_____.____
        if (LOGGER != null) {
            LOGGER.fine("formatStringToBigDecimal: trying: (" + s + ")");        
        }
        DecimalFormat format = new DecimalFormat();
        try {
            Number n = format.parse(normalize(s));
            return new BigDecimal(n.doubleValue());
        } catch (Exception e) {
            if (LOGGER != null) {
                LOGGER.warning("exc: (" + e + ")");
            }
            return null;
        }
   }    
    /**
     * formats a string as integer
     */
    public static int formatStringToInt(String s) {
        // MODIF -gestire null _____.____
        DecimalFormat format = new DecimalFormat();
        try {
            Number n = format.parse(normalize(s));
            return n.intValue();
        } catch (Exception e) {
            if (LOGGER != null) {
                LOGGER.warning("formatStringToInt exc: (" + e + ")");
            }
            return 0;
        }
   }    
    
    /**
     * Returns a styled JButton.
     * @param style button type
     * @return a styled button
     */
    public static JButton creaStyledButton(int style) {
        JButton jb = new JButton();
        jb.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jb.setMargin(new Insets(0, 5, 1, 5));
        switch (style) {
            case STYLE_EDIT: {
                jb.setText("edit");
                jb.setToolTipText("edit");
                jb.setPreferredSize(new Dimension(60,30));
                return jb;
            }
            case STYLE_OK: {
                jb.setText("ok");
                jb.setToolTipText("confirm changes");
                jb.setPreferredSize(new Dimension(60,30));
                return jb;
            }
            case STYLE_CANCEL: {
                jb.setText("cancel");
                jb.setToolTipText("discard changes");
                jb.setPreferredSize(new Dimension(60,30));
                return jb;
            }
            case STYLE_INSBEFOREROW: {
                jb.setIcon(ICON_INSBEFOREROW);
                jb.setToolTipText("insert row before");
                jb.setName("INSBEFOREROW");
                return jb;
            }
            case STYLE_INSAFTERROW: {
                jb.setIcon(ICON_INSAFTERROW);
                jb.setToolTipText("insert row after");
                jb.setName("INSAFTERROW");
                return jb;
            }
            case STYLE_DELETEROW: {
                jb.setIcon(ICON_DELETEROW);
                jb.setToolTipText("delete row");
                jb.setName("DELETEROW");
                return jb;
            }
            case STYLE_CLONEBEFOREROW: {
                jb.setIcon(ICON_CLONEBEFOREROW);
                jb.setToolTipText("clone row before");
                jb.setName("CLONEBEFOREROW");
                return jb;
            }
            case STYLE_CLONEAFTERROW: {
                jb.setIcon(ICON_CLONEAFTERROW);
                jb.setToolTipText("clone row after");
                jb.setName("CLONEAFTERROW");
                return jb;
            }
            case STYLE_DEFAULTROWS: {
                jb.setIcon(ICON_DEFAULTROWS);
                jb.setToolTipText("set default values");
                jb.setName("DEFAULTROWS");
                return jb;
            }
            default: {
                return null;
            }
        }
    }
    /**
     * visualizzazione errori HTML
     * @param frame frame di provenienza (di solito e' <code>this</code>).
     * @param task task di provenienza dell'errore (di solito e' <code>TASK_NAME</code>).
     * @param mess messaggio da visualizzare
     */
    public static void dispErrore(JFrame frame,String task,String mess) {
        //
        JOptionPane.showMessageDialog(frame,formattaHTML(mess),ERR_TITLE,
        JOptionPane.ERROR_MESSAGE);
    }
    /**
     * visualizzazione errori HTML
     * @param iframe internal frame di provenienza (di solito e' <code>this</code>).
     * @param task task di provenienza dell'errore (di solito e' <code>TASK_NAME</code>).
     * @param mess messaggio da visualizzare
     */
    public static void dispInternalErrore(JInternalFrame iframe,String task,String mess) {
        //
        JOptionPane.showInternalMessageDialog(iframe,formattaHTML(mess),ERR_TITLE,
        JOptionPane.ERROR_MESSAGE);
    }
    /**
     * visualizzazione errori HTML - visualizza il messaggio da tabella errori.
     * @param frame frame di provenienza (di solito e' <code>this</code>).
     * @param task task di provenienza dell'errore (di solito e' <code>TASK_NAME</code>).
     * @param error codice del messaggio
     * @param mess ulteriori dati da visualizzare
     */
    public static void dispErrore(JFrame frame,String task,String error,String mess) {
        //
        JOptionPane.showMessageDialog(frame,componiStringaErrore(error,mess),ERR_TITLE,
        JOptionPane.ERROR_MESSAGE);
    }
    /**
     * visualizzazione errori HTML - visualizza il messaggio da tabella errori.
     * @param iframe internal frmae di provenienza (di solito e' <code>this</code>).
     * @param task task di provenienza dell'errore (di solito e' <code>TASK_NAME</code>).
     * @param error codice del messaggio
     * @param mess ulteriori dati da visualizzare
     */
    public static void dispInternalErrore(JInternalFrame iframe,String task,String error,String mess) {
        //
        JOptionPane.showMessageDialog(iframe,componiStringaErrore(error,mess),ERR_TITLE,
        JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * visualizzazione messaggio HTML
     * @param pFrame frame di provenienza (di solito e' <code>this</code>).
     * @param pTask task di provenienza del messaggio (di solito e' <code>TASK_NAME</code>).
     * @param pMessaggio messaggio da visualizzare
     * @return JOptionPane.CANCEL_OPTION o OK_OPTION
     */
    public static int dispWarning(JFrame pFrame,String pTask,String pMessaggio) {
        //
        return(JOptionPane.showConfirmDialog(pFrame,formattaHTML(pMessaggio),WARNING_TITLE,
        JOptionPane.OK_CANCEL_OPTION));
    }
    
    /**
     * visualizzazione messaggio HTML da tabella errori.
     * @param pFrame frame di provenienza (di solito e' <code>this</code>).
     * @param pTask task di provenienza del messaggio (di solito e' <code>TASK_NAME</code>).
     * @param pError codice del messaggio
     * @param pMessaggio ulteriori dati da visualizzare
     * @return JOptionPane.CANCEL_OPTION o OK_OPTION
     */
    public static int dispWarning(JFrame pFrame,String pTask,String pError,String pMessaggio) {
        //
        return(JOptionPane.showConfirmDialog(pFrame,componiStringaWarning(pError,pMessaggio),WARNING_TITLE,
        JOptionPane.OK_CANCEL_OPTION));
    }
    
    /**
     * visualizzazione messaggio HTML
     * @param pFrame frame di provenienza (di solito e' <code>this</code>).
     * @param pTask task di provenienza del messaggio (di solito e' <code>TASK_NAME</code>).
     * @param pMessaggio messaggio da visualizzare
     */
    public static void dispAlert(JFrame pFrame,String pTask,String pMessaggio) {
        //
        JOptionPane.showMessageDialog(pFrame,formattaHTML(pMessaggio),ALERT_TITLE,
        JOptionPane.OK_CANCEL_OPTION);
    }
    
    /**
     * funzioni di utilita'
     */
    private static String formattaHTML(String pStr) {
        return ("<HTML><BODY><P>" + pStr + "</P></BODY></HTML>");
    }
    
    private static String componiStringaErrore(String pError,String pMessaggio) {
        String deserr = MSG_UNKNOWN;
        for (int i = 0; i < taberr.length; i++) {
            if (taberr[i][0].equalsIgnoreCase(pError)) {
                deserr = taberr[i][1];
            }
        }
        
        return formattaHTML("<p align=center><font color='red'>" + ERR_TITLE + "</font></p>" +
        "<font color='blue'>" + pError + "</font><br>" +
        "<font color='blue'>" + deserr + "</font><br>" +
        "<font color='red'>" + pMessaggio + "</font><br>" +
        "<font color='black'><i>contatto:</i></font>&nbsp;" +
        "<font color='black'>assist_gest@itelnet.it</font><br>");
    }
    
    private static String componiStringaWarning(String pWarn,String pMessaggio) {
        String deswarn = MSG_UNKNOWN;
        for (int i = 0; i < tabwarn.length; i++) {
            if (tabwarn[i][0].equalsIgnoreCase(pWarn)) {
                deswarn = tabwarn[i][1];
            }
        }
        
        return formattaHTML("<p align=center><font color='green'>" + WARNING_TITLE + "</font></p>" +
        "<font color='blue'>" + pWarn + "</font><br>" +
        "<font color='blue'>" + deswarn + "</font><br>" +
        "<font color='green'>" + pMessaggio + "</font><br>" +
        "<font color='black'><i>contatto:</i></font>&nbsp;" +
        "<font color='black'>assist_gest@itelnet.it</font><br>");
    }
    
    /**
     * Toglie placeholder e spazi all'inizio e alla fine.
     * @param s valore da normalizzare
     * @return valore normalizzato
     */
    public static String normalize(String s) {
        String s1 = s.replaceAll("_*$","");
        return s1.trim();
    }
    
    /**
     * Centra un frame e ne setta le dimensioni
     */
    public static void locateForm(JFrame f,int x, int y, int w, int h) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        x = (screenSize.width - w) / 2;
        y = (screenSize.height - h) / 2;
        f.setBounds(x,y,w,h);
    }

    /**
     * Centra una finestra all'interno di un'altra
     * @param f componente target (finestra esterna) - se null centra nello schermo
     * @param c componente da centrare (finestra interna)
     */
    public static void centerForm(Component f, Component c) {
        Rectangle rf = new Rectangle();;
        if (f == null) {
            rf = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        } else {
            rf = f.getBounds();
        }
        Rectangle rc = c.getBounds();
        
        int x = (rf.width - rc.width) / 2;
        if (x < 5) { 
            x = 5;
        }
        int y = (rf.height - rc.height) / 2;
        if (y < 5) { 
            y = 5;
        }
        
        if (f == null) {
            c.setLocation(x,y);
        } else {
            c.setLocation(x + f.getX(),y + f.getY());
        }
    }
    /**
     * Setta il focus su form modali
     * @param target componente che ricevera' il focus
     */
    public static void postponeFocus(final Component target) {
         SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        target.dispatchEvent(new FocusEvent(target, FocusEvent.FOCUS_GAINED));
                     }
                    });
    }    
    
    /**
     * When TAB is pressed should be possible to get the focus on
     * the next component.
     * See com.simeosoft.form.test.JTextAreaFocusInverter for credits
     */
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
                static final long serialVersionUID = 12222323;    
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