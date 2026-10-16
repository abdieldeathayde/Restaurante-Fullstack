package br.com.restauranteapp.product;

import java.util.List;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
    private final ProductRepository repository;
    public ProductService(ProductRepository repository) { this.repository = repository; }

    public List<Product> list(String category, String search) {
        List<Product> products = (category == null || category.isBlank())
                ? repository.findByActiveTrueOrderByNameAsc()
                : repository.findByActiveTrueAndCategoryIgnoreCaseOrderByNameAsc(category.trim());
        if (search == null || search.isBlank()) return products;
        String term = search.trim().toLowerCase(Locale.ROOT);
        return products.stream().filter(p ->
                p.getName().toLowerCase(Locale.ROOT).contains(term)
                || p.getDescription().toLowerCase(Locale.ROOT).contains(term)
                || p.getCategory().toLowerCase(Locale.ROOT).contains(term)).toList();
    }

    public Product getActive(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        if (!product.isActive()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        return product;
    }
}
