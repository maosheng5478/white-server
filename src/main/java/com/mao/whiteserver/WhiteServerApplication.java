package com.mao.whiteserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WhiteServerApplication {

    public static void main(String[] args) {

        try {
            SpringApplication.run(WhiteServerApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
        //SpringApplication.run(WhiteServerApplication.class, args);

    }

}
