package top.autuan.templatebusinessapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages={"top.autuan.templatebusinessapi","top.autuan.templatebusinesssupport"})
@MapperScan("top.autuan.templatebusinesssupport.**.mapper")
public class TemplateBusinessApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateBusinessApiApplication.class, args);
    }

}
