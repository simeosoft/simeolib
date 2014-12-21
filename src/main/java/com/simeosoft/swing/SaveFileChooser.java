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

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Enhanced File Chooser
 * @author simeo
 */
public class SaveFileChooser extends JFileChooser {
    static final long serialVersionUID = 12222324;    
    String fileType = "";
    String fileDescr = "";
    /**
     * Build a SaveFileChooser
     * @param currentDirectoryPath current directory
     * @param fileType file type (e.g. ".xml")
     * @param fileDescr file description (e.g. "Text File (.txt)")
     */
    public SaveFileChooser(String currentDirectoryPath, final String fileType, final String fileDescr) {
        super(currentDirectoryPath);
        this.fileType = fileType;
        this.fileDescr = fileDescr;
        if (fileType != null) {
            this.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    }
                    String name = f.getName();
                    if (name.toLowerCase().endsWith(fileType)) {
                        return true;
                    }
                    return false;
                }
                public String getDescription() {
                    return fileDescr;
                }
            });
        }        
    }
    public void approveSelection() {
        File file = getSelectedFile();
        try {
            if (! file.getCanonicalPath().endsWith(fileType)) {
                file = new File(file.getCanonicalPath() + fileType);
            }
        } catch (IOException ioe) {
            return;
        }
        if (file.exists()) {
            String msg = String.format("File %s exists! Overwrite ?", file.getPath());
            int rc = JOptionPane.showConfirmDialog( this, msg, "Save As",JOptionPane.OK_CANCEL_OPTION);
            if (rc == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        super.approveSelection();
    }
}
