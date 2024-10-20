package app.Dao.Interfaces;

import app.Dto.GuestDto;
import app.Dto.UserDto;
import java.util.List;

public interface GuestDao {
   
    void createGuest(GuestDto guestDto) throws Exception;
    GuestDto findGuestById(GuestDto guestDto) throws Exception;
    void updateGuest(GuestDto guestDto) throws Exception;
    void deleteGuest(long id) throws Exception;
    public GuestDto findByUserId( UserDto userDto ) throws Exception;
    List<GuestDto> getAllGuest() throws Exception;
}