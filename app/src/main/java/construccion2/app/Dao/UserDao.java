package app.Dao;

import App.Dao.Interfaces.UserDaoInteface;
import App.Dao.Repository.UserRepository;
import App.Dto.GuestDto;
import App.Dto.PartnerDto;

import App.Dto.PersonDto;
import App.Model.User;
import App.Dto.UserDto;
import App.Helper.Helper;
import App.Model.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@Setter
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