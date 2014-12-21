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

import java.math.BigDecimal;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Some utilities for xls generation (using POI library)
 */
public abstract class XlsUtils {
    
    /**
     * Crea un documento xsl
     * @param sheetName nome del foglio di lavoro
     * @param data array list di array list di oggetti con i dati
     */
    public static HSSFWorkbook doXls(String sheetName,ArrayList<ArrayList<Object>> data) {
        HSSFWorkbook wb = initXls();
        addXlsWorksheet(wb,sheetName,data);
        return wb;
    }
    
    public static HSSFWorkbook initXls() {
        HSSFWorkbook wb = new HSSFWorkbook();
        return wb;
    }
    
    public static void addXlsWorksheet(HSSFWorkbook wb,String sheetName,ArrayList<ArrayList<Object>> data) {
        HSSFSheet s = wb.createSheet(sheetName);
        HSSFRow r = null;
        HSSFCell c = null;
        int i = 0;
        HSSFDataFormat df = wb.createDataFormat();
        HSSFCellStyle cs = wb.createCellStyle();        
        for (ArrayList<Object> record : data) {
            r = s.createRow(i);
            i++;
            short y = 0;
            /*
             * tipi dato previsti: null,String,Date,Integer,Time,Timestamp
             * - gli altri tipi restituiscono errore
             */
            for (Object obj : record) {
                c = r.createCell(y);
                y++;
                if (obj == null) {
                    c.setCellValue("");
                    continue;
                }
                if (obj instanceof String) {
                    c.setCellValue((String) obj);
                    continue;
                }
                // MODIF - java.util.Date o java.sql.Date?                
                if (obj instanceof java.sql.Date || obj instanceof java.sql.Timestamp
                        || obj instanceof java.sql.Time) {
                    HSSFCellStyle csd = wb.createCellStyle();
                    csd.setDataFormat(HSSFDataFormat.getBuiltinFormat("mm/dd/yyyy"));                    
                    c.setCellValue((java.sql.Date) obj);
                    c.setCellStyle(csd);
                    continue;
                }
                if (obj instanceof Integer) {
                    c.setCellValue(((Integer)obj).doubleValue());
                    continue;
                }
                if (obj instanceof BigDecimal) {
                    c.setCellValue(((BigDecimal)obj).doubleValue());
                    cs.setDataFormat(df.getFormat("######0.0000"));
                    c.setCellStyle(cs);
                    continue;
                }
                // default - non previsto                
                c.setCellValue("ERRORE: TIPO NON PREVISTO: " + obj.getClass());
            }
        }
    }
}
