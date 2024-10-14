package App.Controller;

import App.Dao.PartnerDao;
import App.Dto.PartnerDto;

import App.Service.LoginService;
import App.Service.UserService;
import App.Service.PartnerService;
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
public class PartnerController implements ControllerInterface{
    private static final String MENU = "Ingrese una opcion \n "
            + "1. Solicitar consumo \n "
            + "2. Ver historial de consumos \n "
            + "3. Crear Invitado \n "
            + "4. Eliminar Invitado \n "
            + "5. Cambio a VIP \n "
            + "6. Cambiar PASSWORD \n "
            + "9. Para cerrar sesion \n";

    @Autowired
    private final PartnerDaoInterface partnerDao = new PartnerDaoInterface();
    
    @Autowired
    private final UserService userService = new UserService();
    @Autowired
    private final PartnerService partnerService = new PartnerService();
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
                PartnerDto partnerDto = this.partnerDao.findByUserId( LoginService.user );
                this.invoiceService.createPartnerInvoice( partnerDto );
                return true;
            }
            case "2": {
                PartnerDto partnerDto = this.partnerDao.findByUserId( LoginService.user );
                this.invoiceService.historyPartnerInvoice( partnerDto );
                return true;
            }
            case "3": {
                this.guestService.createGuest( LoginService.user );
                return true;
            }
            case "4": {
                this.guestService.deleteGuest();
                return true;
            }
            case "5": {
                PartnerDto partnerDto = this.partnerDao.findByUserId( LoginService.user );
                this.partnerService.updatePartnerType( partnerDto );
                return true;
            }
            case "6": {
                this.userService.changeUserPassword( LoginService.user );
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