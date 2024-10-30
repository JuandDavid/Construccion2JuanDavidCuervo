package app.Controller.Valitators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class PartnerValidator extends CommonsValidator{
    public double validAmount(String amount) throws Exception{
        return super.isValidDouble("el monto de inversión ", amount);
    }

    public void validType(String tipe) throws Exception {
        super.isValidString("el tipo de socio ", tipe);
    }
}