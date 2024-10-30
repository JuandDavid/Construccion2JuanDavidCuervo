package app.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import app.Controller.Utils;
import app.Controller.Valitators.InvoiceDetailValidator;
import app.Dto.Interfaces.InvoiceDetailDtoInterface;

import app.Model.Invoice;

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
        System.out.println("Ingrese la descrpción del detalle");
        String invoiceDetailDesctiptionDto = Utils.getReader().nextLine();
        this.invoiceDetailValidator.validDescription( invoiceDetailDesctiptionDto );
        this.description = invoiceDetailDesctiptionDto ;
    }

    @Override
    public void getInvoiceDetailAmountDto() throws Exception {
        System.out.println("Ingrese el monto del detalle");
        String invoiceDetailAmountDto = Utils.getReader().nextLine();
        this.amount = this.invoiceDetailValidator.validAmount( invoiceDetailAmountDto ) ;
    }
}