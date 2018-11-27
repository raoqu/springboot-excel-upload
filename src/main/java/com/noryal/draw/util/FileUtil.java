/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.noryal.draw.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author raoqu
 * @version $Id: FileUtil.java v0.1 2018/11/23 上午4:05 raoqu Exp$
 */
public class FileUtil {
    public static String getTempPath(String filename) {
        String path = System.getProperty("user.dir") + "/alidata/tmp";
        File tmpFile = new File(path);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        path = path + "/" + filename;
        return path;
    }
}
