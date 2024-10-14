package App.Helper;

import App.Model.Person;
import App.Model.User;
import App.Model.Partner;
import App.Model.Guest;
import App.Model.Invoice;
import App.Model.InvoiceDetail;

import App.Dto.PersonDto;
import App.Dto.UserDto;
import App.Dto.PartnerDto;
import App.Dto.GuestDto;
import App.Dto.InvoiceDto;
import App.Dto.InvoiceDetailDto;

public abstract class Helper {
    public static PersonDto parse(Person person){
        PersonDto personDto = new PersonDto();
        personDto.setId( person.getId() );
        personDto.setDocument( person.getDocument() );
        personDto.setName( person.getName() );
        personDto.setCellphone( person.getCellphone());
        return personDto;
    }
    
    public static UserDto parse(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId() );
        userDto.setPassword( user.getPassword() );
        userDto.setPersonnId( user.getPersonnId() );
        userDto.setRole( user.getRole());
        userDto.setUserName( user.getUserName() );
        return userDto;
    }

    public static Person parse(PersonDto personDto){
        Person person = new Person();
        person.setId( personDto.getId());
        person.setDocument(personDto.getDocument());
        person.setName( personDto.getName());
        person.setCellphone( personDto.getCellphone() );
        return person;
    }

    public static User parse(UserDto userDto){
        User user = new User();
        user.setId( userDto.getId() );
        user.setPassword(userDto.getPassword() );
        user.setPersonId( userDto.getPersonId() );
        user.setRole(userDto.getRole() );
        user.setUserName(userDto.getUserName() );
        return user;
    }

    public static PartnerDto parse( Partner partner ){
        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setId( partner.getId() );
        partnerDto.setUserId( partner.getUserId() );
        partnerDto.setType( partner.getType() );
        partnerDto.setAmount( partner.getAmount() );
        partnerDto.setCreationDate( partner.getCreationDate() );
        return partnerDto;
    }
    
    public static Partner parse( PartnerDto partnerDto){
        Partner partner = new Partner();
        partner.setId( partnerDto.getId() );
        partner.setUserId( partnerDto.getUserId() );
        partner.setType( partnerDto.getType() );
        partner.setAmount( partnerDto.getAmount() );
        partner.setCreationDate( partnerDto.getCreationDate() );
        return partner;
    }
    
    public static Guest parse( GuestDto GuestDto ){
        Guest guest = new Guest();
        guest.setId( GuestDto.getId() );
        guest.setUserId( GuestDto.getUserId() );
        guest.setPartnerId( GuestDto.getPartnerId() );
        guest.setStatus( GuestDto.getStatus() );
        return guest;
    }
    
    public static GuestDto parse( Guest guest ){
        GuestDto guestDto = new GuestDto();
        guestDto.setId( guest.getId() );
        guestDto.setUserId( guest.getUserId() );
        guestDto.setPartnerId( guest.getPartnerId() );
        guestDto.setStatus( guest.getStatus() );
        return guestDto;
    }

    public static Invoice parse( InvoiceDtoInterface invoiceDto ){
        Invoice invoice = new Invoice();
        invoice.setId( invoiceDto.getId() );
        invoice.setPersonId( invoiceDto.getPersonId() );
        invoice.setPartnerId( invoiceDto.getPartnerId() );
        invoice.setCreationDate( invoiceDto.getCreationDate() );
        invoice.setAmount( invoiceDto.getAmount() );
        invoice.setStatus( invoiceDto.getStatus() );
        return invoice;
    }

    public static InvoiceDetailDto parse( InvoiceDetailDao invoiceDetail ){
        InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();
        invoiceDetailDto.setId( invoiceDetail.getId() );
        invoiceDetailDto.setInvoiceId( invoiceDetail.getInvoiceId() );
        invoiceDetailDto.setItem( invoiceDetail.getItem() );
        invoiceDetailDto.setDescription( invoiceDetail.getDescription() );
        invoiceDetailDto.setAmount( invoiceDetail.getAmount() );
        return invoiceDetailDto;
    }
    
    public static InvoiceDtoInterface parse( Invoice invoice ){
        InvoiceDtoInterface invoiceDto = new InvoiceDtoInterface();
        invoiceDto.setId( invoice.getId() );
        invoiceDto.setPersonId( invoice.getPersonId() );
        invoiceDto.setPartnerId( invoice.getPartnerId() );
        invoiceDto.setCreationDate( invoice.getCreationDate() );
        invoiceDto.setAmount( invoice.getAmount() );
        invoiceDto.setStatus( invoice.getStatus() );
        return invoiceDto;
    }

    public static InvoiceDetailDao parse( InvoiceDetailDto invoiceDetailDto ) {
        InvoiceDetailDao invoiceDetail = new InvoiceDetailDao();
        invoiceDetail.setId( invoiceDetailDto.getId() );
        invoiceDetail.setInvoiceId( invoiceDetailDto.getInvoiceId() );
        invoiceDetail.setItem( invoiceDetailDto.getItem() );
        invoiceDetail.setDescription( invoiceDetailDto.getDescription() );
        invoiceDetail.setAmount( invoiceDetailDto.getAmount() );
        return invoiceDetail;
    }
}