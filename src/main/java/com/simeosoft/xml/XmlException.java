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
package com.simeosoft.xml;

/**
 * Xml Exception
 * @author Simeosoft
 * $Id: XmlException.java 16 2007-06-11 10:40:29Z simeo $
 */
public class XmlException extends java.lang.Exception {
    static final long serialVersionUID = 12222340;    
    
    /**
     * Creates a new instance of <code>XmlException</code> without detail message.
     */
    public XmlException() {
    }
    
    
    /**
     * Constructs an instance of <code>XmlException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public XmlException(String msg) {
        super(msg);
    }
}
