package br.com.restauranteapp.product;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    @GetMapping
    public List<Product> list(@RequestParam(required = false) String category,
                              @RequestParam(required = false) String search) {
        return service.list(category, search);
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) { return service.getActive(id); }
}
