package br.com.restauranteapp.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "customer_orders")
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String customerName;
    @Column(nullable = false, length = 180)
    private String customerEmail;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.RECEIVED;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    protected CustomerOrder() {}
    public CustomerOrder(String customerName, String customerEmail) { this.customerName = customerName; this.customerEmail = customerEmail; }
    public void addItem(OrderItem item) { items.add(item); item.setOrder(this); }
    public void setTotal(BigDecimal total) { this.total = total; }
    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public OrderStatus getStatus() { return status; }
    public BigDecimal getTotal() { return total; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<OrderItem> getItems() { return items; }
}
