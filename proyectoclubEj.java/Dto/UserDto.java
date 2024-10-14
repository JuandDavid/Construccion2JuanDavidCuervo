package App.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import App.Controllers.Utils;
import App.Controllers.Validator.UserValidator;
import App.Dto.Interfaces.UserDtoInterface;
import App.Model.Person;

@Getter
@Setter
@NoArgsConstructor

public class UserDto implements UserDtoInterface{
    private long id;
    private Person personId;
    private String userName;
    private String password;
    private String role;

    private final UserValidator userValidator = new UserValidator();
    
    @Override
    public void getUserNameDto() throws Exception {
        System.out.println("Ingrese nombre de usuario");
        String userNameDto = Utils.getReader().nextLine();
        this.userValidator.validUserName( userNameDto );
        this.userName = userNameDto;
    }

    @Override
    public void getUserTypeDto() throws Exception {
        String userRoleDto = "";
        boolean continueRead = true;
        while ( continueRead ){
            System.out.println("Ingrese el rol de usuario\n 
            1. Administrador\n 
            2. Socio\n 
            3. Invitado\n 
            4. Cancelar\n");
            userRoleDto = Utils.getReader().nextLine();
            switch ( userRoleDto ){
                case "1": {
                    userRoleDto = "Administrador";
                    continueRead = false;
                    break;
                }
                case "2": {
                    userRoleDto = "Socio";
                    continueRead = false;
                    break;
                }
                case "3": {
                    userRoleDto = "Invitado";
                    continueRead = false;
                    break;
                }
                case "4": {
                    userRoleDto = "";
                    continueRead = false;
                    break;
                }
                default: {
                    System.out.println("Ingrese una opcion valida");
                }
            }            
        }
        this.userValidator.validRole( userRoleDto );
        this.role = userRoleDto;
    }

    @Override
    public void getUserPasswordDto() throws Exception {
        System.out.println("Ingrese password de usuario");
        String userPasswordDto = Utils.getReader().nextLine();
        this.userValidator.validPassword( userPasswordDto );
        this.password = userPasswordDto;        
    }    
}