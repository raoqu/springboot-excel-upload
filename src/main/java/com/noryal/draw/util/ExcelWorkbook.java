/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.noryal.draw.util;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * @author raoqu
 * @version $Id: ExcelWorkbook.java v0.1 2018/11/22 下午11:36 raoqu Exp$
 */
public class ExcelWorkbook {
    private XSSFWorkbook workbook;

    public ExcelWorkbook(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public void close() {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
            }
        }
    }

    public ExcelSheet getSheet(int index) {
        return new ExcelSheet(workbook.getSheetAt(index));
    }
}
