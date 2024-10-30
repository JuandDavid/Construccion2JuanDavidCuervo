package app.Service.Interface;

import app.Dto.PartnerDto;
import app.Dto.GuestDto;

public interface InvoiceServiceInterface {
    public void createInvoice( ) throws Exception;
    public void createPartnerInvoice( PartnerDto partnerDto ) throws Exception;
    public void createGuestInvoice( GuestDto guestDto ) throws Exception;
    
    public void historyInvoice( ) throws Exception;
    public void historyPartnerInvoice( PartnerDto partnerDto ) throws Exception;
}