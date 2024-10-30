package app.Controller;

import app.Controller.Valitators.UserValidator;
import app.Dto.UserDto;
import app.Service.LoginService;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Setter
@Getter
@Controller
public class LoginController implements ControllerInterface {
    private static final String MENU = "ingrese la opcion que desea: \n "
            + "1. para iniciar sesion. \n "
            + "9. para detener la ejecucion.";
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private LoginService serviceLogin;
    private Map<String, ControllerInterface> roles;

    public  LoginController ( AdminController adminController, PartnerController partnerController, GuestController guestController) {
        this.roles = new HashMap<String, ControllerInterface>();
        roles.put("ADMINISTRADOR", adminController);
        roles.put("SOCIO", partnerController);
        roles.put("INVITADO", guestController);
    }
    
    @Override
    public void session() throws Exception {
        boolean session = true;
        while (session) {
            session = menu();
        }		
    }

    private boolean menu() {
        try {
            System.out.println(MENU);
            String option = Utils.getReader().nextLine();
            return options(option);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    private boolean options(String option) throws Exception {
        switch (option) {
            case "1": {
                this.login();
                return true;
            }
            case "9": {
                System.out.println("se detiene el programa");;
                return false;
            }
            default: {
                System.out.println("Ingrese una opcion valida");
                return true;
            }
        }
    }
    
    private void login() throws Exception {
        System.out.println("ingrese el usuario");
        String userName = Utils.getReader().nextLine();
        this.userValidator.validUserName(userName);
        System.out.println("ingrese la contraseña");
        String password = Utils.getReader().nextLine();
        this.userValidator.validPassword(password);
        UserDto userDto = new UserDto();
        userDto.setPassword(password);
        userDto.setUserName(userName);
        this.serviceLogin.login(userDto);
        if(this.roles.get(userDto.getRole()) == null) {
            throw new Exception ("Rol invalido");
        }
        this.roles.get(userDto.getRole()).session();
    }
}