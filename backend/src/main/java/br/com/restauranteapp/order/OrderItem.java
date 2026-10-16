package br.com.restauranteapp.order;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private CustomerOrder order;
    @Column(nullable = false)
    private Long productId;
    @Column(nullable = false, length = 120)
    private String productName;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
    protected OrderItem() {}
    public OrderItem(Long productId, String productName, int quantity, BigDecimal unitPrice) {
        this.productId = productId; this.productName = productName; this.quantity = quantity; this.unitPrice = unitPrice;
    }
    void setOrder(CustomerOrder order) { this.order = order; }
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getSubtotal() { return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
}
