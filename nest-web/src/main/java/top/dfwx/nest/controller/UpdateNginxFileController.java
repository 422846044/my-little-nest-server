package top.dfwx.nest.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import top.dfwx.common.domain.Result;
import top.dfwx.common.exchandler.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

/**
 * @author atulan_zyj
 * @date 2025/7/24
 */
@RestController
@RequestMapping("/nginx/update/file")
@Slf4j
public class UpdateNginxFileController {


    @PutMapping("/replaceAll/{type}")
    public Result<Object> replaceAll(@PathVariable("type") Integer type,
                                     @RequestParam("file") MultipartFile multipartFile){
        String contentType = multipartFile.getContentType();
        if(!"application/x-zip-compressed".equals(contentType)){
            throw new GlobalException("不支持当前文件格式");
        }
        String fileName = "frontend.zip", originalDirName = "华雅生活", targetDirName = "axure";
        if(type == 1){
            fileName = "backend.zip";
            originalDirName = "华雅后台";
            targetDirName = "hyht";
        }

        String uploadFilePath = "/usr/local/application/tmpFile/" + fileName;

        File dir = new File("/usr/local/application/tmpFile/frontend");
        deleteDir(dir);
        dir.mkdir();
        File file1 = new File("/usr/local/nginx/html/axure");
        deleteDir(file1);
        file1.mkdir();

        //将上传的文件保存到本�?
        try {
            File file = new File(uploadFilePath);
            InputStream inputStream = multipartFile.getInputStream();
            IoUtil.copy(inputStream, Files.newOutputStream(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        extracted(Arrays.asList("unzip", uploadFilePath, "-d", "/usr/local/application/tmpFile/frontend"));

        FileUtil.copyContent(new File("/usr/local/application/tmpFile/frontend/" + originalDirName), new File("/usr/local/nginx/html/" + targetDirName), true);

        return Result.ok();
    }

    private static void extracted(List<String> cmd) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                int i = process.waitFor();
                if(i == 0){
                    log.info("命令"+ cmd+"执行成功");
                }else{
                    log.info("命令"+ cmd+"执行失败");
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


    // 使用递归方式删除目录及其中的文件
    public static Boolean deleteDir(File dir) {
        boolean res = true;
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDir(file);
                } else {
                    res = res && file.delete();
                }
            }
            res = res && dir.delete();
        }
        return res;
    }



}
