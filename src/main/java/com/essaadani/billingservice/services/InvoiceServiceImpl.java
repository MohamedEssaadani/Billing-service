package com.essaadani.billingservice.services;

import com.essaadani.billingservice.dto.InvoiceRequestDTO;
import com.essaadani.billingservice.dto.InvoiceResponseDTO;
import com.essaadani.billingservice.entities.Invoice;
import com.essaadani.billingservice.exceptions.CustomerNotFoundException;
import com.essaadani.billingservice.mappers.InvoiceMapper;
import com.essaadani.billingservice.models.Customer;
import com.essaadani.billingservice.openfeign.CustomerRestClient;
import com.essaadani.billingservice.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;
    private CustomerRestClient customerRestClient;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, CustomerRestClient customerRestClient) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.customerRestClient = customerRestClient;
    }

    @Override
    public InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO) {
        /*
        * Verification de l'intégrité référentielle Customer -> Invoice
        * */
        Customer customer;
        try{
            customer = customerRestClient.getCustomer(invoiceRequestDTO.getCustomerID());

        }catch (Exception exception){
            throw new CustomerNotFoundException("Customer not found!");
        }
        Invoice invoice = invoiceMapper.toInvoice(invoiceRequestDTO);
        invoice.setId(UUID.randomUUID().toString());
        invoice.setDate(new Date());

        Invoice savedInvoice = invoiceRepository.save(invoice);
        savedInvoice.setCustomer(customer);

        return invoiceMapper.toInvoiceResponseDTO(savedInvoice);
    }

    @Override
    public InvoiceResponseDTO getInvoice(String invoiceID) {
        Invoice invoice = invoiceRepository.findById(invoiceID).get();
        Customer customer = customerRestClient.getCustomer(invoice.getCustomerID());
        invoice.setCustomer(customer);

        return invoiceMapper.toInvoiceResponseDTO(invoice);
    }

    @Override
    public List<InvoiceResponseDTO> getInvoicesByCustomer(String customerID) {
        List<Invoice> invoices = invoiceRepository.findByCustomerID(customerID);
        for (Invoice invoice : invoices){
            Customer customer = customerRestClient.getCustomer(invoice.getCustomerID());
            invoice.setCustomer(customer);
        }
        return invoices.stream()
                .map(invoice -> invoiceMapper.toInvoiceResponseDTO(invoice))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        for (Invoice invoice : invoices){
            Customer customer = customerRestClient.getCustomer(invoice.getCustomerID());
            invoice.setCustomer(customer);
        }

        return invoices
                .stream()
                .map(invoice -> invoiceMapper.toInvoiceResponseDTO(invoice))
                .collect(Collectors.toList());
    }
}
