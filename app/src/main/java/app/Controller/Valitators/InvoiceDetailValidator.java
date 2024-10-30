package app.Controller.Valitators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class InvoiceDetailValidator extends CommonsValidator{
    public double validAmount(String amount) throws Exception{
        return super.isValidDouble("el monto del detalle ", amount);
    }

    public void validDescription(String description) throws Exception {
        super.isValidString("la descripción ", description);
    }
}