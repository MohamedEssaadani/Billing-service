package com.essaadani.billingservice.web;

import com.essaadani.billingservice.dto.InvoiceRequestDTO;
import com.essaadani.billingservice.dto.InvoiceResponseDTO;
import com.essaadani.billingservice.services.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceRestController {
    private InvoiceService invoiceService;


    public InvoiceRestController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @GetMapping("/invoices")
    public List<InvoiceResponseDTO> getAllInvoices(){
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/invoices/{id}")
    public InvoiceResponseDTO getInvoice(@PathVariable(name = "id") String id){
        return invoiceService.getInvoice(id);
    }


    @GetMapping("/invoices/byCustomer/{customerID}")
    public List<InvoiceResponseDTO> getInvoicesByCustomer(@PathVariable(name = "customerID") String customerID){
        return invoiceService.getInvoicesByCustomer(customerID);
    }

    @PostMapping("/invoices")
    public InvoiceResponseDTO saveInvoice(@RequestBody InvoiceRequestDTO invoiceRequestDTO){
        return invoiceService.save(invoiceRequestDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
