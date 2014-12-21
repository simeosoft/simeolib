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
package com.simeosoft.util;

/**
 * Some utils.
 * $Id: Utils.java 2 2006-11-15 09:21:35Z simeo $
 */
public abstract class Utils {

    private static String VERSION = "1.1"; 
    /**
     * Returns the library version.
     * @return library version
     */
    public static String getRelease() {
        return VERSION;
    }
    
}
