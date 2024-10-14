package App.Service.Intefaces;

import App.Dto.UserDto;

public interface LoginServiceInterface {
    public void login(UserDto userDto) throws Exception;
    public void logout();    
}