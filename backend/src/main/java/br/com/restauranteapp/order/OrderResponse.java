package br.com.restauranteapp.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long id, String customerName, String customerEmail, OrderStatus status,
                            BigDecimal total, LocalDateTime createdAt, List<ItemResponse> items) {
    public record ItemResponse(Long productId, String productName, int quantity, BigDecimal unitPrice, BigDecimal subtotal) {}
    public static OrderResponse from(CustomerOrder order) {
        return new OrderResponse(order.getId(), order.getCustomerName(), order.getCustomerEmail(),
                order.getStatus(), order.getTotal(), order.getCreatedAt(),
                order.getItems().stream().map(item -> new ItemResponse(item.getProductId(), item.getProductName(),
                        item.getQuantity(), item.getUnitPrice(), item.getSubtotal())).toList());
    }
}
