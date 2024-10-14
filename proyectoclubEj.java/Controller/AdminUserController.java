package App.Controller;

import app.Dto.UserDto;
import app.service.Interface.UserServiceInterface;
import java.util.Scanner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Getter
@NoArgsConstructor
@Setter
@Controller

public class AdminUserController implements ControllerInterface {
    
    private static final String MENU = "Gestion de usuario\n" +
                                        "1. Crear usuario\n" +
                                        "2. Actualizar usuario\n" +
                                        "3. Eliminar usuario\n" +
                                        "4. Cerrar sesión.";

 @Autowired
    private UserServiceInterface userService;
 
 private Scanner scanner = new Scanner(System.in);
    
    
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
            case "1":
               
                return createUser();
                
            case "2":
               
                return updateUser();
            case "3":
               
                return deleteUser();
            case "4":
                System.out.println("Sesion finalizada");
                return false;
            default:
                System.out.println("Opción invalida");
                return true;
        }
    }
    
    private boolean createUser() {
        Scanner scanner = new Scanner(System.in);
        UserDto newUser = new UserDto();

        try {
            System.out.println("Ingrese id de usuario:");
            newUser.setId(Long.valueOf(scanner.nextLine()));

            System.out.println("Ingrese nombre de usuario:");
            newUser.setUserName(scanner.nextLine());

            System.out.println("Ingrese contraseña:");
            newUser.setPassword(scanner.nextLine());

            System.out.println("Ingrese rol:");
            newUser.setRole(scanner.nextLine());

            userService.createUser(newUser);
            System.out.println("Usuario creado correctamente");
        } catch (Exception e) {
            System.out.println("Error al crear usuario " + e.getMessage());
        }

        return true;
    }

    private boolean updateUser() {
        Scanner scanner = new Scanner(System.in);
        UserDto updatedUser = new UserDto();

        try {
            System.out.println("Ingrese id de usuario a actualizar:");
            updatedUser.setId(Long.valueOf(scanner.nextLine()));

            System.out.println("Ingrese nuevo nombre de usuario:");
            updatedUser.setUserName(scanner.nextLine());

            System.out.println("Ingrese nueva contraseña:");
            updatedUser.setPassword(scanner.nextLine());

            System.out.println("Ingrese nuevo rol:");
            updatedUser.setRole(scanner.nextLine());

            userService.updateUser(updatedUser);
            System.out.println("Usuario actualizado correctamente");
        } catch (Exception e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        }

        return true;
    }

    private boolean deleteUser() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Ingrese id de usuario a eliminar:");
            long id = Long.parseLong(scanner.nextLine());

            userService.deleteUser(id);
            System.out.println("Usuario eliminado correctamente");
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }

        return true;
    }
    
}