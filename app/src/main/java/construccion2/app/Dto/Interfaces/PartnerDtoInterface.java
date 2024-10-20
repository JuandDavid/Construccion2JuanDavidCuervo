package app.Dto;

import java.sql.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@NoArgsConstructor
@Setter

public class PartnerDtoInterface{
    private long id;
    private Long userId;
    private double amount;
    private String type;
    private Date creationDate;
}