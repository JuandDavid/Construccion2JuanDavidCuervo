package App.Service;

import App.Controllers.Utils;
import App.Dao.GuestDao;
import App.Dao.InvoiceDao;
import App.Dao.InvoiceDetailDao;
import App.Dao.PartnerDao;
import App.Dao.PersonDao;
import App.Dao.UserDao;
import App.Dto.GuestDto;
import App.Dto.InvoiceDetailDto;
import App.Dto.InvoiceDto;
import App.Dto.PartnerDto;
import App.Dto.PersonDto;
import App.Dto.UserDto;
import App.Helper.Helper;
import App.Service.Intefaces.InvoiceServiceInterface;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@Setter

@Service
    public class InvoiceService implements InvoiceServiceInterface {
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
    public void createInvoice() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.getPersonDocumentDto();
        
        if ( !this.personDao.existsByDocument( personDto ) ) {
            throw new Exception( "No existe ninguna persona con el documento: " + personDto.getDocument() );
        }
        
        personDto = this.personDao.findByDocument( personDto );
        
        UserDto userDto = this.userDao.findByPersonId( personDto ) ;
        
        if ( userDto == null ){
            throw new Exception( personDto.getName() + " no tiene usuario " );
        }
        PartnerDto partnerDto = new PartnerDto();
        
        if ( userDto.getRole().equals( "SOCIO" ) ){
            partnerDto = this.partnerDao.findByUserId( userDto );
            if ( partnerDto == null ){
                throw new Exception( personDto.getName() + " no es socio " );                
            }
        }
        else{
            GuestDto guestDto = this.guestDao.findByUserId( userDto );
            if ( guestDto == null ){
                throw new Exception( personDto.getName() + " no es invitado " );                
            }
            partnerDto = this.partnerDao.findByGuestPartnerId( guestDto );
        }
        this.CreateInvoice(personDto, partnerDto);
    }

    @Override
    public void createPartnerInvoice( PartnerDto partnerDto ) throws Exception {
        UserDto userDto = this.userDao.findByUserId( partnerDto );
        PersonDto personDto = this.personDao.findByUserId( userDto );

        this.CreateInvoice(personDto, partnerDto);
    }

    @Override
    public void createGuestInvoice( GuestDto guestDto ) throws Exception {
        UserDto userDto = this.userDao.findByGuestUserId( guestDto );
        PersonDto personDto = this.personDao.findByUserId( userDto );
        PartnerDto partnerDto = this.partnerDao.findByGuestPartnerId( guestDto );

        this.CreateInvoice(personDto, partnerDto);
    }

    @Override
    public void historyInvoice() throws Exception {
        ArrayList<InvoiceDto> listInvoices = this.invoiceDao.listClubInvoices();
        if ( listInvoices.isEmpty() ){
            throw new Exception( "No hay historial de facturación" );
        }
        List<InvoiceDto> filteredAndSorted = listInvoices.stream()
                .sorted(Comparator.comparing(InvoiceDto::getCreationDate)) 
                .collect(Collectors.toList());

        for ( InvoiceDto invoices : filteredAndSorted ){
            PersonDto personDto = this.personDao.findByPersonId( invoices );
            PartnerDto partnerDto = this.partnerDao.findByPartnerId( invoices );
            UserDto userDto = this.userDao.findByUserId( partnerDto );
            PersonDto personPartnerDto = this.personDao.findByUserId( userDto );
            System.out.println( "Responsable: " + personDto.getName()
                    + ", Socio: " + personPartnerDto.getName()
                    + ", Fecha: " + invoices.getCreationDate()
                    + ", Monto: " + invoices.getAmount()
                    + ", Estado: " + invoices.getStatus() );
        }        
    }

    @Override
    public void historyPartnerInvoice( PartnerDto partnerDto ) throws Exception {
        ArrayList<InvoiceDto> listInvoices = this.invoiceDao.listPartnerInvoices( partnerDto );
        if ( listInvoices.isEmpty() ){
            throw new Exception( "No hay historial de facturación" );
        }
        List<InvoiceDto> filteredAndSorted = listInvoices.stream()
                .sorted(Comparator.comparing(InvoiceDto::getCreationDate)) 
                .collect(Collectors.toList());
        
        for ( InvoiceDto invoices : filteredAndSorted ){
            PersonDto personDto = this.personDao.findByPersonId( invoices );
            System.out.println( "Responsable: " + personDto.getName()
                    + ", Fecha: " + invoices.getCreationDate()
                    + ", Monto: " + invoices.getAmount()
                    + ", Estado: " + invoices.getStatus() );
        }
    }
    
    private void CreateInvoice( PersonDto personDto, PartnerDto partnerDto ) throws Exception{
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setPersonId( Helper.parse( personDto ) );
        invoiceDto.setPartnerId( Helper.parse( partnerDto ) );
        invoiceDto.setStatus( "PENDIENTE" );

        ArrayList<InvoiceDetailDto> listInvoiceDetails = new ArrayList<InvoiceDetailDto>();
        double amountInvoice = 0;        
        boolean continueRead = true;
        while ( continueRead ){
            InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();        
            invoiceDetailDto.getInvoiceDetailDescriptionDto();
            invoiceDetailDto.getInvoiceDetailAmountDto();
            invoiceDetailDto.setItem( listInvoiceDetails.size() + 1 );
            listInvoiceDetails.add( invoiceDetailDto );

            amountInvoice += invoiceDetailDto.getAmount();
            
            System.out.println("1. Para más detalles");
            String continueReadConsole = Utils.getReader().nextLine();
            if ( continueReadConsole.equals( "1" ) ){
                continueRead = true;
            }
            else{
                continueRead = false;                
            }
        }
        
        invoiceDto.setAmount( amountInvoice );
        this.invoiceDao.createInvoice( invoiceDto );

        for ( InvoiceDetailDto invoiceDetailsDto : listInvoiceDetails ) {
            invoiceDetailsDto.setInvoiceId( Helper.parse( invoiceDto ) );
            this.invoiceDetailDao.createInvoiceDetail( invoiceDetailsDto );
        }
        
        if ( partnerDto.getAmount() >= invoiceDto.getAmount() ){
            this.invoiceDao.cancelInvoice( invoiceDto );
            partnerDto.setAmount( partnerDto.getAmount() - amountInvoice );
            this.partnerDao.updatePartner( partnerDto );
        }
    }
}