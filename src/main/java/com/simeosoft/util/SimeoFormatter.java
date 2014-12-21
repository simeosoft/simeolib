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
package com.simeosoft.util;

import java.text.*;
import java.util.Date;
/**
 *
 * @author  simeo
 */
public class SimeoFormatter extends java.util.logging.Formatter {
    String crlf = System.getProperty("line.separator");
    /** Creates a new instance of SimeoFormatter */
    public SimeoFormatter() {
    }
    
    public String format(java.util.logging.LogRecord record) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "[" + format.format(new Date(record.getMillis())) + "] [" + 
            record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".") + 1) + "] [" + record.getSourceMethodName() + "] " +
            record.getMessage() + crlf;
    }
}
