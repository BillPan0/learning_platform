package cn.objectspace.webssh.service.impl;

import cn.objectspace.commonmodule.utils.TimeFormat;
import cn.objectspace.dubbo.MicroService;
import cn.objectspace.dubbo.dubbointerface.dto.TerminalInfoDTO;
import cn.objectspace.webssh.entity.AllocatedTerminalInfo;
import cn.objectspace.webssh.entity.ImageInfo;
import cn.objectspace.webssh.entity.PDFToImageMap;
import cn.objectspace.webssh.mapper.AllocatedTerminalMapper;
import cn.objectspace.webssh.mapper.ImageMapper;
import cn.objectspace.webssh.mapper.PDFToImageMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.UUID;

/**
 * @author Bill
 */
@DubboService
public class MicroServiceImpl implements MicroService {
    @Resource
    ImageMapper imageMapper;
    @Resource
    PDFToImageMapper pdfToImageMapper;
    @Resource
    AllocatedTerminalMapper allocatedTerminalMapper;

    /**
     * 根据pdfId分配新的终端
     * @param pdfId pdf的id
     * @return 分配的终端信息
     */
    @Override
    public TerminalInfoDTO requestTerminalInfo(int pdfId) throws Exception {
        QueryWrapper<PDFToImageMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pdf_id", pdfId);
        PDFToImageMap pdfToImageMap = pdfToImageMapper.selectOne(queryWrapper);
        int imageId = pdfToImageMap.getImageId();

        ImageInfo imageInfo = imageMapper.selectById(imageId);
        //随机生成新终端ip（容器名）
        String host = UUID.randomUUID().toString().replace("-","");
        String imageName = imageInfo.getImageName() + ":" + imageInfo.getTag();
        String port = createTerminal(imageName, host);
        TerminalInfoDTO terminalInfoDTO = new TerminalInfoDTO();
        terminalInfoDTO.setHost(host);
        terminalInfoDTO.setPort(port);
        terminalInfoDTO.setUser(imageInfo.getUsername());
        terminalInfoDTO.setPassword(imageInfo.getPassword());
        terminalInfoDTO.setImageName(imageName);
        //插入分配记录，插入不成功的情况
        if(saveAllocatedTerminal(host, terminalInfoDTO) == 0) {
            throw new Exception();
        } else {
            return terminalInfoDTO;
        }
    }

    /**
     * 执行终端命令创建新的终端
     * @param imageName 镜像名称
     * @param host 终端ip
     * @return 终端映射到宿主机的端口号
     */
    public String createTerminal(String imageName, String host) {
        //生成随机端口号
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int[] arr = random.ints(60, 20000, 60000).toArray();
        int randomPort = arr[TimeFormat.getLocalDateTime().getSecond()];
        //检查同名docker
        String checkExistCmd = "docker ps | grep " + host;
        //创建docker文件夹并复制文件
        //所有操作在运行文件夹下进行
        String imageFolder = imageName.replace(":", "_");
        String newFolderCmd = "mkdir " + host +
                "&& cp " + imageFolder + "/* " + host;
        //修改docker compose文件
        //1.替换容器名（原容器名为镜像文件夹名称）
        //2.替换22端口映射（原端口映射至18222）
        String modifyDockerComposeFileCmd = "sed -i \"s/" + imageFolder + "/" + host + "/g\" " + host + "/docker-compose.yml" +
                "&& sed -i 's/- \"[1-9]\\+:22\"/- \"" + randomPort + ":22\"/g' " + host + "/docker-compose.yml";
        String startDockerComposeCmd = "cd " + host + " && docker compose build && docker compose up -d";
        Process process;
        try {
            //检查是否已经存在该名称的容器
            ProcessBuilder checkProcessBuilder = new ProcessBuilder();
            checkProcessBuilder.command("bash", "-c", checkExistCmd);
            process = checkProcessBuilder.start();
            process.waitFor();
            BufferedReader checkReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String checkLineResult = checkReader.readLine();
            if(checkLineResult == null) {
                //创建docker文件夹
                checkProcessBuilder.command("bash", "-c", newFolderCmd);
                process = checkProcessBuilder.start();
                process.waitFor();
                //修改新的容器docker-compose文件
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("bash", "-c", modifyDockerComposeFileCmd);
                process = processBuilder.start();
                process.waitFor();
                //开启刚刚创建的容器
                processBuilder.command("bash", "-c", startDockerComposeCmd);
                process = processBuilder.start();
                process.waitFor();
                return String.valueOf(randomPort);
            }else{
                String portCmd = "docker ps | grep -w " + host + " " +
                        "| grep -oE 0.0.0.0:[0-9]+ | grep -oE [0-9][0-9]+";
                //查找已存在的docker的ssh端口号
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("bash", "-c", portCmd);
                process = processBuilder.start();
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                return reader.readLine();
                //TODO 将端口写入数据库
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public int saveAllocatedTerminal(String host, TerminalInfoDTO terminalInfoDTO){
        AllocatedTerminalInfo allocatedTerminalInfo = new AllocatedTerminalInfo();
        allocatedTerminalInfo.setHost(host);
        allocatedTerminalInfo.setImageName(terminalInfoDTO.getImageName().split(":")[0]);
        allocatedTerminalInfo.setTag(terminalInfoDTO.getImageName().split(":")[1]);
        allocatedTerminalInfo.setPort(terminalInfoDTO.getPort());
        allocatedTerminalInfo.setUsername(terminalInfoDTO.getUser());
        allocatedTerminalInfo.setPassword(terminalInfoDTO.getPassword());
        return allocatedTerminalMapper.insert(allocatedTerminalInfo);
    }
}
