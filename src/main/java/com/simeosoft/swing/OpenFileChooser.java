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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Enhanced File Chooser
 * @author simeo
 */
public class OpenFileChooser extends JFileChooser {
    static final long serialVersionUID = 12222325;    
    String fileType = "";
    String fileDescr = "";
    /**
     * Build a OpenFileChooser
     * @param currentDirectoryPath current directory
     * @param fileType file type (e.g. ".xml")
     * @param fileDescr file description (e.g. "Text File (.txt)")
     */
    public OpenFileChooser(String currentDirectoryPath, final String fileType, final String fileDescr) {
        super( currentDirectoryPath );
        this.fileType = fileType;
        this.fileDescr = fileDescr;
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
