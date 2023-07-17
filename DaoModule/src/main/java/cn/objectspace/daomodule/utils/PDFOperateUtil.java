package cn.objectspace.daomodule.utils;

import cn.objectspace.daomodule.entity.PDFInfo;
import cn.objectspace.daomodule.entity.UserInfo;
import cn.objectspace.daomodule.mapper.PDFInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PDFOperateUtil {
    @Resource
    PDFInfoMapper pdfInfoMapper;

    /**
     * 通过PDF文件的id获取路径
     * @param pdfId PDF文件id
     * @return 文件路径
     */
    public String getPDFPathById(int pdfId){
        QueryWrapper<PDFInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", pdfId);
        PDFInfo pdfInfo = pdfInfoMapper.selectOne(queryWrapper);
        if(pdfInfo != null)
            return pdfInfo.getPdfPath();
        else
            return "";
    }

    /**
     * 通过PDF文件的id获取名称
     * @param pdfId PDF文件id
     * @return 文件名称
     */
    public String getPDFNameById(int pdfId){
        QueryWrapper<PDFInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", pdfId);
        PDFInfo pdfInfo = pdfInfoMapper.selectOne(queryWrapper);
        if(pdfInfo != null)
            return pdfInfo.getPdfName();
        else
            return "";
    }
}
