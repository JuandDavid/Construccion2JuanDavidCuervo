package app.controller;

import app.dto.GuestDto;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.model.GuestStatus;
import app.model.InvoiceStatus;
import app.model.Role;
import app.model.SubscriptionType;
import app.service.Service;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

public class PartnerController implements ControllerInterface{
    private Service service;
    private static final String MENU = "Ingrese una opcion: \n 1. Crear Invitado. "
            + "\n 2. Activar Usuario "
            + "\n 3. Desactivar Usuario "
            + "\n 4. Registrar consumo "
            + "\n 5. Recargar fondos "
            + "\n 6. Eliminar usuario "
            + "\n 7. Promover usuario a VIP \n"
            + "\n 8. Salir \n";

    public PartnerController(){
        this.service = new Service();
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
            System.out.println("Bienvenido");
            System.out.print(MENU);
            String option = Utils.getReader().nextLine();
            return options(option);
        } catch (Exception e) {
                System.out.println(e.getMessage());
                return true;
        }
    }
    
    private boolean options(String option) throws Exception{
        switch (option) {
            case "1": {
                System.out.println("Crear Invitado");
                this.createGuest();
                return true;
            }
            case "2": {
                System.out.println("Activar Usuario"); 
                return true;
            }
            case "3": {
                System.out.println("Desactivar Usuario"); 
                return true;
            }
            case "4": {
                System.out.println("Registrar Consumo");
                this.newService();
                return true;
            }
            case "5": {
                System.out.println("Recargar Fondos");
                this.increaseAmount();
                return true;
            }
            case "6": {
                System.out.println("Eliminar Usuario");
                this.cancelSubscription();
                return true;
            }
            case "7": {
                System.out.println("Promover Usuario a VIP");
                this.requestVIPUpgrade();
                return true;
            }
            case "8": {
                System.out.println("Sesion Terminada");
                return false;
            }
            default: {
                System.out.println("Ingrese una opcion valida");
                return true;
            }
        }
    }
    
    private void createGuest() throws Exception{
        System.out.println("Ingrese Numero De Documento: ");
        String inputDocument = Utils.getReader().nextLine();
        Long document = Utils.getValidator().isValidLong("Documento", inputDocument);
        
        System.out.println("Nombre: ");
        String inputName = Utils.getReader().nextLine();
        String name = Utils.getValidator().isValidString("Nombre", inputName);
        
        System.out.println("Numero Celular: ");
        String inputCellphone = Utils.getReader().nextLine();
        Long cellphone = Utils.getValidator().isValidLong("Celular", inputCellphone);
        
        System.out.println("Nombre De Usuario: ");
        String inputUserName = Utils.getReader().nextLine();
        String userName = Utils.getValidator().isValidString("Nombre de Usuario", inputUserName);
        
        System.out.println("Ingrese una contraseña: ");
        String inputPassword = Utils.getReader().nextLine();
        String password = Utils.getValidator().isValidString("Contraseña", inputPassword);
        
        PersonDto personDto = new PersonDto();
        personDto.setDocument(document);
        personDto.setName(name);
        personDto.setCellPhone(cellphone);
        
        UserDto userDto = new UserDto();
        userDto.setPersonId(personDto);
        userDto.setUserName(userName);
        userDto.setPassword(password);
        userDto.setRole(Role.GUEST);
        
        PartnerDto currentPartner = this.service.getCurrentPartner();
        
        GuestDto guestDto = new GuestDto();
        guestDto.setUserId(userDto);
        guestDto.setPartnerId(currentPartner);
        guestDto.setStatus(GuestStatus.INACTIVE);
        
        this.service.createGuest(guestDto);
    }
    
    private void increaseAmount() throws Exception {
        PartnerDto currentPartner = this.service.getCurrentPartner();
        final double maxAmountVIP = 5000000;
        final double maxAmountRegular = 1000000;
        
        System.out.println("Valor a recargar: ");
        String inputAddAmount = Utils.getReader().nextLine();
        Double addAmount = Utils.getValidator().isValidDouble("Fondos adicionales", inputAddAmount);
        
        double newAmount = currentPartner.getAmount() + addAmount;
        if((currentPartner.getType() == SubscriptionType.VIP && newAmount > maxAmountVIP) || 
            (currentPartner.getType() != SubscriptionType.VIP && newAmount > maxAmountRegular)){
            throw new Exception("Nuevo saldo supera el máximo permitido \n"
                + "- VIP: " + maxAmountVIP + "\n"
                + "- REGULARES: " + maxAmountRegular + "\n");
        }
        currentPartner.setAmount(newAmount);
        
        this.service.updatePartner(currentPartner);
        System.out.println("Saldo Actualizado");
    }
    
    private void requestVIPUpgrade() throws Exception {
        PartnerDto currentPartner = this.service.getCurrentPartner();
        currentPartner.setType(SubscriptionType.PENDING_VIP);
        this.service.updatePartner(currentPartner);
        System.out.println("Solicitud creada exitosamente.");
    }
    
    private void cancelSubscription() throws Exception {
        List<InvoiceDto> partnerInvoices = this.service.getPendingInvoicesByCurrentPartnerId();
        if(partnerInvoices.size() > 0){
            throw new Exception("Cancele facturas pendientes e intente nuevamente \n");
        }
        System.out.println("Factura Eliminada");
        this.service.deleteInvoicesByCurrentPartnerId();
        
        System.out.println("Invitado Eliminado");
        this.service.deleteGuestsByCurrentPartnerId();
        
        System.out.println("Datos Eliminados");
        this.service.deleteCurrentPartner();
    }
    
    private void newService() throws Exception {
        System.out.println("Descripcion del producto: ");
        String inputDesc = Utils.getReader().nextLine();
        String desc = Utils.getValidator().isValidString("Descripción", inputDesc);
        
        System.out.println("Valor del producto: ");
        String inputAmount = Utils.getReader().nextLine();
        double amount = Utils.getValidator().isValidDouble("Precio", inputAmount);
        
        System.out.println("Ingrese cantidad del producto: ");
        String inputItem = Utils.getReader().nextLine();
        int item = Utils.getValidator().isValidInteger("Cantidad", inputItem);
        
        InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();
        invoiceDetailDto.setAmount(amount);
        invoiceDetailDto.setDescription(desc);
        invoiceDetailDto.setItem(item);
        
        double totalInvoice = amount * item;
        Calendar calendar = Calendar.getInstance();
        Timestamp creationDate = new Timestamp(calendar.getTimeInMillis());
        PartnerDto currentPartner = this.service.getCurrentPartner();
        
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setAmount(totalInvoice);
        invoiceDto.setCreationDate(creationDate);
        invoiceDto.setStatus(InvoiceStatus.PENDING);
        invoiceDto.setPartnerId(currentPartner);
        
        invoiceDetailDto.setInvoiceId(invoiceDto);
        
        this.service.createInvoice(invoiceDetailDto);
    }
}