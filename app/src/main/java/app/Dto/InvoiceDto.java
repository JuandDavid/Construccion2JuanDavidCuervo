package app.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import app.Model.Partner;
import app.Model.Person;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceDto {
    private long id;
    private Person personId;
    private Partner partnerId;
    private Timestamp creationDate;
    private double amount;
    private String status;
}