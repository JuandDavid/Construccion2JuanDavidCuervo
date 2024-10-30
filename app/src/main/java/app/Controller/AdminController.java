package app.Controller;

import app.Service.LoginService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Getter
@Setter
@NoArgsConstructor
@Controller

public class AdminController implements ControllerInterface{
    private static final String MENU = "Ingrese la opcion que desea \n "
            + "1. PERSONAS \n "
            + "2. USUARIOS \n "
            + "3. SOCIOS \n "
            + "4. INVITADOS \n "
            + "5. PROCESOS \n "
            + "9. Para cerrar sesion \n";
    
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
            System.out.println("bienvenido " + LoginService.user.getUserName());
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