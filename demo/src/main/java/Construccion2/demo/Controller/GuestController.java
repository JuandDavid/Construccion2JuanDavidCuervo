package app.controller;

public class GuestController implements ControllerInterface{
    private static final String MENU = "Ingrese una opcion: \n 1. Registrar consumo. "
                + "\n 2. Solicitud promocion de estado de socio "
                + "\n 3. Salir \n";

    public GuestController(){
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
                System.out.println("Registrar consumo"); 
                return true;
            }
            case "2": {
                System.out.println("Solicitud promocion de estado de socio"); 
            }
            case "3": {
                System.out.println("Finalizado");
                return false;
            }
            default: {
                System.out.println("Opción no válida");
                return true;
            }
        }
    }
}