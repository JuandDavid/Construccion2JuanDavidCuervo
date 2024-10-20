package app.Dao.Interfaces;
import App.Dto.PersonDto;

public interface PersonDaoInterface{
	  
   public void createPerson(PersonDto personDto) throws Exception;
   public PersonDto findPersonById(long id) throws Exception;
  public void updatePerson(PersonDto personDto) throws Exception;
   public void deletePerson(PersonDto personDto) throws Exception;
   public boolean existsByDocument (PersonDto personDto) throws Exception;
}