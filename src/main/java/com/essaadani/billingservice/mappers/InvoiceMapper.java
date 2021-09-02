package com.essaadani.billingservice.mappers;

import com.essaadani.billingservice.dto.InvoiceRequestDTO;
import com.essaadani.billingservice.dto.InvoiceResponseDTO;
import com.essaadani.billingservice.entities.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoice toInvoice(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO toInvoiceResponseDTO(Invoice invoice);
}
