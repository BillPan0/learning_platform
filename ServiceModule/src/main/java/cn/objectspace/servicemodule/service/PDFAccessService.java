package cn.objectspace.servicemodule.service;

import cn.objectspace.servicemodule.vo.PDFVO.GetPDFFileVO;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface PDFAccessService {
    public ResponseEntity<byte[]> getPDF(int pdfId) throws IOException;
}
