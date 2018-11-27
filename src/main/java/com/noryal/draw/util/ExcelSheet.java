/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.noryal.draw.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * @author raoqu
 * @version $Id: ExcelSheet.java v0.1 2018/11/22 下午11:35 raoqu Exp$
 */
public class ExcelSheet {

    /** excel sheet */
    private XSSFSheet sheet;

    public ExcelSheet(XSSFSheet sheet) {
        this.sheet = sheet;
    }

    /**
     * 获取sheet的数据行数
     * @return 行数
     */
    public int getRowNum() {
        return sheet.getLastRowNum();
    }

    /**
     * 获取指定行、列所在位置的单元格
     * @param row 行序号
     * @param col 列序号
     * @return 单元格
     */
    public Cell getCell(int row, int col) {
        return new Cell(sheet.getRow(row), col);
    }

    /**
     * 获取指定行的列数
     * @param row
     * @return
     */
    public int getColumnCount(int row) {
        XSSFRow objRow = sheet.getRow(row);
        return objRow.getLastCellNum();
    }

    /**
     * 获取excel sheet的实际有效行数
     * @implNote 这种方法效率较低
     * @return 实际有效行数
     */
    public int getActureRowNum() {
        return getActureRowNum(-1);
    }

    /**
     * 获取excel sheet 的实际有效行数
     * @param checkCol 根据指定列是否为空来判断该行是否为空，<0时检查该行的所有单元格
     * @return 实际有效行数
     */
    public int getActureRowNum(int checkCol) {
        int rowNum = getRowNum();
        for (int i = rowNum; i >= 0; i--) {
            if (!isRowEmpty(i, checkCol)) {
                return i;
            }
        }

        return 0;
    }

    /**
     * 判断指定行的单元格是否为空，当checkCol<0时检查是否整行为空
     *
     * @param row 行序号
     * @param checkCol 如果<0，则检查是否整行为空
     * @return
     */
    private boolean isRowEmpty(int row, int checkCol) {
        XSSFRow objRow = sheet.getRow(row);
        if (checkCol >= 0) {
            Cell cell = new Cell(objRow, checkCol);
            return cell.isEmpty();
        } else {
            // 当checkCol<0，只要该行有一个单元格不为空就表明该行不为空
            int cellNum = objRow.getLastCellNum();
            for (int i = 0; i <= cellNum; i++) {
                if (!isRowEmpty(row, i)) {
                    return false;
                }
            }
        }
        return true;
    }
}
