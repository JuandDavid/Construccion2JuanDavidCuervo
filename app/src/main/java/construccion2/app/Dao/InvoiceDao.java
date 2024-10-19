package App.Dao;

import java.util.ArrayList;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import App.Dao.Repository.InvoiceRepository;
import App.Dao.Interfaces.InvoiceDaoInterface;
import App.Helper.Helper;

import App.Model.Partner;
import App.Model.Person;
import App.Model.Invoice;

import App.Dto.InvoiceDto;
import App.Dto.PartnerDto;
import App.Dto.PersonDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@LoginService

public class InvoiceDao implements InvoiceDaoInterface {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public double amountActiveInvoices(PersonDto personDto) throws Exception {
        Person person = Helper.parse( personDto );
        List<Invoice> invoiceList = (List<Invoice>) this.invoiceRepository.findByPersonId(person);
        double amount = 0;
        for (Invoice invoice : invoiceList) {
            if(invoice.getStatus().equals("Pendiente")){
                amount += invoice.getAmount();
            }
        }
        return amount;
    }

    @Override
    public double amountActiveInvoicesByPartner(PartnerDto partnerDto) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        List<Invoice> invoiceList = this.invoiceRepository.findByPartnerId( partner );
        double amount = 0;
        for (Invoice invoice : invoiceList) {
            if(invoice.getStatus().equals("Pendiente")){
                amount += invoice.getAmount();
            }
        }
        return amount;
    }

    @Override
    public double amountCancelInvoicesByPartner(PartnerDto partnerDto) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        List<Invoice> invoiceList = this.invoiceRepository.findByPartnerId( partner );
        double amount = 0;
        for (Invoice invoice : invoiceList) {
            if(invoice.getStatus().equals("Cancelada")){
                amount += invoice.getAmount();
            }
        }
        return amount;
    }

    @Override
    public double amountTotalInvoicesByPartner(PartnerDto partnerDto) throws Exception {
        Partner partner = Helper.parse(partnerDto);
        List<Invoice> invoiceList = this.invoiceRepository.findByPartnerId( partner );
        double amount = 0;
        for (Invoice invoice : invoiceList) {
            amount += invoice.getAmount();
        }
        return amount;
    }
    
    @Override
    public void createInvoice( InvoiceDto invoiceDto ) throws Exception {
        Invoice invoice = Helper.parse( invoiceDto );
        invoice.setCreationDate( Timestamp.valueOf( LocalDateTime.now() ) );
        this.invoiceRepository.save( invoice );
        invoiceDto.setId( invoice.getId() );
        invoiceDto.setCreationDate( invoice.getCreationDate() );
    }

    @Override
    public void updateInvoiceAmount( InvoiceDto invoiceDto ) throws Exception {
        Invoice invoice = Helper.parse( invoiceDto );
        this.invoiceRepository.save( invoice );
    }

    @Override
    public void deleteInvoice( InvoiceDto invoiceDto ) throws Exception {
        Invoice invoice = Helper.parse( invoiceDto );
        this.invoiceRepository.deleteById( invoice.getId() );
    }

    @Override
    public void cancelInvoice( InvoiceDto invoiceDto ) throws Exception {
        Invoice invoice = Helper.parse( invoiceDto );
        invoice.setStatus("CANCELADA");
        this.invoiceRepository.save( invoice );
    }
    
    @Override
    public ArrayList<InvoiceDto> listClubInvoices() throws Exception {
        ArrayList<InvoiceDto> listInvoices = new ArrayList<InvoiceDto>();
        List<Invoice> invoiceList = this.invoiceRepository.findAll();
        for (Invoice invoice : invoiceList) {
            listInvoices.add(Helper.parse(invoice));
        }
        return listInvoices;
    }

    @Override
    public ArrayList<InvoiceDto> listPartnerInvoices(PartnerDto partnerDto) throws Exception {
        ArrayList<InvoiceDto> listInvoices = new ArrayList<InvoiceDto>();
        Partner partner = Helper.parse(partnerDto);
        List<Invoice> invoiceList = this.invoiceRepository.findByPartnerId( partner );
        for (Invoice invoice : invoiceList) {
            listInvoices.add(Helper.parse(invoice));
        }
        return listInvoices;
    }

    @Override
    public ArrayList<InvoiceDto> listPersonInvoices(PersonDto personDto) throws Exception {
        ArrayList<InvoiceDto> listInvoices = new ArrayList<InvoiceDto>();
        Person person = Helper.parse(personDto);
        List<Invoice> invoiceList = this.invoiceRepository.findByPersonId( person );
        for (Invoice invoice : invoiceList) {
            listInvoices.add(Helper.parse(invoice));
        }
        return listInvoices;
    }
}