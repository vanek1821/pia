package vanek.pia.service;

import java.util.List;

import javax.validation.Valid;

import vanek.pia.domain.Invoice;

public interface InvoiceManager {
	
	Invoice getById(Long id);

	void addInvoice(@Valid Invoice invoiceValues);

	List<Invoice> getInvoices();

	void updateInvoice(Invoice invoice, @Valid Invoice invoiceValues);

	boolean updateInvoiceCancelled(Long idLong);

}
