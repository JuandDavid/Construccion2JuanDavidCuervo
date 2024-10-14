package app.service;

import app.dao.GuestDao;
import app.dao.InvoiceDao;
import app.dao.PartnerDao;
import app.dao.PersonDao;
import app.dao.UserDao;
import app.dto.GuestDto;
import app.dto.InvoiceDetailDto;
import app.dto.InvoiceDto;
import app.dto.PartnerDto;
import app.dto.PersonDto;
import app.dto.UserDto;
import app.model.Role;
import app.model.SubscriptionType;
import java.sql.SQLException;
import java.util.List;

public class Service {
    private UserDao userDao;
    private PersonDao personDao;
    private InvoiceDao invoiceDao;
    private PartnerDao partnerDao;
    private GuestDao guestDao;
    public static UserDto user;
    
    public Service() {
        this.userDao = new UserDao();
        this.personDao = new PersonDao();
        this.invoiceDao = new InvoiceDao();
        this.partnerDao = new PartnerDao();
        this.guestDao = new GuestDao();
    }
    
    public void login(UserDto userDto) throws Exception {
        UserDto validateDto = userDao.findByUserName(userDto);
        if (validateDto == null) {
            throw new Exception("Usuario no encontrado");
        }
        if (!userDto.getPassword().equals(validateDto.getPassword())) {
            throw new Exception("Usuario o contraseña no valido");
        }
        userDto.setRole(validateDto.getRole());
        user = validateDto;
    }

    public void logout() {
        user = null;
        System.out.println("Sesion finalizada");
    }
    
    public void createPerson(PersonDto personDto) throws Exception {
        if (this.personDao.existsByDocument(personDto)) {
            throw new Exception("Documento existente");
        }
        try {
            this.personDao.createPerson(personDto);
        } catch (SQLException e) {
            throw new Exception("No se pudo crear " + e);
        }
    }
    
    public void createUser(UserDto userDto) throws Exception {
        this.createPerson(userDto.getPersonId());
        PersonDto personDto = personDao.findByDocument(userDto.getPersonId());
        userDto.setPersonId(personDto);
        long[] ids = {userDto.getPersonId().getId()};
        if (this.userDao.existsByUserName(userDto)) {
            this.personDao.deletePerson(ids);
            throw new Exception("Nombre de usuario existente");
        }
        try {
            this.userDao.createUser(userDto);
        } catch (SQLException e) {
            this.personDao.deletePerson(ids);
            throw new Exception("No se pudo crear " + e.getMessage());
        }
    }
    
    public void createPartner(PartnerDto partnerDto) throws Exception {
        this.createUser(partnerDto.getUserId());
        UserDto userDto = userDao.findByUserName(partnerDto.getUserId());
        partnerDto.setUserId(userDto);
        try {
            this.partnerDao.createPartner(partnerDto);
            System.out.println("Socio creado exitosamente");
        } catch (SQLException e) {
            long[] idsPerson = {userDto.getPersonId().getId()};
            long[] idsUser = {partnerDto.getUserId().getId()};
            this.personDao.deletePerson(idsPerson);
            this.userDao.deleteUser(idsUser);
            throw new Exception("No se pudo crear " + e.getMessage());
        }
        
    }
    
    public void createGuest(GuestDto guestDto) throws Exception {
        this.createUser(guestDto.getUserId());
        UserDto userDto = userDao.findByUserName(guestDto.getUserId());
        guestDto.setUserId(userDto);
        try {
            this.guestDao.createGuest(guestDto);
            System.out.println("Invitado creado exitosamente");
        } catch (SQLException e) {
            long[] idsPerson = {userDto.getPersonId().getId()};
            long[] idsUser = {guestDto.getUserId().getId()};
            this.personDao.deletePerson(idsPerson);
            this.userDao.deleteUser(idsUser);
            throw new Exception("No se pudo crear " + e);
        }
    }
    
    public List<InvoiceDto> getAllInvoices() throws Exception {
        try {
            return this.invoiceDao.getAllInvoices();
        } catch (SQLException e) {
            throw new Exception("No se encontraron datos de factura " + e);
        }
    }
    
    public List<InvoiceDto> getInvoicesByRole(Role role) throws Exception {
        try {
            return this.invoiceDao.getInvoicesByRole(role);
        } catch (SQLException e) {
            throw new Exception("No se encontraron datos de factura " + e);
        }
    }
    
    public List<PartnerDto> getPartnersByType(SubscriptionType type) throws Exception{
        try {
            return this.partnerDao.getPartnersByType(type);
        } catch (SQLException e) {
            throw new Exception("No se encontraron datos de suscripcion" + e);
        }
    }
    
    public void updatePartner(PartnerDto partnerDto) throws Exception{
        try {
            this.partnerDao.updatePartner(partnerDto);
        } catch (SQLException e) {
            throw new Exception("No se pudieron actualizar los datos del socio " + e);
        }
    }
    
    public PartnerDto getCurrentPartner() throws Exception{
        try {
            return this.partnerDao.getPartnerByUserId(user.getId());
        } catch (SQLException e) {
            throw new Exception("No se encontraron datos del socio " + e);
        }
    }
     
    public void deleteInvoicesByCurrentPartnerId() throws Exception {
        try {
            this.invoiceDao.deleteInvoicesByPartnerId(user.getId());
        } catch (SQLException e) {
            throw new Exception("No se pudieron eliminar las facturas del socio " + e);
        }
    }
    
    public void deleteGuestsByCurrentPartnerId() throws Exception {
        try {
            this.guestDao.deleteGuestsByPartnerId(user.getId());
        } catch (SQLException e) {
            throw new Exception("No se pudieron eliminar invitados del socio: " + e);
        }
    }
    
    public void deleteCurrentPartner() throws Exception {
        try {
            long[] ids = {user.getId()};
            this.partnerDao.deletePartner(ids);
        } catch (SQLException e) {
            throw new Exception("No se pudieron eliminar invitados del socio" + e);
        }
    }
    
    public List<InvoiceDto> getPendingInvoicesByCurrentPartnerId() throws Exception {
        try {
            return this.invoiceDao.getPendingInvoicesByPartnerId(user.getId());
        } catch (SQLException e) {
            throw new Exception("No se pudieron obtener facturas pendientes " + e);
        }
    }
    
    public void createInvoice(InvoiceDetailDto invoiceDetailDto) throws Exception {
        try {
            PersonDto personDto = new PersonDto();
            personDto.setId(user.getPersonId().getId());
            invoiceDetailDto.getInvoiceId().setPersonId(personDto);
            
            this.invoiceDao.createInvoice(invoiceDetailDto.getInvoiceId());
            this.invoiceDao.createInvoiceDetail(invoiceDetailDto);
            System.out.println("Factura creada");
        } catch (SQLException e) {
            throw new Exception("No se encontraron datos de factura " + e);
        }
    }
}