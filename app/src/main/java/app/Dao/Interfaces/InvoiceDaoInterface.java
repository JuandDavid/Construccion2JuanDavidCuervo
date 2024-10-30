package app.Dao.Interfaces;

import app.Dto.PersonDto;
import app.Dto.PartnerDto;

import app.Dto.InvoiceDto;
import java.util.ArrayList;


public interface InvoiceDaoInterface {
    public void createInvoice( InvoiceDto invoiceDto ) throws Exception ;
    public void updateInvoiceAmount( InvoiceDto invoiceDto ) throws Exception ;
    public void cancelInvoice( InvoiceDto invoiceDto ) throws Exception ;
    public void deleteInvoice( InvoiceDto invoiceDto ) throws Exception ;
    public double amountActiveInvoices( PersonDto personDto ) throws Exception ;
    public double amountActiveInvoicesByPartner( PartnerDto partnerDto ) throws Exception ;
    public double amountCancelInvoicesByPartner( PartnerDto partnerDto ) throws Exception ;
    public double amountTotalInvoicesByPartner( PartnerDto partnerDto ) throws Exception ;

    public ArrayList<InvoiceDto> listClubInvoices( ) throws Exception ;
    public ArrayList<InvoiceDto> listPartnerInvoices( PartnerDto partnerDto ) throws Exception ;
    public ArrayList<InvoiceDto> listPersonInvoices( PersonDto personDto ) throws Exception ;
}