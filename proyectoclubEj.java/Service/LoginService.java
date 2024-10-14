package App.Service;

import App.Service.Intefaces.LoginServiceInterface;
import App.Dao.UserDao;
import App.Dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@Setter

    @Service
    public class LoginService implements LoginServiceInterface{
    @Autowired
    private final UserDao userDao = new UserDao();
    
    public static UserDto user;

    @Override
    public void login(UserDto userDto) throws Exception {
        UserDto validateDto = this.userDao.findByUserName(userDto);
        if (validateDto == null) {
            throw new Exception("No existe usuario registrado");
        }
        if (!userDto.getPassword().equals(validateDto.getPassword())) {
            throw new Exception("Usuario/contraseña incorrecto");
        }
        userDto.setRole(validateDto.getRole());
        user = validateDto;
    }

    @Override
    public void logout() {
        user = null;
        System.out.println("Sesion Finalizada");
    }
    
}