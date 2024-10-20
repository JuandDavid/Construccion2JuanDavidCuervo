package app.Service;

import App.Dao.PersonDao;
import App.Dao.UserDao;
import App.Dto.PersonDto;
import App.Dto.UserDto;
import App.Service.Intefaces.PersonServiceInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@Setter

@Service
    public class PersonService implements PersonServiceInterface {
    @Autowired
    private final PersonDao personDao = new PersonDao();
    @Autowired
    private final UserDao userDao = new UserDao();
    
    @Override
    public PersonDto createPerson( ) throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.getPersonDocumentDto();
        
        if ( this.personDao.existsByDocument( personDto ) ) {
            personDto = this.personDao.findByDocument( personDto );
            System.out.println("Ya existe: " + personDto.getName() );
            personDto = this.personDao.findByDocument( personDto );
            return personDto;
        }
        
        personDto.getPersonNameDto();
        personDto.getPersonCellNumberDto();
        
        this.personDao.createPerson( personDto );
        
        personDto = this.personDao.findByDocument( personDto );
        return personDto;
    }

    @Override
    public PersonDto updatePerson( ) throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.getPersonDocumentDto();
        
        if ( !this.personDao.existsByDocument( personDto ) ) {
            throw new Exception( "No se encentra el número identificación");
        }
        
        personDto = this.personDao.findByDocument( personDto );
        personDto.getPersonCellNumberDto();
        
        this.personDao.updatePerson( personDto );
        
        personDto = this.personDao.findByDocument( personDto );
        return personDto;        
    }

    @Override
    public void deletePerson( ) throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.getPersonDocumentDto();
        
        if ( !this.personDao.existsByDocument( personDto ) ) {
            throw new Exception( "No se encentra el número identificación");
        }
        personDto = this.personDao.findByDocument( personDto );
                
        UserDto userDto = this.userDao.findByPersonId( personDto );
        if ( userDto != null ){
            throw new Exception("La persona tiene usuario" );
        }
        
        System.out.println("Eliminar a: " + personDto.getName() );
        this.personDao.deletePerson( personDto );        
    }
}