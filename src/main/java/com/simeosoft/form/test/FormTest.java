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

import com.simeosoft.form.AddFieldException;
import com.simeosoft.form.FieldException;
import com.simeosoft.form.FormController;
import com.simeosoft.form.IFormController.regexType;
import com.simeosoft.form.IFormListener;
import com.simeosoft.swing.SwingUtils;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JComboBox;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 * Test case of most of the com.simeosoft.form package components.
 * <br>$Id: FormTest.java 16 2007-06-11 10:40:29Z simeo $
 */
public class FormTest extends javax.swing.JFrame implements IFormListener, ItemListener {
    static final long serialVersionUID = 12222329;    
    FormController fc;
    
    // DefineComponent dc;
    Logger LOGGER = null;
    /** Creates new form TestJFTF */
    public FormTest() {
        initComponents();
        LOGGER = Logger.getLogger("SwingUtilsTest");
        LOGGER.setLevel(Level.ALL);
        
        Locale.setDefault(new Locale("en","US"));        
        
        fc = new FormController(this, jlMessage, LOGGER);

        this.setBounds(200, 200, 623,  569);
        defineComponent();
        jcbLocale.addItem(new Locale("it","IT"));
        jcbLocale.addItem(new Locale("en","US"));
        jcbLocale.addItem(new Locale("en","GB"));     
        jcbLocale.addItemListener(this);
        
        //
        DecimalFormat nf = new DecimalFormat();
        DecimalFormatSymbols dfs =  nf.getDecimalFormatSymbols();
        dfs.setDecimalSeparator(fc.getDecimalPoint().charAt(0));
        jlMessage.setText("Start - Locale changed: now:" + Locale.getDefault().toString() + " dec.symb (locale): " + 
                dfs.getDecimalSeparator() + "  dec symb: fixed: " + fc.getDecimalPoint());
    }
    
    public void itemStateChanged(ItemEvent ie) {
        JComboBox jcb = (JComboBox) ie.getSource();
        Locale loc = (Locale) jcb.getSelectedItem();
        Locale.setDefault(loc);
        DecimalFormat nf = new DecimalFormat();
        DecimalFormatSymbols dfs = nf.getDecimalFormatSymbols();
        jlMessage.setText("Locale changed: now:" + loc.toString() + " dec.symb: " + dfs.getDecimalSeparator());
        fc.setDecimalPoint(String.valueOf(dfs.getDecimalSeparator()));
        fc.refreshLocale();
    }
    private void defineComponent() {
        try {
            /*
            if (jCheckBox1.isSelected()) {
            } else {
            }
             */
            fc.addTextField(ft20, "text 20", 20, false);
            fc.addTextField(ft100, "text 100", 100, false);
            fc.addTextField(jta1, "textarea1", 255, false);
            fc.addDateField(fdate,"date field", false);
            fc.addTimeField(ftime, "time field", false);
            fc.addTimestampField(ftimestamp, "timestamp field", false);
            fc.addIntegerField(fint2u, "integer 2 unsigned", 2,  false, false);
            fc.addIntegerField(fint2s, "integer 2 signed", 2,  false, true);
            fc.addIntegerField(fint10u, "integer 10 unsigned", 10, false, false);
            fc.addIntegerField(fint10s, "integer 10 signed", 10, false, true);
            fc.addDecimalField(fdec32u, "decimal 3 2 unsigned", 3, 2, false, false);
            fc.addDecimalField(fdec32s, "decimal 3 2 signed", 3, 2, false, true);
            fc.addDecimalField(fdec95u, "decimal 9 5 unsigned", 9, 5, false, false);
            fc.addDecimalField(fdec95s, "decimal 9 5 signed", 9, 5, false, true);
            fc.addTextField(ipaddr,"ip address",16,false,regexType.IP_ADDRESS);
            fc.addTextField(email,"e-mail",40,false,regexType.EMAIL);
            fc.addTextField(url,"url",40,false,regexType.URL);
            fc.addTextField(creditcard,"credit card",20,false,regexType.CREDIT_CARD);
            //
            // fc.addDecimalField(fdec95u, "decimal 9 5", 23, 34, false); // should throws exception
        } catch (AddFieldException e) {
            System.out.println("Formatting error: " + e);
        }
    }
    private void invioActionPerformed() {
        try {
            fc.check();
        } catch (FieldException fe) {
            JOptionPane.showMessageDialog(this, fe.getLocalizedMessage(), "Warning!",JOptionPane.WARNING_MESSAGE);
            System.out.println("focusable: "+fe.getFocusableComponent());            
            if (fe.getFocusableComponent() != null) {
                SwingUtils.postponeFocus(fe.getFocusableComponent());
            }
        }
            
//        } catch (MultipleFieldException me) {
//            System.out.println("************ errori multipli "+me);
//            StringBuffer s1 = new StringBuffer();
//            ArrayList<String> err = me.getErrors();
//            ArrayList<String> desc = me.getDesc();
//            for (int i=0;i<err.size();i++) {
//                s1.append(err.get(i) + " campo: " + desc.get(i) +"\n");
//            }
//            JOptionPane.showMessageDialog(this,s1);
//            jlMessage.setText(me.getLocalizedMessage());
//        } catch (SingleFieldException se) {
//            System.out.println("************ Errore singolo: "+se);
//            JOptionPane.showMessageDialog(this,se);
//            jlMessage.setText(se.getLocalizedMessage());
//        }
        
       
        System.out.println("------\n" +
                "testo 20:      [" + fc.getString(ft20)+ "]\n" +
                "testo 100:     [" + fc.getString(ft100) + "]\n" +
                "testo 255      [" + fc.getString(jta1)+ "]\n"+
                "date:          [" + fc.getDate(fdate) + "]\n" +
                "time:          [" + fc.getTime(ftime) +"]\n" +
                "timestamp:     [" + fc.getTimestamp(ftimestamp) +"]\n" +
                "integer 2:     [" + fc.getInt(fint2u) +"]\n" +
                "integer 2 s:   [" + fc.getInt(fint2s) +"]\n" +
                "integer 10:    [" + fc.getInt(fint10u) +"]\n" +
                "integer 10 s:  [" + fc.getInt(fint10s) +"]\n" +
                "decimal 3,2:   [" + fc.getDecimal(fdec32u) +"]\n" +
                "decimal 3,2 s: [" + fc.getDecimal(fdec32s) +"]\n" +
                "decimal 9,5:   [" + fc.getDecimal(fdec95u) +"]\n" +           
                "decimal 9,5 s: [" + fc.getDecimal(fdec95s) +"]\n" +
                "ipaddress:     [" + fc.getString(ipaddr) +"]\n" +
                "email:         [" + fc.getString(email) +"]\n" +
                "url:           [" + fc.getString(url) +"]\n" +
                "creditcard:    [" + fc.getString(creditcard) +"]\n");              
    }
    
    private void defaultActionPerformed() {
        fc.setString(ft20, "ahdg kjhgh");
        fc.setString(ft100, "akljfafjimisihfxmsdhfmxsahdfmxsuadfufhxm sadufxmasudfx asdhgas aduhfmxufsdhxmuisdhfmxashfmxas hhhhh");
        fc.setString(jta1, "akljfafjimisihfxmsdhfmxsahdfmxsuadfufhxm sadufxmasudfx asdhgas aduhfmxufsdhxmuisdhfmxashfmxas hhhhh dfsadf dfdfd ");
        fc.setDate(fdate, new Date(123456789));
        //System.out.println(new Date(123456789).toString());
        fc.setTime(ftime, new Date(18640000));
        fc.setTimestamp(ftimestamp, new Date(123456789));
        fc.setInt(fint2u, 67);
        fc.setInt(fint2s, -98);
        fc.setInt(fint10u, 3344);
        fc.setInt(fint10s, -7766);
        fc.setDecimal(fdec32u, new BigDecimal("2.12"));
        fc.setDecimal(fdec32s, new BigDecimal("-2.12"));
        fc.setDecimal(fdec95u, new BigDecimal("345.23"));
        fc.setDecimal(fdec95s, new BigDecimal("-345.23"));
        fc.setString(ipaddr, "15.8.161.108");        
        fc.setString(email, "pippo@pippo.it");        
        fc.setString(url, "http://aa");        
        fc.setString(creditcard, "6011-1234-1234-1234");        
        jlChanged.setText("NOT CHANGED");
        jlChanged.setForeground(Color.BLACK);        
    }
   
    // simula un caricamento da recordset
    private void caricaActionPerformed() {
        fc.setString(ft20,"carica string a");
        fc.setString(ft100,"carica string b");
        fc.setString(jta1,"carica string c\ncarica string c");
        java.sql.Date d = new java.sql.Date(System.currentTimeMillis());
        fc.setDate(fdate,d);
        fc.setTime(ftime,d);
        fc.setTimestamp(ftimestamp,d);
        fc.setInt(fint2u,new Integer(88));
        fc.setInt(fint2s,new Integer(-88));
        fc.setInt(fint10u,new Integer(34567));
        fc.setInt(fint10s,new Integer(-34567));
        fc.setDecimal(fdec32u, new BigDecimal("123.12"));
        fc.setDecimal(fdec32s, new BigDecimal("-123.12"));
        fc.setDecimal(fdec95u, new BigDecimal("123456789.1234"));
        fc.setDecimal(fdec95s, new BigDecimal("-123456789.1234"));
        jlChanged.setText("NOT CHANGED");
        jlChanged.setForeground(Color.BLACK);        
    }
    public void changed(JComponent jc) {
        jlChanged.setText("CHANGED");
        jlChanged.setForeground(Color.RED);
    }

    // FIXME
    private void caricaNull() {
        fc.setString(ft20,null);
        fc.setString(ft100,null);
        fc.setString(jta1,null);
        Object a = null;
        fc.setDate(fdate,(Date) a);
        fc.setTime(ftime,(Time) a);
        fc.setTimestamp(ftimestamp,(Timestamp) a);
        fc.setInt(fint2u,0);
        fc.setInt(fint2s,0);
        fc.setInt(fint10u,0);
        fc.setInt(fint10s,0);
        fc.setDecimal(fdec32u,null);
        fc.setDecimal(fdec32s,null);
        fc.setDecimal(fdec95u,null);
        fc.setDecimal(fdec95s,null);
        jlChanged.setText("NOT CHANGED");
        jlChanged.setForeground(Color.BLACK);         
    }

    private void caricaNonValidi() {
        ft20.setText("123456789012345678923452525252352");
        ft100.setText("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        jta1.setText("");
        fdate.setText("32/13/2005");
        // FIXME - completare
//        fc.setTime(ftime,(Time) a);
//        fc.setTimestamp(ftimestamp,(Timestamp) a);
//        fc.setInt(fint2u,0);
//        fc.setInt(fint2s,0);
//        fc.setInt(fint10u,0);
//        fc.setInt(fint10s,0);
//        fc.setDecimal(fdec32u,null);
//        fc.setDecimal(fdec32s,null);
//        fc.setDecimal(fdec95u,null);
//        fc.setDecimal(fdec95s,null);
        jlChanged.setText("NOT CHANGED");
        jlChanged.setForeground(Color.BLACK);         
    }
    
    // simula il metodo pulisciform
    private void clearActionPerformed() {
        fc.clear();
        jlChanged.setText("NOT CHANGED");
        jlChanged.setForeground(Color.BLACK);        
        ft20.requestFocus();        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new FormTest().setVisible(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ft20 = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        ft100 = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jta1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        fdate = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        ftime = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        ftimestamp = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        fint2u = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        fint2s = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        fint10u = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        fint10s = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        fdec32u = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        fdec32s = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        fdec95u = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        fdec95s = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        ipaddr = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        email = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        url = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        creditcard = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        f10 = new javax.swing.JFormattedTextField();
        jlMessage = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jlChanged = new javax.swing.JLabel();
        jcbLocale = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jbInvio = new javax.swing.JButton();
        jbDefault = new javax.swing.JButton();
        jbCarica = new javax.swing.JButton();
        jbClear = new javax.swing.JButton();
        jbNulls = new javax.swing.JButton();
        jbNotValid = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setTitle("FormTest");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setMinimumSize(new java.awt.Dimension(200, 19));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 19));
        jLabel1.setText("text 20");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel1, gridBagConstraints);

        ft20.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        ft20.setMinimumSize(new java.awt.Dimension(200, 19));
        ft20.setPreferredSize(new java.awt.Dimension(200, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(ft20, gridBagConstraints);

        jLabel2.setText("text 100");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        ft100.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        ft100.setMinimumSize(new java.awt.Dimension(400, 19));
        ft100.setPreferredSize(new java.awt.Dimension(400, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(ft100, gridBagConstraints);

        jLabel10.setText("textarea 255");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel10, gridBagConstraints);

        jScrollPane1.setBorder(new javax.swing.border.EtchedBorder());
        jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 80));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 80));
        jta1.setLineWrap(true);
        jta1.setWrapStyleWord(true);
        jta1.setBorder(null);
        jta1.setMinimumSize(new java.awt.Dimension(0, 0));
        jta1.setPreferredSize(new java.awt.Dimension(500, 80));
        jta1.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(jta1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel3.setText("date");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel3, gridBagConstraints);

        fdate.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fdate.setMinimumSize(new java.awt.Dimension(100, 19));
        fdate.setPreferredSize(new java.awt.Dimension(100, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(fdate, gridBagConstraints);

        jLabel4.setText("time");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel4, gridBagConstraints);

        ftime.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        ftime.setMinimumSize(new java.awt.Dimension(40, 19));
        ftime.setPreferredSize(new java.awt.Dimension(40, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(ftime, gridBagConstraints);

        jLabel5.setText("timestamp");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel5, gridBagConstraints);

        ftimestamp.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        ftimestamp.setMinimumSize(new java.awt.Dimension(150, 19));
        ftimestamp.setPreferredSize(new java.awt.Dimension(150, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(ftimestamp, gridBagConstraints);

        jLabel6.setText("integer 2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel6, gridBagConstraints);

        fint2u.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fint2u.setMinimumSize(new java.awt.Dimension(40, 19));
        fint2u.setPreferredSize(new java.awt.Dimension(40, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(fint2u, gridBagConstraints);

        jLabel12.setText("integer 2 s");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel12, gridBagConstraints);

        fint2s.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fint2s.setMinimumSize(new java.awt.Dimension(40, 19));
        fint2s.setPreferredSize(new java.awt.Dimension(40, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(fint2s, gridBagConstraints);

        jLabel7.setText("integer 10");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel7, gridBagConstraints);

        fint10u.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fint10u.setMinimumSize(new java.awt.Dimension(100, 19));
        fint10u.setPreferredSize(new java.awt.Dimension(100, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(fint10u, gridBagConstraints);

        jLabel13.setText("integer 10 s");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel13, gridBagConstraints);

        fint10s.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fint10s.setMinimumSize(new java.awt.Dimension(100, 19));
        fint10s.setPreferredSize(new java.awt.Dimension(100, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(fint10s, gridBagConstraints);

        jLabel8.setText("decimal 3,2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel8, gridBagConstraints);

        fdec32u.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fdec32u.setMinimumSize(new java.awt.Dimension(50, 19));
        fdec32u.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(fdec32u, gridBagConstraints);

        jLabel14.setText("decimal 3,2 s");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel14, gridBagConstraints);

        fdec32s.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fdec32s.setMinimumSize(new java.awt.Dimension(50, 19));
        fdec32s.setPreferredSize(new java.awt.Dimension(50, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(fdec32s, gridBagConstraints);

        jLabel9.setText("decimal 9,5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel9, gridBagConstraints);

        fdec95u.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fdec95u.setMinimumSize(new java.awt.Dimension(200, 19));
        fdec95u.setPreferredSize(new java.awt.Dimension(200, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(fdec95u, gridBagConstraints);

        jLabel15.setText("decimal 9,5 s");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel15, gridBagConstraints);

        fdec95s.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fdec95s.setMinimumSize(new java.awt.Dimension(200, 19));
        fdec95s.setPreferredSize(new java.awt.Dimension(200, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(fdec95s, gridBagConstraints);

        jLabel17.setText("ip address");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel17, gridBagConstraints);

        ipaddr.setMinimumSize(new java.awt.Dimension(200, 19));
        ipaddr.setPreferredSize(new java.awt.Dimension(200, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(ipaddr, gridBagConstraints);

        jLabel18.setText("e-mail");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel18, gridBagConstraints);

        email.setMinimumSize(new java.awt.Dimension(200, 19));
        email.setPreferredSize(new java.awt.Dimension(200, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(email, gridBagConstraints);

        jLabel19.setText("url");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel19, gridBagConstraints);

        url.setMinimumSize(new java.awt.Dimension(200, 19));
        url.setPreferredSize(new java.awt.Dimension(200, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(url, gridBagConstraints);

        jLabel20.setText("credit card");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel20, gridBagConstraints);

        creditcard.setMinimumSize(new java.awt.Dimension(200, 19));
        creditcard.setPreferredSize(new java.awt.Dimension(200, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(creditcard, gridBagConstraints);

        jLabel11.setText("jftf no decoro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel11, gridBagConstraints);

        f10.setText("jFormattedTextField1");
        f10.setMinimumSize(new java.awt.Dimension(128, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(f10, gridBagConstraints);

        jlMessage.setBackground(new java.awt.Color(255, 153, 102));
        jlMessage.setText("messaggio");
        jlMessage.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanel1.add(jlMessage, gridBagConstraints);

        jCheckBox1.setText("???");
        jCheckBox1.setOpaque(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jCheckBox1, gridBagConstraints);

        jlChanged.setText("NOT CHANGED");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel1.add(jlChanged, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(jcbLocale, gridBagConstraints);

        jLabel16.setText("Locale:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(jLabel16, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.weighty = 100.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jbInvio.setText("invio");
        jbInvio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbInvioActionPerformed(evt);
            }
        });

        jPanel2.add(jbInvio);

        jbDefault.setText("default");
        jbDefault.setOpaque(false);
        jbDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDefaultActionPerformed(evt);
            }
        });

        jPanel2.add(jbDefault);

        jbCarica.setText("carica");
        jbCarica.setOpaque(false);
        jbCarica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCaricaActionPerformed(evt);
            }
        });

        jPanel2.add(jbCarica);

        jbClear.setText("clear");
        jbClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClearActionPerformed(evt);
            }
        });

        jPanel2.add(jbClear);

        jbNulls.setText("carica valori null");
        jbNulls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNullsActionPerformed(evt);
            }
        });

        jPanel2.add(jbNulls);

        jbNotValid.setText("carica non validi");
        jbNotValid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNotValidActionPerformed(evt);
            }
        });

        jPanel2.add(jbNotValid);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents

    private void jbNotValidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNotValidActionPerformed
        caricaNonValidi();
    }//GEN-LAST:event_jbNotValidActionPerformed

    private void jbNullsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNullsActionPerformed
        caricaNull();
    }//GEN-LAST:event_jbNullsActionPerformed
    
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        defineComponent();
    }//GEN-LAST:event_jCheckBox1ActionPerformed
    
    private void jbClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbClearActionPerformed
        clearActionPerformed();
    }//GEN-LAST:event_jbClearActionPerformed
    
    private void jbCaricaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCaricaActionPerformed
        caricaActionPerformed();
    }//GEN-LAST:event_jbCaricaActionPerformed
    
    private void jbDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDefaultActionPerformed
        defaultActionPerformed();
    }//GEN-LAST:event_jbDefaultActionPerformed
    
    private void jbInvioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbInvioActionPerformed
        invioActionPerformed();
    }//GEN-LAST:event_jbInvioActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
   
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField creditcard;
    private javax.swing.JFormattedTextField email;
    private javax.swing.JFormattedTextField f10;
    private javax.swing.JFormattedTextField fdate;
    private javax.swing.JFormattedTextField fdec32s;
    private javax.swing.JFormattedTextField fdec32u;
    private javax.swing.JFormattedTextField fdec95s;
    private javax.swing.JFormattedTextField fdec95u;
    private javax.swing.JFormattedTextField fint10s;
    private javax.swing.JFormattedTextField fint10u;
    private javax.swing.JFormattedTextField fint2s;
    private javax.swing.JFormattedTextField fint2u;
    private javax.swing.JFormattedTextField ft100;
    private javax.swing.JFormattedTextField ft20;
    private javax.swing.JFormattedTextField ftime;
    private javax.swing.JFormattedTextField ftimestamp;
    private javax.swing.JFormattedTextField ipaddr;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCarica;
    private javax.swing.JButton jbClear;
    private javax.swing.JButton jbDefault;
    private javax.swing.JButton jbInvio;
    private javax.swing.JButton jbNotValid;
    private javax.swing.JButton jbNulls;
    private javax.swing.JComboBox jcbLocale;
    private javax.swing.JLabel jlChanged;
    private javax.swing.JLabel jlMessage;
    private javax.swing.JTextArea jta1;
    private javax.swing.JFormattedTextField url;
    // End of variables declaration//GEN-END:variables
    
}
