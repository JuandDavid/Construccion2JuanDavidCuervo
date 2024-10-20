package app.Service;

import java.sql.SQLException;
import App.Service.Intefaces.UserServiceInterface;

import App.Dao.PersonDao;
import App.Dao.UserDao;
import App.Dao.PartnerDao;

import App.Dto.PersonDto;
import App.Dto.UserDto;
import App.Dto.PartnerDto;
import App.Helper.Helper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@Setter

@Service
    public class UserService implements UserServiceInterface {
    @Autowired
    private final PersonService personService = new PersonService();

    @Autowired
    private final PersonDao personDao = new PersonDao();
    @Autowired
    private final UserDao userDao = new UserDao();
    @Autowired
    private final PartnerDao partnerDao = new PartnerDao();

    @Override
    public void createUser( ) throws Exception {
        UserDto userDto;
        PersonDto personDto = this.personService.createPerson( );
        
        userDto = this.userDao.findByPersonId( personDto ) ;
        
        if ( userDto != null ){
            throw new Exception("El usuario para: " + personDto.getName() + " es: " + userDto.getUserName() );
        }
        
        userDto = new UserDto();
        userDto.setPersonnId( Helper.parse( personDto ) );
        
        userDto.getUserNameDto();
        userDto.getUserTypeDto();
        userDto.getUserPasswordDto();
                
        if ( this.userDao.existsByUserName( userDto ) ) {
            throw new Exception("Usuario existente");
        }
        
        this.userDao.createUser( userDto );
    }

    @Override
    public UserDto createGuestUser( ) throws Exception {
        UserDto userDto;
        PersonDto personDto = this.personService.createPerson( );
        
        userDto = this.userDao.findByPersonId( personDto ) ;
        
        if ( userDto != null ){
            if ( userDto.getRole().equals( "INVITADO" ) ){
                return userDto;
            }
            else{
                throw new Exception("El usuario no es invitado");
            }
        }
        
        userDto = new UserDto();
        userDto.setPersonnId( Helper.parse( personDto ) );
        
        userDto.getUserNameDto();
        userDto.setRole( "INVITADO" );
        userDto.getUserPasswordDto();
                
        if ( this.userDao.existsByUserName( userDto ) ) {
            throw new Exception("Usuario existente");
        }
        
        this.userDao.createUser( userDto );
        return userDto;
    }

    @Override
    public void changeUserPassword( ) throws Exception {
        UserDto userDto;
        PersonDto personDto = new PersonDto();
        personDto.getPersonDocumentDto();
        personDto = this.personDao.findByDocument( personDto );
        if ( personDto == null ){
            throw new Exception("No se encontro el documento" );            
        }
        
        userDto = this.userDao.findByPersonId( personDto ) ;
        if ( userDto == null ){
            throw new Exception( personDto.getName() + " no tiene usuario" );
        }
        
        System.out.println("Cambiar password al usuario: " + userDto.getUserName() );
        userDto.getUserPasswordDto();
        
        this.userDao.updateUser( userDto );
    }
    
    @Override
    public void changeUserPassword( UserDto userDto ) throws Exception {
        userDto.getUserPasswordDto();
        
        this.userDao.updateUser( userDto );
    }
    
    @Override
    public void changeUserRole( UserDto userDto ) throws Exception {
        
    }

    @Override
    public void deleteUser( ) throws Exception {
        UserDto userDto;
        PersonDto personDto = new PersonDto();
        personDto.getPersonDocumentDto();
        
        personDto = this.personDao.findByDocument( personDto );
        if ( personDto == null ){
            throw new Exception("La persona no existe" );
        }

        userDto = this.userDao.findByPersonId( personDto ) ;        
        if ( userDto == null ){
            throw new Exception("La persona no tiene usuario" );
        }
        
        PartnerDto partnerDto = this.partnerDao.findByUserId( userDto );
        
        if ( partnerDto != null ){
            throw new Exception("El usuario es socio" );            
        }
        
        System.out.println("Borrar usuario: " + userDto.getUserName() );
        
        this.userDao.deleteUser( userDto );        
    }
}