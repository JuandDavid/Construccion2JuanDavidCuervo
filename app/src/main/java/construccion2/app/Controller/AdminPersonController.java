package App.Controller;

import App.Service.LoginService;
import App.Service.PersonService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Getter
@NoArgsConstructor
@Setter

@Controller
public class AdminPersonController implements ControllerInterface{
    private static final String MENU = "Ingrese una opcion \n "
            + "1. Crear persona \n "
            + "2. Actualizar persona \n "
            + "3. Borrar persona \n "
            + "0. Cerrar sesion  \n";
    
    @Autowired
    private PersonService personService = new PersonService();

    @Override
    public void session() throws Exception {
        boolean session = true;
        while (session) {
            session = menu();
        }
    }

    private boolean menu() {
        try {
            System.out.println("Bienvenido " + LoginService.user.getUserName());
            System.out.print(MENU);
            String option = Utils.getReader().nextLine();
            return options(option);

        } catch ( Exception e ) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    private boolean options(String option) throws Exception{
        switch (option) {
            case "1": {
                this.personService.createPerson();
                return true;
            }
            case "2": {
                this.personService.updatePerson();
                return true;
            }
            case "3": {
                this.personService.deletePerson();
                return true;
            }
            case "0": {
                System.out.println("Se ha cerrado sesion");
                return false;
            }
            default: {
                System.out.println("Ingrese una opcion valida");
                return true;
            }
        }
    }
}