package app.Service;

import app.Service.Interface.GuestServiceInterface;

import app.Dao.PersonDao;
import app.Dao.UserDao;
import app.Dao.PartnerDao;
import app.Dao.GuestDao;
import app.Dao.InvoiceDao;
import app.Dao.InvoiceDetailDao;

import app.Dto.PersonDto;
import app.Dto.UserDto;
import app.Dto.GuestDto;
import app.Dto.InvoiceDto;
import app.Dto.PartnerDto;
import app.Helper.Helper;
import java.util.ArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
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
            throw new Exception("No se encontró ningún usuario");            
        }

        PersonDto personDto = new PersonDto();
        personDto.getPersonDocumentDto( "Ingrese el documento del socio que invita" );
        personDto = this.personDao.findByDocument(personDto);
        if ( personDto == null ) {
            throw new Exception("No se encontró ningúna persona con el numero de identificación");            
        }
        
        UserDto userDtoInvite = this.userDao.findByPersonId( personDto );
        PartnerDto partnerDto = this.partnerDao.findByUserId( userDtoInvite );
        if ( partnerDto == null ) {
            throw new Exception( personDto.getName() + " no es socio del club");            
        }
                
        GuestDto guestDto = new GuestDto();
        guestDto.setUserId( Helper.parse( userLocateDto ) );
        guestDto.setPartnerId( Helper.parse( partnerDto ) );
        guestDto.setStatus( "ACTIVO" );
        
        this.guestDao.createGuest( guestDto );
    }
    
    @Override
    public void createGuest( UserDto userDto ) throws Exception {
        UserDto userLocateDto = this.userService.createGuestUser();

        if ( userLocateDto == null ) {
            throw new Exception("No se encontró ningún usuario");
        }
        PartnerDto partnerDto = this.partnerDao.findByUserId( userDto );
        
        GuestDto guestDto = new GuestDto();
        guestDto.setUserId( Helper.parse( userLocateDto ) );
        guestDto.setPartnerId( Helper.parse( partnerDto ) );
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
            throw new Exception("No se encontró ningún usuario con el número de identificación ingresado");            
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
            throw new Exception( personDtoLocale.getName() + " ya es SOCIO del club");
        }
        
        partnerDto = new PartnerDto();
        partnerDto.setUserId( Helper.parse( userDto ) );
        partnerDto.getPartnerTypeDto();
        if ( partnerDto.getType().equals( "VIP" ) ){
            long numberVIP = this.partnerDao.numberPartnersVIP();
            if ( numberVIP >= 5 ){
                throw new Exception("Cupo de socios VIP copado");                
            }
        }
        partnerDto.getPartnerAmountDto();
        
        userDto.setRole( "SOCIO" );
        
        this.guestDao.deleteGuest( guestDto );
        this.partnerDao.createPartner( partnerDto );
        this.userDao.updateUser( userDto );
    }
}