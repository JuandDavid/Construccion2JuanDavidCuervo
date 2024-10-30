package app.Dao;

import app.Dao.Interfaces.UserDaoInteface;
import app.Dao.Repository.UserRepository;
import app.Dto.GuestDto;
import app.Dto.PartnerDto;

import app.Dto.PersonDto;
import app.Model.User;
import app.Dto.UserDto;
import app.Helper.Helper;
import app.Model.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service

public class UserDao implements UserDaoInteface {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto findByUserName( UserDto userDto ) throws Exception {
        User user = this.userRepository.findByUserName(userDto.getUserName());
        return Helper.parse( user );
    }

    @Override
    public UserDto findByPersonId( PersonDto personDto ) throws Exception {
        Person person = Helper.parse( personDto );
        User user = this.userRepository.findByPersonnId( person );
        return Helper.parse( user );
    }

    @Override
    public UserDto findByUserId( PartnerDto partnerDto ) throws Exception {
        User user = this.userRepository.findById( partnerDto.getUserId().getId() );
        return Helper.parse( user );
    }

    @Override
    public UserDto findByGuestUserId( GuestDto guestDto ) throws Exception {
        User user = this.userRepository.findById( guestDto.getUserId().getId() );
        return Helper.parse( user );
    }

    @Override
    public boolean existsByUserName(UserDto userDto) throws Exception {
        return this.userRepository.existsByUserName( userDto.getUserName() );
    }

    @Override
    public void createUser(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        this.userRepository.save( user );
        userDto.setId( user.getId() );
    }
    
    @Override
    public void updateUser(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        this.userRepository.save( user );
    }

    @Override
    public void deleteUser(UserDto userDto) throws Exception {
        User user = Helper.parse(userDto);
        this.userRepository.deleteById( user.getId() );
    }
}