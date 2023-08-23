package cn.objectspace.servicemodule.service.impl;

import cn.objectspace.daomodule.utils.PDFOperateUtil;
import cn.objectspace.servicemodule.service.PDFAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Bill
 */
@Service("pdfAccessService")
@Slf4j
public class PDFAccessServiceImpl implements PDFAccessService {
    @Resource
    PDFOperateUtil pdfOperateUtil;

    @Override
    public ResponseEntity<byte[]> getPdf(int pdfId) throws IOException{
        String pdfPath = pdfOperateUtil.getPdfPathById(pdfId);
        String pdfName = pdfOperateUtil.getPdfNameById(pdfId);
        if("".equals(pdfPath) || "".equals(pdfName)) {
            return ResponseEntity.ok()
                    .body("未找到该文件".getBytes());
        }
        org.springframework.core.io.Resource resource = new ClassPathResource(pdfPath);
        File file = resource.getFile();

        // 将文件读取到字节数组中
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        int contentLength = fileInputStream.read(bytes);
        fileInputStream.close();

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", pdfName);

        // 返回响应实体
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(contentLength)
                .body(bytes);
    }
}
