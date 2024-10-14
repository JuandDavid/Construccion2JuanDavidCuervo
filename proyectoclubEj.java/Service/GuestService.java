package App.Service;

import App.Service.Intefaces.GuestServiceInterface;

import App.Dao.PersonDao;
import App.Dao.UserDao;
import App.Dao.PartnerDao;
import App.Dao.GuestDao;
import App.Dao.InvoiceDao;
import App.Dao.InvoiceDetailDao;

import App.Dto.PersonDto;
import App.Dto.UserDto;
import App.Dto.GuestDto;
import App.Dto.InvoiceDto;
import App.Dto.PartnerDto;
import App.Helper.Helper;
import java.util.ArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@Setter

@Service
    public class GuestService implements GuestServiceInterface {
    @Autowired
    private final UserService userService = new UserService();
    @Autowired
    private final PersonDao personDao = new PersonDao();
    @Autowired
    private final UserDao userDao = new UserDao();
    @Autowired
    private final PartnerDao partnerDao = new PartnerDao();
    @Autowired
    private final GuestDao guestDao = new GuestDao();
    @Autowired
    private final InvoiceDao invoiceDao = new InvoiceDao();
    @Autowired
    private final InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDao();

    @Override
    public void createGuest( ) throws Exception {
        UserDto userLocateDto = this.userService.createGuestUser();

        if ( userLocateDto == null ) {
            throw new Exception("Ningun usuario encontrado");            
        }

        PersonDto personDto = new PersonDto();
        personDto.getPersonDocumentDto( "Ingrese documento del socio que invita" );
        personDto = this.personDao.findByDocument(personDto);
        if ( personDto == null ) {
            throw new Exception("Ninguna persona coincide con el numero de Id");            
        }
        
        UserDto userDtoInvite = this.userDao.findByPersonId( personDto );
        PartnerDto partnerDto = this.partnerDao.findByUserId( userDtoInvite );
        if ( partnerDto == null ) {
            throw new Exception( personDto.getName() + " no es socio del club");            
        }
                
        GuestDto guestDto = new GuestDto();
        guestDto.setUserId( InvoiceDetailDao.parse( userLocateDto ) );
        guestDto.setPartnerId( InvoiceDetailDao.parse( partnerDto ) );
        guestDto.setStatus( "ACTIVO" );
        
        this.guestDao.createGuest( guestDto );
    }
    
    @Override
    public void createGuest( UserDto userDto ) throws Exception {
        UserDto userLocateDto = this.userService.createGuestUser();

        if ( userLocateDto == null ) {
            throw new Exception("Ningun usuario encontrado");
        }
        PartnerDto partnerDto = this.partnerDao.findByUserId( userDto );
        
        GuestDto guestDto = new GuestDto();
        guestDto.setUserId( InvoiceDetailDao.parse( userLocateDto ) );
        guestDto.setPartnerId( InvoiceDetailDao.parse( partnerDto ) );
        guestDto.setStatus( "ACTIVO" );
        
        this.guestDao.createGuest( guestDto );
    }
    
    @Override
    public void deleteGuest( ) throws Exception {
        PersonDto personDtoLocale = new PersonDto();
        personDtoLocale.getPersonDocumentDto();
        personDtoLocale = this.personDao.findByDocument( personDtoLocale );
        
        if ( personDtoLocale == null ){
            throw new Exception("No existe la persona");
        }
        
        double amountActiveInvoices = this.invoiceDao.amountActiveInvoices( personDtoLocale );
        if ( amountActiveInvoices > 0 ){
            throw new Exception( personDtoLocale.getName() + " tiene facturas pendientes de pago");
        }
        
        UserDto userDtoLocate = this.userDao.findByPersonId( personDtoLocale );
        if ( userDtoLocate == null ) {
            throw new Exception("Ningun usuario coincide con el numero de Id");            
        }

        GuestDto guestDto = this.guestDao.findByUserId( userDtoLocate );
        
        if ( guestDto == null ){
            throw new Exception("No existe el invitado");                            
        }
        
        this.guestDao.deleteGuest( guestDto );
    }    

    @Override
    public void changeGuestToPartner( UserDto userDto ) throws Exception {
        PersonDto personDtoLocale = this.personDao.findByUserId( userDto );
        
        if ( personDtoLocale == null ){
            throw new Exception("No existe la persona");
        }
        
        double amountActiveInvoices = this.invoiceDao.amountActiveInvoices( personDtoLocale );
        if ( amountActiveInvoices > 0 ){
            throw new Exception( personDtoLocale.getName() + " tiene facturas pendientes de pago");
        }

        GuestDto guestDto = this.guestDao.findByUserId( userDto );
        if ( guestDto == null ){
            throw new Exception( personDtoLocale.getName() + " no es un invitado");            
        }

        PartnerDto partnerDto = this.partnerDao.findByUserId( userDto );
        if ( partnerDto != null ){
            throw new Exception( personDtoLocale.getName() + " ya es socio del club");
        }
        
        partnerDto = new PartnerDto();
        partnerDto.setUserId( InvoiceDetailDao.parse( userDto ) );
        partnerDto.getPartnerTypeDto();
        if ( partnerDto.getType().equals( "VIP" ) ){
            long numberVIP = this.partnerDao.numberPartnersVIP();
            if ( numberVIP >= 5 ){
                throw new Exception("Cupo de socios VIP lleno");                
            }
        }
        partnerDto.getPartnerAmountDto();
        
        userDto.setRole("SOCIO");
        
        this.guestDao.deleteGuest( guestDto );
        this.partnerDao.createPartner( partnerDto );
        this.userDao.updateUser( userDto );
    }
}