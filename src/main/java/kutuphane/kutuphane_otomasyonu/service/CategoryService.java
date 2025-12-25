package kutuphane.kutuphane_otomasyonu.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import kutuphane.kutuphane_otomasyonu.Repository.CategoryRepostory;
import kutuphane.kutuphane_otomasyonu.model.Category;
@Service
public class CategoryService {
@Autowired

private CategoryRepostory categoryRepostory;
// Tüm kategorileri getirme işlemi
public List<Category> getAllCategories() {
    return categoryRepostory.findAll();
}
// Yeni kategori ekleme işlemi
public Category addCategory(Category category) {
    return categoryRepostory.save(category);
}
// ID'ye göre kategori getirme işlemi
public Category getCategoryById(Long id) {
    return categoryRepostory.findById(id).orElse(null);
}
// Kategori silme işlemi
public void deleteCategory(Long id) {
    categoryRepostory.deleteById(id);       
}
// Kategori güncelleme işlemi
public Category updateCategory(Long id, Category updatedCategory) {
    // map ile mevcut kategoriyi bul ve güncelle
    return categoryRepostory.findById(id).map(category -> {
        category.setName(updatedCategory.getName());
        return categoryRepostory.save(category);
    }).orElse(null);
}
}