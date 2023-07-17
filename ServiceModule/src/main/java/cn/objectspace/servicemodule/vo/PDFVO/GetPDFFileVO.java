package cn.objectspace.servicemodule.vo.PDFVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetPDFFileVO {
    @ApiModelProperty(value = "pdf二进制流")
    byte[] pdf;

    @ApiModelProperty(value = "二进制流长度")
    long contentLength;
}
