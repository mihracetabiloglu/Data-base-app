package kutuphane.kutuphane_otomasyonu.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity; // Bu sınıfın bir veritabanı varlığı (tablosu) olduğunu belirtmek için
import jakarta.persistence.GeneratedValue; // ID'lerin nasıl üretileceğini (örn: otomatik artan) belirlemek için
import jakarta.persistence.GenerationType; // Otomatik artan (IDENTITY) gibi stratejileri seçmek için
import jakarta.persistence.Id; // Hangi alanın Birincil Anahtar (Primary Key) olduğunu belirtmek için
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "authors")
public class Author {

@Id // Bu alan Primary Key (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID'nin otomatik artan olmasını sağlar
private Long author_ID;
private String name;
private String biography;
public Author(Long author_ID, String name, String biography) {
    this.author_ID = author_ID;
    this.name = name;
    this.biography = biography;
}

public Author() {
}
@ManyToMany(mappedBy = "authors")
@JsonIgnore
    private Set<Book> books = new HashSet<>();

    // Getter ve Setter
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

public Long getAuthor_ID() {
    return author_ID;
}
public void setAuthor_ID(Long author_ID) {
    this.author_ID = author_ID;
}
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public String getBiography() {
    return biography;
}
public void setBiography(String biography) {
    this.biography = biography;
}






}
