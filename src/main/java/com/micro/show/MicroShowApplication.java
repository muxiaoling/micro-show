package com.micro.show;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.micro.show.mapper")
@SpringBootApplication
public class MicroShowApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroShowApplication.class, args);
	}

}
