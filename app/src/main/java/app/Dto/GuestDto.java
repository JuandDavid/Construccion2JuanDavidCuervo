package app.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import app.Dto.Interfaces.GuestDtoInterface;
import app.Model.Partner;
import app.Model.User;

@Getter
@Setter
@NoArgsConstructor
public class GuestDto implements GuestDtoInterface{
    private long id;
    private User userId;
    private Partner partnerId;
    private String status;
}