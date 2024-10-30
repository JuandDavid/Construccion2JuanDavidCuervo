package app.Dao;

import app.Helper.Helper;
import app.Dao.Interfaces.GuestDaoInterface;
import app.Dao.Repository.GuestRepository;

import app.Dto.UserDto;
import app.Dto.GuestDto;
import app.Model.Guest;
import app.Model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service

public class GuestDao implements GuestDaoInterface{
    @Autowired
    GuestRepository guestRepository;

    @Override
    public boolean existsByUserId( UserDto userDto ) throws Exception {
        User user = Helper.parse( userDto );
        return this.guestRepository.existsByUserId( user );
    }

    @Override
    public void createGuest(GuestDto guestDto) throws Exception {
        Guest guest = Helper.parse( guestDto );
        this.guestRepository.save( guest );
    }

    @Override
    public void updateGuestStatus( GuestDto guestDto ) throws Exception {
        Guest guest = Helper.parse( guestDto );
        this.guestRepository.save( guest );
    }

    @Override
    public void deleteGuest( GuestDto guestDto ) throws Exception {
        Guest guest = Helper.parse( guestDto );
        this.guestRepository.deleteById( guest.getId() );
    }

    @Override
    public GuestDto findByUserId( UserDto userDto ) throws Exception {        
        User user = Helper.parse( userDto );
        Guest guest = this.guestRepository.findByUserId( user );
        return Helper.parse( guest );
    }
}