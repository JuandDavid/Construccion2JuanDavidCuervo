package App.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter

@Entity
@Table (name= "Guest")
    public class Guest {
   
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "Id")

    private long id;
    @JoinColumn (name = "UserId")
    @OneToOne

    private User userId;
    @JoinColumn (name = "PartnerId")
    @ManyToOne

    private Partner partnerId;
    @Column (name = "Status")

    private String status;
}