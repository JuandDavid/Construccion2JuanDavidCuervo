package App.Dto;

import App.Controller.Validator.PersonValidator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@NoArgsConstructor
@Setter

public class PersonDtoInterface{
    private long Id;
    private long document;
    private String name;
    private long cellPhone;
    private final PersonValidator personValidator = new PersonValidator();
}