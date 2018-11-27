/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.noryal.draw.util;

import com.noryal.draw.util.ExcelWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author raoqu
 * @version $Id: ExcelUtil.java v0.1 2018/11/22 下午11:06 raoqu Exp$
 */
public class ExcelUtil {
    public static boolean RandomOrder(InputStream inputStream, OutputStream outputStream) {
        XSSFWorkbook wb = null;
        ExcelWorkbook workbook = null;
        try {
            wb = new XSSFWorkbook(inputStream);
            workbook = new ExcelWorkbook(wb);
        } catch (IOException e) {
            return false;
        }

        try {
            ExcelSheet sheet = workbook.getSheet(0);

            int colNum = sheet.getColumnCount(0);
            int rowNum = sheet.getActureRowNum(0);
            if (rowNum <= 0 || colNum < 3) {
                throw new RuntimeException("Excel文件格式不符合要求");
            }

            List<Integer> list = new ArrayList<>();
            for (int i = 1; i <= rowNum; i++) {
                list.add(new Integer(i));
            }
            for (int i = 1; i <= rowNum; i++) {
                Random random = new Random();
                int index = random.nextInt(list.size());
                Integer row = list.remove(index);
                sheet.getCell(row, 3).setInt(i);
            }

            wb.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Excel输出失败");
        } catch (RuntimeException re) {
            throw re;
        } finally {
            workbook.close();
        }

        return true;
    }

    public static String randomFileName(String originalFilename) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        Date date = new Date();
        String str = sdf.format(date);
        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        str = str + "_" + rannum;// 当前时间

        return str + getFilePrefix(originalFilename) + getFileSuffix(originalFilename);
    }

    public static String getFileSuffix(String originalFilename) {
        if (!StringUtils.isEmptyOrWhitespace(originalFilename)) {
            int idx = originalFilename.lastIndexOf(".");
            if (idx >= 0 && idx < originalFilename.length() - 1) {
                String suffix = "." + originalFilename.substring(idx + 1);
                return suffix;
            }
        }
        return "";
    }

    public static String getFilePrefix(String originalFilename) {
        if (!StringUtils.isEmptyOrWhitespace(originalFilename)) {
            int idx = originalFilename.lastIndexOf(".");
            if (idx >= 0) {
                String suffix = "_" + originalFilename.substring(0, idx);
                return suffix;
            }
        }
        return "";
    }
}
