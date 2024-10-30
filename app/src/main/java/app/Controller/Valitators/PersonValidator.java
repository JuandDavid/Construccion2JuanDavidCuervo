package app.Controller.Valitators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class PersonValidator extends CommonsValidator {	
    public void validName(String personName) throws Exception{
        super.isValidString("el nombre de la persona ", personName);
    }

    public long validDocument(String document) throws Exception{
        return super.isValidLong("la cédula de la persona ", document);
    }

    public long validCellPhone(String cellPhone) throws Exception{
        return super.isValidLong("el numero de celular ", cellPhone);
    }
}