package app.Dao.Interfaces;

import app.Dto.PersonDto;
import app.Dto.UserDto;
import app.Dto.PartnerDto;
import app.Dto.GuestDto;

public interface UserDaoInteface {
    public UserDto findByUserName( UserDto userDto ) throws Exception;
    public UserDto findByPersonId( PersonDto personDto ) throws Exception;
    public UserDto findByUserId( PartnerDto partnerDto ) throws Exception;
    public UserDto findByGuestUserId( GuestDto guestDto ) throws Exception;
    public boolean existsByUserName( UserDto userDto ) throws Exception;
    public void createUser( UserDto userDto ) throws Exception;    
    public void updateUser( UserDto userDto ) throws Exception;    
    public void deleteUser( UserDto userDto ) throws Exception;    
}