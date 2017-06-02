package com.disid.proofs.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * = ClientApplication
 *
 * TODO Auto-generated class documentation
 *
 */
@SpringBootApplication
@EnableCaching
public class ClientApplication {

    /**
     * TODO Auto-generated method documentation
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}