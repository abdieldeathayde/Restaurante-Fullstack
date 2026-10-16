package br.com.restauranteapp.order;

import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import br.com.restauranteapp.product.Product;
import br.com.restauranteapp.product.ProductService;

@Service
public class OrderService {
    private final OrderRepository orders;
    private final ProductService products;
    public OrderService(OrderRepository orders, ProductService products) { this.orders = orders; this.products = products; }

    @Transactional
    public OrderResponse create(CreateOrderRequest request) {
        CustomerOrder order = new CustomerOrder(request.customerName().trim(), request.customerEmail().trim().toLowerCase());
        BigDecimal total = BigDecimal.ZERO;
        for (CreateOrderRequest.OrderLineRequest line : request.items()) {
            Product product = products.getActive(line.productId());
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(line.quantity()));
            total = total.add(subtotal);
            order.addItem(new OrderItem(product.getId(), product.getName(), line.quantity(), product.getPrice()));
        }
        order.setTotal(total);
        return OrderResponse.from(orders.save(order));
    }

    @Transactional(readOnly = true)
    public OrderResponse get(Long id) {
        CustomerOrder order = orders.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
        return OrderResponse.from(order);
    }
}
