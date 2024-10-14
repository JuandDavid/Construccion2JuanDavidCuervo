package App;

import App.Controllers.LoginController;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Setter
@SpringBootApplication

    public class AppClubApplication implements CommandLineRunner {
    @Autowired
    LoginController controller;
    

    public static void main(String[] args) {
            SpringApplication.run(AppClubApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            controller.session();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}