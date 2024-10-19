package App.Dao;

import java.util.ArrayList;
import App.Helper.Helper;
import App.Dto.UserDto;
import App.Dto.PartnerDto;
import App.Dto.GuestDto;
import App.Dao.Interfaces.PartnerDaoInterface;
import App.Dao.Repository.PartnerRepository;
import App.Dto.InvoiceDto;
import App.Model.User;
import App.Model.Partner;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@Setter
@Service

public class PartnerDao implements PartnerDaoInterface{
    @Autowired
    PartnerRepository partnerRepository;

    @Override
    public boolean existsByUserId(UserDto userDto) throws Exception {
        User user = Helper.parse( userDto );
        return this.partnerRepository.existsByUserId( user );
    }

    @Override
    public void createPartner(PartnerDto partnerDto) throws Exception {
        Partner partner = Helper.parse( partnerDto );
        partner.setCreationDate( Timestamp.valueOf( LocalDateTime.now() ) );
        this.partnerRepository.save( partner );
    }

    @Override
    public void updatePartner(PartnerDto partnerDto) throws Exception {
        Partner partner = Helper.parse( partnerDto );
        this.partnerRepository.save( partner );
    }

    @Override
    public void deletePartner(PartnerDto partnerDto) throws Exception {
        Partner partner = Helper.parse( partnerDto );
        this.partnerRepository.deleteById( partner.getId() );
    }

    @Override
    public PartnerDto findByUserId( UserDto userDto ) throws Exception {
        User user = Helper.parse( userDto );
        Partner partner = this.partnerRepository.findByUserId( user );
        return Helper.parse( partner );
    }

    @Override
    public PartnerDto findByGuestPartnerId( GuestDto guestDto ) throws Exception {
        Partner partner = this.partnerRepository.findById( guestDto.getPartnerId().getId() );
        return Helper.parse( partner );
    }

    @Override
    public PartnerDto findByPartnerId( InvoiceDto invoiceDto ) throws Exception {
        Partner partner = this.partnerRepository.findById( invoiceDto.getPartnerId().getId() );
        return Helper.parse( partner );
    }

    @Override
    public long numberPartnersVIP(  ) throws Exception {
        return this.partnerRepository.countByType( "Vip" );
    }

    @Override
    public long numberPartnersRequestVIP(  ) throws Exception {
        return this.partnerRepository.countByType( "Solicitud cambio a Vip" );
    }
    
    @Override
    public ArrayList<PartnerDto> listPartnerRequestVIP( ) throws Exception{
        ArrayList<Partner> listToVIP = this.partnerRepository.findByType( "Solicitud cambio a Vip" );
        ArrayList<PartnerDto> listPartners = new ArrayList<PartnerDto>();
        for ( Partner partners : listToVIP ){
            listPartners.add( Helper.parse( partners ) );            
        }
        return listPartners;
    }
}