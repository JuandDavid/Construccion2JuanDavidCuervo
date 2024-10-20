package app.Service.Intefaces;

import App.Dto.UserDto;
import App.Dto.PartnerDto;

public interface PartnerServiceInterface {
    public void createPartner( ) throws Exception;
    public void updatePartnerAmount( ) throws Exception;
    public void updatePartnerType( PartnerDto partnerDto ) throws Exception;
    public void deletePartner( ) throws Exception;
    public void deletePartner( UserDto userDto ) throws Exception;    
    public void changePartnersToVIP(  ) throws Exception;    
}