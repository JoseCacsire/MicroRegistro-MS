package com.codigo.msregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

//con estas dos anotaciones podemos consumir la api rest reniec o otras apis
@EnableFeignClients("com.codigo.msregister")//activando los clientes feign(Tus interfaces q tengan anotacion @FeignClient)
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@SpringBootApplication
public class MsRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRegisterApplication.class, args);
	}

}
