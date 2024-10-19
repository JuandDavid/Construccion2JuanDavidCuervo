package App.Controller.Validator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Setter
@Component

public class UserValidator extends CommonsValidator{	
    public void validUserName(String userName) throws Exception {
        super.isValidString("Nombre de usuario ", userName);
    }
    
    public void validPassword(String password) throws Exception {
        super.isValidString("Contraseña de usuario ", password);
    }
    
    public void validRole(String role) throws Exception {
        super.isValidString("Rol usuario", role);
    }
}