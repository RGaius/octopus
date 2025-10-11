package org.gaius.octopus.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gaius
 */
@SpringBootApplication
@MapperScan("org.gaius.octopus.core.mapper")
public class OctopusApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OctopusApplication.class, args);
    }
    
}
