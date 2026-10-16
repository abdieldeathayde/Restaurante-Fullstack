package br.com.restauranteapp.product;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "products", indexes = @Index(name = "idx_product_category", columnList = "category"))
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false, length = 120)
    private String name;
    @NotBlank @Column(nullable = false, length = 600)
    private String description;
    @NotNull @DecimalMin("0.00") @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @NotBlank @Column(nullable = false, length = 60)
    private String category;
    @Column(length = 1000)
    private String imageUrl;
    @Column(nullable = false)
    private boolean active = true;

    protected Product() {}
    public Product(String name, String description, BigDecimal price, String category, String imageUrl) {
        this.name = name; this.description = description; this.price = price; this.category = category; this.imageUrl = imageUrl; this.active = true;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }
    public boolean isActive() { return active; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setActive(boolean active) { this.active = active; }
}
