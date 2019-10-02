package model;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Set;

//- dodawanie książek,
//- listowanie ksiazek
//- usuwanie ksiazek
//- modyfikacja tytulu/roku napisania/ilosci stron w ksiazce
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book implements IBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private int yearWritten;
//    private DateFormat yearWritten;

    @Formula(value = "(year(now()) - yearWritten)")
    private int howOld;

    private int numberOfPages;
    private int numberOfAvailableCopies;

    @Formula(value = "(select count(*) from BookLent bl where bl.book_id = id and bl.dateReturned id null )")
    private int numberOfBorrowedCopies;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private Set<Author> authors;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<BookLent> currentLents;

    public Book(String title, int yearWritten, int numberOfPages, int numberOfAvailableCopies) {
        this.title = title;
        this.yearWritten = yearWritten;
        this.numberOfPages = numberOfPages;
        this.numberOfAvailableCopies = numberOfAvailableCopies;
    }
}
