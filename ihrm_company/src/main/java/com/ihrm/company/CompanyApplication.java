package com.ihrm.company;

import com.ihrm.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

//配置Springboot的包扫描
@SpringBootApplication(scanBasePackages = "com.ihrm" )
@EntityScan(basePackages = "com.ihrm.domain.company")
public class CompanyApplication {
    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(CompanyApplication.class,args);
    }


    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
