package com.essaadani.billingservice.openfeign;

import com.essaadani.billingservice.models.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {
    @GetMapping("/api/customers/{id}")
    Customer getCustomer(@PathVariable(name="id") String id);

    @GetMapping("/api/customers")
    List<Customer> getAllCustomers();
}
