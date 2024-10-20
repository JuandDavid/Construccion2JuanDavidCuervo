package app.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter

@Entity
@Table (name = "Partner")
    public class Partner {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "Id")

    private long id;
    @JoinColumn (name = "Userid")
    @OneToOne

    private User userId;
    @Column (name = "Amount")

    private double amount;
    @Column (name = "Type")

    private String type;
    @Column (name = "CreationDate")

    private Timestamp creationDate;  
}