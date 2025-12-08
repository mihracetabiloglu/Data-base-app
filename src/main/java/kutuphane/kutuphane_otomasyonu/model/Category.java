package kutuphane.kutuphane_otomasyonu.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity; // Bu sınıfın bir veritabanı varlığı (tablosu) olduğunu belirtmek için
import jakarta.persistence.GeneratedValue; // ID'lerin nasıl üretileceğini (örn: otomatik artan) belirlemek için
import jakarta.persistence.GenerationType; // Otomatik artan (IDENTITY) gibi stratejileri seçmek için
import jakarta.persistence.Id; // Hangi alanın Birincil Anahtar (Primary Key) olduğunu belirtmek için
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "categories")
public class Category {


@Id // 3. Bu, Primary Key (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
public Category(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
   // "mappedBy", ilişkinin sahibinin "Book" sınıfındaki "categories" alanı olduğunu söyler.
    @ManyToMany(mappedBy = "categories")
    private Set<Book> books = new HashSet<>();

    // Getter ve Setter
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
    

    public Category() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
