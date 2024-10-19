package App.Controller;

import App.Dao.GuestDao;
import App.Dao.PersonDao;
import App.Dto.GuestDto;

import App.Service.LoginService;
import App.Service.UserService;
import App.Service.GuestService;
import App.Service.InvoiceService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Getter
@NoArgsConstructor
@Setter
@Controller

public class GuestController implements ControllerInterface{
    private static final String MENU = "Ingrese una opcion \n "
            + "1. Solicitar consumo  \n "
            + "2. Cambio a socio \n "
            + "3. Cambiar password\n "
            + "0. Finalizar sesion \n";
    
    @Autowired
    private final PersonDao personDao = new PersonDao();
    @Autowired
    private final GuestDao guestDao = new GuestDao();
    
    @Autowired
    private final UserService userService = new UserService();
    @Autowired
    private final InvoiceService invoiceService = new InvoiceService();
    @Autowired
    private final GuestService guestService = new GuestService();
    
    
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
                GuestDto guestDto = this.guestDao.findByUserId( LoginService.user );
                this.invoiceService.createGuestInvoice( guestDto );
                return true;
            }

            case "2": {
                this.guestService.changeGuestToPartner( LoginService.user );
                return true;
            }
            case "3": {
                this.userService.changeUserPassword( LoginService.user );
                return true;
            }
            case "0": {
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