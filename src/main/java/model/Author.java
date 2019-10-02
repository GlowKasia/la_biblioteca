package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author implements IBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;

    private LocalDate dateOfBirth;

    // Z powodu relacji ManyToMany mamy tabelę pośredniczącą o nazwie Author_Book.
    // zapytanie musi dotyczyć tabeli pośredniczącej i zliczać wystąpienia autora w tej tabeli
    @Formula("(select count(*) from Author_Book ab where ab.authors_id = id")
    private Long numberOfBooks;

    // możemy z tej strony dodawać (książki do autorów) żeby tworzyć relacje
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<>();

    public Author(String name, String surname, LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }
}
