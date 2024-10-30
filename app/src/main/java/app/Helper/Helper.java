package app.Helper;

import app.Model.Person;
import app.Model.User;
import app.Model.Partner;
import app.Model.Guest;
import app.Model.Invoice;
import app.Model.InvoiceDetail;

import app.Dto.PersonDto;
import app.Dto.UserDto;
import app.Dto.PartnerDto;
import app.Dto.GuestDto;
import app.Dto.InvoiceDto;
import app.Dto.InvoiceDetailDto;

public abstract class Helper {
    public static PersonDto parse(Person person) {
        if ( person == null ){
            return null;
        }
        PersonDto personDto = new PersonDto();
        personDto.setId( person.getId() );
        personDto.setDocument( person.getDocument() );
        personDto.setName( person.getName() );
        personDto.setCellphone( person.getCellphone());
        return personDto;
    }

    public static Person parse(PersonDto personDto) {
        if ( personDto == null ){
            return null;
        }
        Person person = new Person();
        person.setId( personDto.getId());
        person.setDocument(personDto.getDocument());
        person.setName( personDto.getName());
        person.setCellphone( personDto.getCellphone() );
        return person;
    }
    
    public static UserDto parse(User user) {
        if ( user == null ){
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId() );
        userDto.setPassword( user.getPassword() );
        userDto.setPersonnId( user.getPersonnId() );
        userDto.setRole( user.getRole());
        userDto.setUserName( user.getUserName() );
        return userDto;
    }

    public static User parse(UserDto userDto) {
        if ( userDto == null ){
            return null;
        }
        User user = new User();
        user.setId( userDto.getId() );
        user.setPassword(userDto.getPassword() );
        user.setPersonnId( userDto.getPersonnId() );
        user.setRole(userDto.getRole() );
        user.setUserName(userDto.getUserName() );
        return user;
    }
    
    public static Partner parse( PartnerDto partnerDto) {
        if ( partnerDto == null ){
            return null;
        }
        Partner partner = new Partner();
        partner.setId( partnerDto.getId() );
        partner.setUserId( partnerDto.getUserId() );
        partner.setType( partnerDto.getType() );
        partner.setAmount( partnerDto.getAmount() );
        partner.setCreationDate( partnerDto.getCreationDate() );
        return partner;
    }
    
    public static PartnerDto parse( Partner partner ){
        if ( partner == null ){
            return null;
        }
        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setId( partner.getId() );
        partnerDto.setUserId( partner.getUserId() );
        partnerDto.setType( partner.getType() );
        partnerDto.setAmount( partner.getAmount() );
        partnerDto.setCreationDate( partner.getCreationDate() );
        return partnerDto;
    }

    public static Guest parse( GuestDto GuestDto ) {
        if ( GuestDto == null ){
            return null;
        }
        Guest guest = new Guest();
        guest.setId( GuestDto.getId() );
        guest.setUserId( GuestDto.getUserId() );
        guest.setPartnerId( GuestDto.getPartnerId() );
        guest.setStatus( GuestDto.getStatus() );
        return guest;
    }
    
    public static GuestDto parse( Guest guest ){
        if ( guest == null ){
            return null;
        }
        GuestDto guestDto = new GuestDto();
        guestDto.setId( guest.getId() );
        guestDto.setUserId( guest.getUserId() );
        guestDto.setPartnerId( guest.getPartnerId() );
        guestDto.setStatus( guest.getStatus() );
        return guestDto;
    }

    public static Invoice parse( InvoiceDto invoiceDto ) {
        if ( invoiceDto == null ){
            return null;
        }
        Invoice invoice = new Invoice();
        invoice.setId( invoiceDto.getId() );
        invoice.setPersonId( invoiceDto.getPersonId() );
        invoice.setPartnerId( invoiceDto.getPartnerId() );
        invoice.setCreationDate( invoiceDto.getCreationDate() );
        invoice.setAmount( invoiceDto.getAmount() );
        invoice.setStatus( invoiceDto.getStatus() );
        return invoice;
    }
    
    public static InvoiceDto parse( Invoice invoice ){
        if ( invoice == null ){
            return null;
        }
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setId( invoice.getId() );
        invoiceDto.setPersonId( invoice.getPersonId() );
        invoiceDto.setPartnerId( invoice.getPartnerId() );
        invoiceDto.setCreationDate( invoice.getCreationDate() );
        invoiceDto.setAmount( invoice.getAmount() );
        invoiceDto.setStatus( invoice.getStatus() );
        return invoiceDto;
    }

    public static InvoiceDetail parse( InvoiceDetailDto invoiceDetailDto ) {
        if ( invoiceDetailDto == null ){
            return null;
        }
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setId( invoiceDetailDto.getId() );
        invoiceDetail.setInvoiceId( invoiceDetailDto.getInvoiceId() );
        invoiceDetail.setItem( invoiceDetailDto.getItem() );
        invoiceDetail.setDescription( invoiceDetailDto.getDescription() );
        invoiceDetail.setAmount( invoiceDetailDto.getAmount() );
        return invoiceDetail;
    }
    
    public static InvoiceDetailDto parse( InvoiceDetail invoiceDetail ){
        if ( invoiceDetail == null ){
            return null;
        }
        InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();
        invoiceDetailDto.setId( invoiceDetail.getId() );
        invoiceDetailDto.setInvoiceId( invoiceDetail.getInvoiceId() );
        invoiceDetailDto.setItem( invoiceDetail.getItem() );
        invoiceDetailDto.setDescription( invoiceDetail.getDescription() );
        invoiceDetailDto.setAmount( invoiceDetail.getAmount() );
        return invoiceDetailDto;
    }
}