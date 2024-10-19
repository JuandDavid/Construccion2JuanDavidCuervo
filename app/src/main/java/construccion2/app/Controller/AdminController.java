package App.Controller;

import App.Service.LoginService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Getter
@NoArgsConstructor
@Setter
@Controller

public class AdminController implements ControllerInterface{
    private static final String MENU = "Ingrese una opcion \n "
            + "1. Personas \n "
            + "2. Usuarios \n "
            + "3. Socios \n "
            + "4. Invitados \n "
            + "5. Procesos \n "
            + "9. Cerrar sesion \n";
    
    @Autowired
    public ControllerInterface adminPersonController = new AdminPersonController();
    @Autowired
    public ControllerInterface adminUserController = new AdminUserController();
    @Autowired
    public ControllerInterface adminPartnerController = new AdminPartnerController();
    @Autowired
    public ControllerInterface adminGuestController = new AdminGuestController();
    @Autowired
    public ControllerInterface adminProcessesController = new AdminProcessesController();

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
                this.adminPersonController.session();
                return true;
            }
            case "2": {
                this.adminUserController.session();
                return true;
            }
            case "3": {
                this.adminPartnerController.session();
                return true;
            }
            case "4": {
                this.adminGuestController.session();
                return true;
            }
            case "5": {
                this.adminProcessesController.session();
                return true;
            }
            case "9": {
                System.out.println("Sesion finalizada");
                return false;
            }
            default: {
                System.out.println("Ingrese una opcion valida");
                return true;
            }
        }
    }

}