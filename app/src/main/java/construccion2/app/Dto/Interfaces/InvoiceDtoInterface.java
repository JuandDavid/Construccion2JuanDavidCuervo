package app.Dto;

import java.sql.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@NoArgsConstructor
@Setter

public class InvoiceDtoInterface{
    private long id;
    private PersonDto personId;
    private MemberDto memberId;
    private Date creationDate;
    private double amount;
    private String status; 
    private List<InvoiceDetailDto> details;
}