package App.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import App.Controllers.Utils;
import App.Controllers.Validator.InvoiceDetailValidator;
import App.Dto.Interfaces.InvoiceDetailDtoInterface;

import App.Model.Invoice;

@Getter
@Setter
@NoArgsConstructor

public class InvoiceDetailDto implements InvoiceDetailDtoInterface{
    private final InvoiceDetailValidator invoiceDetailValidator = new InvoiceDetailValidator();

    private long id;
    private Invoice invoiceId;
    private int item;
    private String description;
    private double amount;

    @Override
    public void getInvoiceDetailDescriptionDto() throws Exception {
        System.out.println("Ingrese descrpcion del detalle");
        String invoiceDetailDesctiptionDto = Utils.getReader().nextLine();
        this.invoiceDetailValidator.validDescription( invoiceDetailDesctiptionDto );
        this.description = invoiceDetailDesctiptionDto ;
    }

    @Override
    public void getInvoiceDetailAmountDto() throws Exception {
        System.out.println("Ingrese valor del detalle");
        String invoiceDetailAmountDto = Utils.getReader().nextLine();
        this.amount = this.invoiceDetailValidator.validAmount( invoiceDetailAmountDto ) ;
    }
}