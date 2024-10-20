package app.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table (name = "User")
    public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "Id")

    private long id;
    @JoinColumn (name = "PersonId")
    @OneToOne

    private Person personnId;
    @Column (name = "UserName")

    private String userName;
    @Column (name = "Password")

    private String password;
    @Column (name = "Role")

    private String role;
}