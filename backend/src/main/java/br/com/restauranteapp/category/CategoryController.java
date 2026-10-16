package br.com.restauranteapp.category;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final JdbcTemplate jdbc;
    public CategoryController(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @GetMapping
    public List<String> list() {
        return jdbc.query("select distinct category from products where active = true order by category",
                (rs, rowNum) -> rs.getString("category"));
    }
}
