package kutuphane.kutuphane_otomasyonu.model;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import jakarta.persistence.Table;

@Entity //Bu sınıfın bir tablo olduğunu veritabanına söyler
@Table(name = "book") //Tablonun adını belirler

public class Book {
@GeneratedValue(strategy = GenerationType.IDENTITY) //PK numarasını otomatik olarak artıracak 1-2-3...
@Id  //Tablonun birincil anahtarının bu alan olduğunu söyler
private Long books_ID; //Bu tablonun pk değeri
private String title; 
private int isbn;
private int available_copies; //Kitabın kaç kopyası var
private int publication_year; //yayın yılı
 
////Bir kitap birden fazla yazar tutabiliri sağlayacak
// 2. KİTAP - KATEGORİ İLİŞKİSİ (Basit @ManyToMany Yöntemi)
    // Spring Boot "book_categories" tablosunu OTOMATİK oluşturacak.
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "book_categories", // Oluşacak ara tablonun adı
        joinColumns = @JoinColumn(name = "book_id"), // Bizim ID'miz
        inverseJoinColumns = @JoinColumn(name = "category_id") // Karşı tarafın ID'si
    )
    private Set<Category> categories = new HashSet<>();

    // Getter ve Setter'ı unutma:
    public Set<Category> getCategories() {
        return categories;
    }
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "book_authors", // Ara tablo adı
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    // Getter ve Setter'ı unutma:
    public Set<Author> getAuthors() {
        return authors;
    }
public Long getBookId() {
    return books_ID;
}
public void setBookId(Long books_ID) {
    this.books_ID = books_ID;
}
public String getTitle() {
    return title;
}
public void setTitle(String title) {
    this.title = title;
}
public int getIsbn() {
    return isbn;
}
public void setIsbn(int isbn) {
    this.isbn = isbn;
}
public int getAvailable_copies() {
    return available_copies;
}
public void setAvailable_copies(int available_copies) {
    this.available_copies = available_copies;
}
public int getPublication_year() {
    return publication_year;
}
public void setPublication_year(int publication_year) {
    this.publication_year = publication_year;
}
public void setAuthors(Set<Author> kaliciYazarlar) {
    this.authors = kaliciYazarlar;
}
}
