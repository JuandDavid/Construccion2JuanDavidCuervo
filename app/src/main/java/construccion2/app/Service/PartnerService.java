package app.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import App.Controllers.Utils;
import App.Service.Intefaces.PartnerServiceInterface;
import App.Helper.Helper;

import App.Dao.PersonDao;
import App.Dao.PartnerDao;
import App.Dao.InvoiceDao;
import App.Dao.InvoiceDetailDao;
import App.Dao.UserDao;
import App.Dto.InvoiceDto;
import App.Dto.PersonDto;
import App.Dto.PartnerDto;
import App.Dto.PartnerInvoiceAmountDto;
import App.Dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@Setter

@Service
    public class PartnerService implements PartnerServiceInterface {
    @Autowired
    private final PersonDao personDao = new PersonDao();
    @Autowired
    private final UserDao userDao = new UserDao();
    @Autowired
    private final PartnerDao partnerDao = new PartnerDao();
    @Autowired
    private final InvoiceDao invoiceDao = new InvoiceDao();
    @Autowired
    private final InvoiceDetailDao invoiceDetailDao = new InvoiceDetailDao();

    @Override
    public void createPartner( ) throws Exception {
        PersonDto personDtoLocale = new PersonDto();
        personDtoLocale.getPersonDocumentDto();
        personDtoLocale = this.personDao.findByDocument( personDtoLocale );
        if ( personDtoLocale == null ){
            throw new Exception("No existe la persona");
        }

        UserDto userDtoLocate = this.userDao.findByPersonId( personDtoLocale );
        if ( userDtoLocate == null ) {
            throw new Exception("No se encontro ningún usuario con el número de Id");            
        }
        
        PartnerDto partnerDto = this.partnerDao.findByUserId( userDtoLocate );
        if ( partnerDto != null ){
            throw new Exception( personDtoLocale.getName() + " ya es socio del club");
        }
        
        partnerDto = new PartnerDto();
        
        partnerDto.setUserId( Helper.parse( userDtoLocate ) );
        
        partnerDto.getPartnerTypeDto();
        if ( partnerDto.getType().equals( "VIP" ) ){
            long numberVIP = this.partnerDao.numberPartnersVIP();
            if ( numberVIP >= 5 ){
                throw new Exception("Cupo de socios vip lleno");                
            }
        }
        
        partnerDto.getPartnerAmountDto();

        this.partnerDao.createPartner( partnerDto );
    }
    
    @Override
    public void updatePartnerAmount( ) throws Exception {
        PersonDto personDtoLocale = new PersonDto();
        personDtoLocale.getPersonDocumentDto();
        personDtoLocale = this.personDao.findByDocument( personDtoLocale );
        if ( personDtoLocale == null ){
            throw new Exception("No existe la persona");
        }

        UserDto userDtoLocate = this.userDao.findByPersonId( personDtoLocale );
        if ( userDtoLocate == null ) {
            throw new Exception("No se encontro ningún usuario para: " + personDtoLocale.getName() );            
        }
        
        PartnerDto partnerDtoLocale = this.partnerDao.findByUserId( userDtoLocate );
        if ( partnerDtoLocale == null ){
            throw new Exception( personDtoLocale.getName() +  " NO es socio del club");
        }
        
        double amountDao = partnerDtoLocale.getAmount();
        
        partnerDtoLocale.setUserId(Helper.parse( userDtoLocate ) );
        
        partnerDtoLocale.getPartnerAmountIncraseDto();
        
        if ( partnerDtoLocale.getType().equals( "REGULAR" ) || partnerDtoLocale.getType().equals( "Solicitud de cambio a vip" ) ){
            if ( ( partnerDtoLocale.getAmount() + amountDao ) > 1000000 ){
                throw new Exception( personDtoLocale.getName() +  " excede el monto permitido de inversion");
            }
        }
        else{
            if ( ( partnerDtoLocale.getAmount() + amountDao ) > 5000000 ){
                throw new Exception( personDtoLocale.getName() +  " excede el monto permitido de inversion");
            }            
        }

        PartnerDto partnerDtoDao = this.partnerDao.findByUserId( userDtoLocate );
        
        partnerDtoLocale.setAmount( partnerDtoLocale.getAmount() + partnerDtoDao.getAmount() );
        
        this.partnerDao.updatePartner( partnerDtoLocale );
        
        ArrayList<InvoiceDto> listInvoice =  this.invoiceDao.listPartnerInvoices( partnerDtoLocale );

        List<InvoiceDto> filteredAndSorted = listInvoice.stream()
                .filter(invoice -> "PENDIENTE".equals(invoice.getStatus())) 
                .sorted(Comparator.comparing(InvoiceDto::getCreationDate)) 
                .collect(Collectors.toList());
        
        for ( InvoiceDto invoiceDtoList : filteredAndSorted ) {
            partnerDtoDao = this.partnerDao.findByUserId( userDtoLocate );
            if ( partnerDtoDao.getAmount() >= invoiceDtoList.getAmount() ){
                this.invoiceDao.cancelInvoice( invoiceDtoList );

                partnerDtoDao.setAmount( partnerDtoDao.getAmount() - invoiceDtoList.getAmount() );
                this.partnerDao.updatePartner( partnerDtoDao );
            }
            else {
                break;
            }
        }
    }

    @Override
    public void deletePartner( ) throws Exception {
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
            throw new Exception("No se encontro ningun usuario con el numero de identificacion ingresado");            
        }
        
        PartnerDto partnerDto = this.partnerDao.findByUserId( userDtoLocate );
        
        if ( partnerDto == null ){
            throw new Exception( "No existe el socio");
        }
        
        if ( partnerDto.getAmount() > 0 ){
            throw new Exception( "El socio tiene inversion disponible");
        }
        
        this.partnerDao.deletePartner( partnerDto );
    }
    
    @Override
    public void deletePartner( UserDto userDto ) throws Exception {
        PersonDto personDtoLocale = this.personDao.findByUserId( userDto );
        if ( personDtoLocale == null ){
            throw new Exception("No existe la persona");
        }
        
        double amountActiveInvoices = this.invoiceDao.amountActiveInvoices( personDtoLocale );
        if ( amountActiveInvoices > 0 ){
            throw new Exception( personDtoLocale.getName() + " tiene facturas pendientes de pago");
        }
        
        PartnerDto partnerDto = this.partnerDao.findByUserId( userDto );
        
        if ( partnerDto == null ){
            throw new Exception("No existe el socio");                            
        }

        if ( partnerDto.getAmount() > 0 ){
            throw new Exception( "El socio tiene inversion disponible");
        }

        this.partnerDao.deletePartner( partnerDto );
    }    

    @Override
    public void updatePartnerType( PartnerDto partnerDto ) throws Exception {
        partnerDto.setType( "Solicitud de cambio a vip" );
        this.partnerDao.updatePartner( partnerDto );
    }

    @Override
    public void changePartnersToVIP() throws Exception {
        long numberVIP = this.partnerDao.numberPartnersVIP();
        if ( numberVIP >= 5 ){
            throw new Exception("Cupo de socios vip lleno");                
        }
        
        long available = 5 - numberVIP;
        long numberRequesVIP = this.partnerDao.numberPartnersRequestVIP();
        if ( numberRequesVIP > available ){
            System.out.println (String.valueOf( numberRequesVIP ) + " candidatos, " + String.valueOf( available ) + " cupos disponibles");
        }

        ArrayList<PartnerDto> listPartners = this.partnerDao.listPartnerRequestVIP();
        
        ArrayList<PartnerInvoiceAmountDto> listPartnersInvoiceAmount = new ArrayList<PartnerInvoiceAmountDto>() ;

        double invoicesAmount;
        
        for ( PartnerDto partnersDto : listPartners ){
            PartnerInvoiceAmountDto partnerInvoiceAmountDto = new PartnerInvoiceAmountDto();
            partnerInvoiceAmountDto.setId( partnersDto.getId() );
            partnerInvoiceAmountDto.setUserId( partnersDto.getUserId() );
            partnerInvoiceAmountDto.setType( partnersDto.getType() );
            partnerInvoiceAmountDto.setAmount( partnersDto.getAmount() );
            partnerInvoiceAmountDto.setCreationDate( partnersDto.getCreationDate() );
            invoicesAmount = this.invoiceDao.amountCancelInvoicesByPartner( partnersDto );            
            partnerInvoiceAmountDto.setInvoiceAmount( invoicesAmount );
            
            listPartnersInvoiceAmount.add( partnerInvoiceAmountDto );
        }

        List<PartnerInvoiceAmountDto> partersInvoiceAmountSorted = listPartnersInvoiceAmount.stream()
                .sorted( Comparator.comparing( PartnerInvoiceAmountDto::getInvoiceAmount ).reversed()
                    .thenComparing( Comparator.comparing( PartnerInvoiceAmountDto::getAmount ).reversed() )
                    .thenComparing( PartnerInvoiceAmountDto::getCreationDate ) 
                )
                .collect(Collectors.toList());

        PersonDto personDto;
        UserDto userDto;

        for ( PartnerInvoiceAmountDto partnerInvoiceAmountDto : partersInvoiceAmountSorted ){
            PartnerDto partnerDto = new PartnerDto();
            partnerDto.setId( partnerInvoiceAmountDto.getId() );
            partnerDto.setUserId( partnerInvoiceAmountDto.getUserId() );
            partnerDto.setType( partnerInvoiceAmountDto.getType() );
            partnerDto.setAmount( partnerInvoiceAmountDto.getAmount() );
            partnerDto.setCreationDate( partnerInvoiceAmountDto.getCreationDate() );
            
            userDto = this.userDao.findByUserId( partnerDto );
            personDto = this.personDao.findByUserId( userDto );
            invoicesAmount = partnerInvoiceAmountDto.getInvoiceAmount();
            
            System.out.println( "Autorizar promoción a: " + personDto.getName() 
                    + " fondos: " + partnerDto.getAmount() 
                    + " ingreso: " + partnerDto.getCreationDate() 
                    + " facturado: " + invoicesAmount);
            
            partnerDto.setType( "VIP" );

            this.partnerDao.updatePartner( partnerDto );
            numberVIP = this.partnerDao.numberPartnersVIP();
            if ( numberVIP >= 5 ){
                break;
            }
        }
    }
}