package com.noryal.draw.controller;

import com.noryal.draw.modal.FileModal;
import com.noryal.draw.util.ExcelUtil;
import com.noryal.draw.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class UploadFileController {

    //@Autowired
    //FileRepository fileRepository;

    @GetMapping("/")
    public String index() {
        return "uploadform";
    }

    @PostMapping("/")
    public String uploadMultipartFile(@RequestParam("files") MultipartFile[] files, Model model) throws IOException {
        List<String> fileNames = new ArrayList<String>();
        List<FileModal> downloadFiles = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        try {
            checkSize(files);
            fileNames = getFileNames(files);
            urls = processFiles(files);

            model.addAttribute("message", "文件上传成功!");
            model.addAttribute("files", fileNames);
            model.addAttribute("urls", urls);
        } catch (RuntimeException re) {
            model.addAttribute("message", "文件上传失败!");
            fileNames.add(re.getMessage());
            model.addAttribute("files", fileNames);
        } catch (IOException ie) {
            throw ie;
        } catch (Exception e) {
            model.addAttribute("message", "文件上传失败!");
            fileNames.add("未知错误");
            model.addAttribute("files", fileNames);
        }

        return "uploadform";
    }

    private void checkSize(MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (file.getSize() > 1024 * 1024 * 100) {
                throw new RuntimeException("文件尺寸过大: " + file.getOriginalFilename());
            }
        }
    }

    private List<String> getFileNames(MultipartFile[] files) {
        ArrayList<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            fileNames.add(file.getOriginalFilename());
        }
        return fileNames;
    }

    private List<String> processFiles(MultipartFile[] files) throws IOException {
        ArrayList<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String outputFilename = ExcelUtil.randomFileName(file.getOriginalFilename());
            // 处理Excel文件
            try {
                String path = FileUtil.getTempPath(outputFilename);
                ExcelUtil.RandomOrder(file.getInputStream(), new FileOutputStream(path));
                fileNames.add(outputFilename);
            } catch (IOException ie) {
                fileNames.add("Exception");
                throw ie;
            } catch (RuntimeException e) {
                fileNames.add(e.getMessage());
            }
        }
        return fileNames;
    }
}
