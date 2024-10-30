package app.Dao;

import app.Dto.PersonDto;
import app.Helper.Helper;
import app.Model.Person;
import app.Dao.Interfaces.PersonDaoInterface;
import app.Dao.Repository.PersonRepository;
import app.Dto.InvoiceDto;
import app.Dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service
public class PersonDao implements PersonDaoInterface {
    @Autowired
    PersonRepository personRepository;

    @Override
    public boolean existsByDocument( PersonDto personDto ) throws Exception {
        Person person = Helper.parse( personDto );
        return this.personRepository.existsByDocument( person.getDocument() );
    }

    @Override
    public void createPerson(PersonDto personDto) throws Exception {
        Person person = Helper.parse(personDto);
        this.personRepository.save( person );
    }

    @Override
    public void deletePerson(PersonDto personDto) throws Exception {
        Person person = Helper.parse(personDto);
        this.personRepository.deleteById( person.getId() );
    }

    @Override
    public PersonDto findByDocument( PersonDto personDto ) throws Exception {
        return Helper.parse( this.personRepository.findByDocument( personDto.getDocument() ) );
    }

    @Override
    public PersonDto findByUserId( UserDto userDto ) throws Exception {
        return Helper.parse( this.personRepository.findById( userDto.getPersonnId().getId() ) );
    }

    @Override
    public PersonDto findByPersonId( InvoiceDto invoiceDto ) throws Exception {
        return Helper.parse( this.personRepository.findById( invoiceDto.getPersonId().getId() ) );
    }

    @Override
    public void updatePerson(PersonDto personDto) throws Exception {
        Person person = Helper.parse( personDto );
        this.personRepository.save( person );
    }
}