package app.Service.Intefaces;

import App.Dto.UserDto;

public interface GuestServiceInterface {
    public void createGuest( ) throws Exception;
    public void createGuest( UserDto userDto ) throws Exception;
    public void deleteGuest( ) throws Exception;    
    public void changeGuestToPartner( UserDto userDto ) throws Exception;    
}