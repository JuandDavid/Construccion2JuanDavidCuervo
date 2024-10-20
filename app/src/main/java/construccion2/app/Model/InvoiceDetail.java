package app.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter

@Entity
@Table (name = "InvoiceDetail")

    public class InvoiceDetail{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "Id")

    private long id;
    @JoinColumn (name = "InvoiceId")
    @ManyToOne
    
    private Invoice invoiceId;
    @Column (name = "Item")
    
    private int item;
    @Column (name = "Description")

    private String description;
    @Column (name = "Amount")

    private double amount;
}