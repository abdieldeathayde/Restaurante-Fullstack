package br.com.restauranteapp.order;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {}
