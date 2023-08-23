package cn.objectspace.servicemodule.service;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * @author Bill
 */
public interface PDFAccessService {
    ResponseEntity<byte[]> getPdf(int pdfId) throws IOException;
}
