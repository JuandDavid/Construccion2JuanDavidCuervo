package App.Dao;

import App.Dto.PersonDto;
import App.Helper.Helper;
import App.Model.Person;
import App.Dao.Interfaces.PersonDaoInterface;
import App.Dao.Repository.PersonRepository;
import App.Dto.InvoiceDto;
import App.Dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@Setter
@Service

public class PersonDao implements PersonDaoInterface{
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