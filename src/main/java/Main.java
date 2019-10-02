import dao.AuthorDao;
import dao.BookDao;
import dao.EntityDao;
import model.Author;
import model.Book;
import model.Client;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityDao dao = new EntityDao();
        parser(dao);
    }

    private static void parser(EntityDao dao) {
        String komenda;

        do {
            System.out.println("(utworz), (listuj), (usun), (modyfikuj), (dodajKsiazkeDoAutora), (), (quit)");
            komenda = scanner.nextLine();
            if (komenda.equalsIgnoreCase("utworz")) {
                create(dao);
            }
            if (komenda.equalsIgnoreCase("listuj")) {
                list(dao);
            }
            if (komenda.equalsIgnoreCase("usun")) {
                delete(dao);
            }
            if (komenda.equalsIgnoreCase("modyfikuj")) {
                modify(dao);
            }
            if (komenda.equalsIgnoreCase("dodajKdoA")) {
                addBookToAuthor();
            }
        } while (!komenda.equalsIgnoreCase("quit"));
    }

    private static void addBookToAuthor() {
        AuthorDao authorDao = new AuthorDao();
        System.out.println("Podaj id autora: ");
        Optional<Author> author = authorDao.getById(Long.valueOf(scanner.nextLine()));
        Author authorAddToBook;
        if (author.isPresent()) {
            authorAddToBook = author.get();
            authorAddToBook.getBooks().add(getBookById);
            authorDao.saveOrUpdate(authorAddToBook);
        }
        System.out.println("Dodano ksiazke do autora.");
    }

    private static Book getBookById() {
        Book bookToAdd = null;
        System.out.println("Podaj id ksiazki: ");
        Optional<Book> book = new BookDao().getById(Long.valueOf(scanner.nextLine()));
        if (book.isPresent()) {
            bookToAdd = book.get();
        }
        return bookToAdd;
    }


    private static void modify(EntityDao dao) {
        System.out.println("(autor) (ksiazka) (klient)");
        String update = scanner.nextLine();
        switch (update) {
            case "autor":
                System.out.println("Podaj id autora: ");
                Optional<Author> author = dao.getById(Author.class, Long.parseLong(scanner.nextLine()));

                if (author.isPresent()) {
                    Author autorToUpdate = author.get();
                    System.out.println("Podaj imię: ");
                    autorToUpdate.setName(scanner.nextLine());

                    System.out.println("Podaj nazwisko: ");
                    autorToUpdate.setSurname(scanner.nextLine());

                    System.out.println("Podaj datę urodzenia: (yyyy-mm-dd)");
                    autorToUpdate.setDateOfBirth(LocalDate.parse(scanner.nextLine()));

                    dao.saveOrUpdate(autorToUpdate);
                    System.out.println("Zaktualizowano dane.");
                }
                break;
            case "ksiazka":
                System.out.println("Podaj id autora: ");
                Optional<Book> book = dao.getById(Book.class, Long.parseLong(scanner.nextLine()));

                if (book.isPresent()) {
                    Book bookToUpdate = book.get();
                    System.out.println("Podaj tytuł: ");
                    bookToUpdate.setTitle(scanner.nextLine());

                    System.out.println("Podaj rok wydania: ");
                    bookToUpdate.setYearWritten(Integer.parseInt(scanner.nextLine()));

                    System.out.println("Podaj ilość stron: ");
                    bookToUpdate.setNumberOfPages(Integer.parseInt(scanner.nextLine()));

                    System.out.println("Podaj ilość kopii: ");
                    bookToUpdate.setNumberOfAvailableCopies(Integer.parseInt(scanner.nextLine()));

                    dao.saveOrUpdate(bookToUpdate);
                    System.out.println("Zaktualizowano dane.");
                }
                break;
            case "klient":
                System.out.println("Podaj id autora: ");
                Optional<Client> client = dao.getById(Client.class, Long.parseLong(scanner.nextLine()));

                if (client.isPresent()) {
                    Client clientToUpdate = client.get();
                    System.out.println("Podaj imię: ");
                    clientToUpdate.setName(scanner.nextLine());

                    System.out.println("Podaj nazwisko: ");
                    clientToUpdate.setSurname(scanner.nextLine());

                    System.out.println("Podaj numer id: ");
                    clientToUpdate.setIdNumber(scanner.nextLine());

                    dao.saveOrUpdate(clientToUpdate);
                    System.out.println("Zaktualizowano dane.");
                }
                break;
            default:
                System.err.println("Zły wybór. Wybierz: autor, książka, klient");
                break;
        }
    }
}
