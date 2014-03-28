package com.eadmarket.pangu.util;

import lombok.Setter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Created by liu on 3/28/14.
 */
public final class FileUploadUtil {

    private final static String SEED = "CDOPQRSTEFGXABYZHIJKLMNUVW";

    private String fileUploadPath;

    @Setter private int maxRetryTimes = 3;

    public void setFileUploadPath(String fileUploadPath) {
        if (StringUtils.isBlank(fileUploadPath)) {
            throw new IllegalArgumentException("fileUploadPath can't be blank");
        }
        if (!fileUploadPath.endsWith("/")) {
            fileUploadPath += "/";
        }
        this.fileUploadPath = fileUploadPath;
    }

    /**
     * 上传文件并且返回文件路径
     */
    public String uploadFile(FileItem fileItem) throws IOException {
        int retryTimes = 0;

        while (retryTimes++ < maxRetryTimes) {
            String fileItemName = fileItem.getName();
            String fileType = resolveFileType(fileItemName);
            String path = generateFileName(fileType);

            String absolutePath = fileUploadPath + path;

            File file = new File(absolutePath);
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                try {
                    fileItem.write(file);
                } catch (Exception ex) {
                    if (newFile) {
                        file.delete();
                    }
                    throw new IOException("failed to write file to " + absolutePath, ex);
                }
            } else {
                //文件已经存在的情况下，进行重试
                continue;
            }
            return path;
        }
        throw new IOException("failed to upload file");
    }

    private static String resolveFileType(String fileName) {
        int beginIndex = fileName.lastIndexOf('.');
        if (beginIndex < 0) {
            return "";
        }
        return fileName.substring(beginIndex);
    }

    private static String generateFileName(String fileType) {
        String timestamp = DateFormatUtils.format(new Date(), "yyMMddHHmm_ss_");
        return timestamp + randomStr() + fileType;
    }

    private static String randomStr() {
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i ++) {
            int index = new Random().nextInt(SEED.length());
            sb.append(SEED.charAt(index));
        }
        return sb.toString();
    }

}
