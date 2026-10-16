package br.com.restauranteapp.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrueOrderByNameAsc();
    List<Product> findByActiveTrueAndCategoryIgnoreCaseOrderByNameAsc(String category);
}
