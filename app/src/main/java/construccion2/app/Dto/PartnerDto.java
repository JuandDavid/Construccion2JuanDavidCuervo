package App.Dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import App.Controllers.Utils;
import App.Dto.Interfaces.PartnerDtoInterface;

import App.Controllers.Validator.PartnerValidator;
import App.Model.User;

@Getter
@Setter
@NoArgsConstructor
public class PartnerDto implements PartnerDtoInterface{
    private final PartnerValidator partnerValidator = new PartnerValidator();

    private long id;
    private User userId;
    private double amount;
    private String type;
    private Timestamp creationDate;

    @Override
    public void getPartnerTypeDto() throws Exception {
        String partnerTypeDto = "";
        boolean continueRead = true;
        while ( continueRead ){
            System.out.println("Ingrese el tipo de socio \n 1. REGULAR \n 2. VIP \n 4. CANCELAR \n");
            partnerTypeDto = Utils.getReader().nextLine();
            
            switch ( partnerTypeDto ){
                case "1": {
                    partnerTypeDto = "REGULAR";
                    continueRead = false;
                    break;
                }
                case "2": {
                    partnerTypeDto = "VIP";
                    continueRead = false;
                    break;
                }
                case "4": {
                    partnerTypeDto = "";
                    continueRead = false;
                    break;
                }
                default: {
                    System.out.println("Ingrese una opcion valida");
                }
            }            
        }
        
        this.partnerValidator.validType( partnerTypeDto );
        
        this.type = partnerTypeDto;
    }

    @Override
    public void getPartnerAmountDto() throws Exception {
        System.out.println("Ingrese el monto de inversión");
        String partnerAmountDto = Utils.getReader().nextLine();
        this.amount = partnerValidator.validAmount( partnerAmountDto );
    }
    
    @Override
    public void getPartnerAmountIncraseDto() throws Exception {
        System.out.println("Ingrese el monto a AUMENTAR la inversión");
        String partnerAmountDto = Utils.getReader().nextLine();
        this.amount = partnerValidator.validAmount( partnerAmountDto );
    }    
}
