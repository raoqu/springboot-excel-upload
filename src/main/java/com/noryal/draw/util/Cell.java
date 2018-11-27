/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.noryal.draw.util;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IntegerField;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author raoqu
 * @version $Id: Cell.java v0.1 2018/11/22 下午11:40 raoqu Exp$
 */
public class Cell {
    /** poi cell */
    private XSSFCell cell;

    public Cell(XSSFRow row, int col) {
        this.cell = row.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(getText());
    }

    public String getText() {
        Object obj = getObject();
        String str = null;

        if (obj != null) {
            str = obj.toString();
        }

        return str;
    }

    public void setText(String text) {
        cell.setCellType(CellType.STRING);
        cell.setCellValue(text);
    }

    public void setInt(int val){
        cell.setCellType(CellType.NUMERIC);
        cell.setCellValue(new Double(new Integer(val).toString()));

    }

    public Object getObject() {
        Object obj = null;
        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
                case BOOLEAN:
                    obj = cell.getBooleanCellValue();
                    break;
                case ERROR:
                    obj = cell.getErrorCellValue();
                    break;
                case FORMULA:
                    try {
                        obj = String.valueOf(cell.getStringCellValue());
                    } catch (IllegalStateException e) {
                        String valueOf = String.valueOf(cell.getNumericCellValue());
                        BigDecimal bd = new BigDecimal(Double.valueOf(valueOf));
                        bd = bd.setScale(2, RoundingMode.HALF_UP);
                        obj = bd;
                    }
                    break;
                case NUMERIC:
                    obj = cell.getNumericCellValue();
                    break;
                case STRING:
                    obj = cell.getStringCellValue();
//                    value = value.replace(" ", "");
//                    value = value.replace("\n", "");
//                    value = value.replace("\t", "");
                    break;
                default:
                    break;
            }
        }

        return obj;
    }
}
