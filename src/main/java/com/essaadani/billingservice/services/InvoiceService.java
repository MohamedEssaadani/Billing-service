package com.essaadani.billingservice.services;

import com.essaadani.billingservice.dto.InvoiceRequestDTO;
import com.essaadani.billingservice.dto.InvoiceResponseDTO;

import java.util.List;

public interface InvoiceService {
    InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO getInvoice(String invoiceID);
    List<InvoiceResponseDTO> getInvoicesByCustomer(String customerID);
    List<InvoiceResponseDTO> getAllInvoices();
}
