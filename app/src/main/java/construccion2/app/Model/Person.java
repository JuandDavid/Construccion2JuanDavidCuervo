package app.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table (name = "Person")
    public class Person {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "Id")

    private long id;
    @Column (name = "Document")

    private long document;
    @Column (name = "Name")

    private String name;
    @Column (name = "Cellphone")

    private long cellphone;
}