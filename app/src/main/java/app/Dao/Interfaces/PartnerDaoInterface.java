package app.Dao.Interfaces;

import app.Dto.UserDto;
import app.Dto.PartnerDto;
import app.Dto.GuestDto;
import app.Dto.InvoiceDto;
import java.util.ArrayList;

public interface PartnerDaoInterface {
    public boolean existsByUserId( UserDto userDto ) throws Exception;
    public void createPartner( PartnerDto partnerDto ) throws Exception;
    public void updatePartner( PartnerDto partnerDto ) throws Exception;
    public void deletePartner( PartnerDto partnerDto ) throws Exception;
    public PartnerDto findByUserId( UserDto userDto ) throws Exception;
    public PartnerDto findByPartnerId( InvoiceDto invoiceDto ) throws Exception;
    public PartnerDto findByGuestPartnerId( GuestDto guestDto ) throws Exception;
    public long numberPartnersVIP( ) throws Exception;
    public long numberPartnersRequestVIP( ) throws Exception;
    public ArrayList<PartnerDto> listPartnerRequestVIP( ) throws Exception ;
    
}