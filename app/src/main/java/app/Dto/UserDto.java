package app.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import app.Controller.Utils;
import app.Controller.Valitators.UserValidator;
import app.Dto.Interfaces.UserDtoInterface;
import app.Model.Person;

@Getter
@Setter
@NoArgsConstructor
public class UserDto implements UserDtoInterface {
    private long id;
    private Person personnId;
    private String userName;
    private String password;
    private String role;

    private final UserValidator userValidator = new UserValidator();
    
    @Override
    public void getUserNameDto() throws Exception {
        System.out.println("Ingrese el nombre de usuario");
        String userNameDto = Utils.getReader().nextLine();
        this.userValidator.validUserName( userNameDto );
        this.userName = userNameDto;
    }

    @Override
    public void getUserTypeDto() throws Exception {
        String userRoleDto = "";
        boolean continueRead = true;
        while ( continueRead ){
            System.out.println("Ingrese el role del usuario \n 1. ADMINISTRADOR \n 2. SOCIO \n 3. INVITADO \n 4. CANCELAR \n");
            userRoleDto = Utils.getReader().nextLine();
            switch ( userRoleDto ){
                case "1": {
                    userRoleDto = "ADMINISTRADOR";
                    continueRead = false;
                    break;
                }
                case "2": {
                    userRoleDto = "SOCIO";
                    continueRead = false;
                    break;
                }
                case "3": {
                    userRoleDto = "INVITADO";
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
        System.out.println("Ingrese el password de usuario");
        String userPasswordDto = Utils.getReader().nextLine();
        this.userValidator.validPassword( userPasswordDto );
        this.password = userPasswordDto;        
    }    
}