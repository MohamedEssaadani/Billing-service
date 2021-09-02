package com.essaadani.billingservice;

import com.essaadani.billingservice.dto.InvoiceRequestDTO;
import com.essaadani.billingservice.services.InvoiceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(InvoiceService invoiceService){
        return args -> {
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(889800), "customer1"));
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(776827), "customer1"));
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(900000), "customer2"));
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(109288), "customer2"));
        };
    }
}
