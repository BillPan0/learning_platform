package cn.objectspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Bill
 */
@SpringBootApplication(scanBasePackages = "cn.objectspace.**")
public class WebSSHClient {
    public static void main(String[] args) {
        SpringApplication.run(WebSSHClient.class);
    }
}
