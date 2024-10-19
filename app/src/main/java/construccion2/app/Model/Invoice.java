package App.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter

@Entity
@Table ( name = "Invoice")
    public class Invoice {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "Id")

    private long id;
    @JoinColumn (name = "Personid")
    @ManyToOne

    private Person personId;
    @JoinColumn (name = "PartnerId")
    @ManyToOne

    private Partner partnerId;
    @Column (name = "CreationDate")

    private Timestamp creationDate;
    @Column (name = "Amount")

    private double amount;
    @Column (name = "Status")

    private String status;
}