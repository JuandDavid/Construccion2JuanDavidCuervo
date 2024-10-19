package App.Controller.Validator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Setter
@Component

public class InvoiceDetailValidator extends CommonsValidator{
    public double validAmount(String amount) throws Exception{
        return super.isValidDouble("Valor del item ", amount);
    }

    public void validDescription(String description) throws Exception {
        super.isValidString("la descripción ", description);
    }
}