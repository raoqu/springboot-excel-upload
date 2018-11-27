/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.noryal.draw.modal;

import lombok.AllArgsConstructor;

/**
 * @author raoqu
 * @version $Id: FileModal.java v0.1 2018/11/22 下午9:39 raoqu Exp$
 */
public class FileModal {
    private String filename;
    private String url;

    public FileModal(String filename, String url) {
        this.filename = filename;
        this.url = url;
    }
}
