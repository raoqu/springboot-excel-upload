package com.noryal.draw.controller;

import com.noryal.draw.modal.FileModal;
import com.noryal.draw.util.FileUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-upload-multiple-file-to-mysql
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 22/11/18
 * Time: 06.20
 */
@Controller
public class DownloadFileController {

//    @Autowired
//    FileRepository fileRepository;

    /*
     * Retrieve Files' Information
     */
    @GetMapping("/files")
    public String getListFiles(Model model) {
//
        String url = MvcUriComponentsBuilder.fromMethodName(DownloadFileController.class,
                "downloadFile", "abc").build().toString();
        String filename = "abc";//fileModel.getName();
        List<FileModal> fileInfos = new ArrayList<>();
        fileInfos.add(new FileModal(filename, url));

        model.addAttribute("files", filename);
        return "listfiles";
    }

    /*
     * Download Files
     */
    @GetMapping("/files/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
        String fileName = null;
        try {
            fileName = URLEncoder.encode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            fileName = filename;
        }

        String path = FileUtil.getTempPath(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .body(File2byte(path));
    }


    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return buffer;
    }
}