package model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

//- dodawanie klientów,
//- listowanie klientow
//- usuwanie klientow
//- modyfikacja imienia/nazwiska lub numeru id klienta
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client implements IBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;

    @Column(unique = true, nullable = false)
    private String idNumber;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<BookLent> lents;

    public Client(String name, String surname, String idNumber) {
        this.name = name;
        this.surname = surname;
        this.idNumber = idNumber;
    }
}
