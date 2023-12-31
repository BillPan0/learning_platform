package cn.objectspace.webmodule.controller;

import cn.objectspace.servicemodule.service.impl.PDFAccessServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

import static org.hibernate.type.descriptor.java.JdbcDateTypeDescriptor.DATE_FORMAT;

/**
 * @author Bill
 */
@RestController
@RequestMapping("/pdf")
@Api(tags = "PDF文件信息管理")
public class PDFAccessController {
    @Resource
    PDFAccessServiceImpl pdfAccessService;

    @GetMapping
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("获取PDF文件")
    public ResponseEntity<byte[]> getPdfFile(int pdfId) throws IOException {
        return pdfAccessService.getPdf(pdfId);
    }
    /*
//
//    @GetMapping("/VO")
//    public ResponseResult<GetPDFFileVO> getPDFFileVO() throws IOException {
//        return new  ResponseResult<>(pdfAccessService.getPdfVO());
//    }

     */
}
