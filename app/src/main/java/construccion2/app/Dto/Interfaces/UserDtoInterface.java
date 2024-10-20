package app.Dto;

import app.Model.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter

public class UserDtoInterface{
    private Long id;
    private Person personId;
    private String userName;
    private String password;
    private String role;  
}