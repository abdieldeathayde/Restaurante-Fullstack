package br.com.restauranteapp.seed;

import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import br.com.restauranteapp.product.Product;
import br.com.restauranteapp.product.ProductRepository;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedProducts(ProductRepository products) {
        return args -> {
            if (products.count() > 0) return;
            products.save(new Product("Burger da Casa", "Blend artesanal, cheddar, alface fresca e molho especial.", new BigDecimal("32.90"), "Hambúrgueres", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=900&q=85"));
            products.save(new Product("Pizza Margherita", "Tomate, mozzarella, manjericão e massa de fermentação lenta.", new BigDecimal("44.90"), "Pizzas", "https://images.unsplash.com/photo-1579751626657-72bc17010498?auto=format&fit=crop&w=900&q=85"));
            products.save(new Product("Pasta ao Pesto", "Massa al dente com pesto de manjericão e parmesão.", new BigDecimal("36.50"), "Massas", "https://images.unsplash.com/photo-1473093295043-cdd812d0e601?auto=format&fit=crop&w=900&q=85"));
            products.save(new Product("Burger BBQ", "Hambúrguer artesanal, cebola caramelizada, bacon e barbecue.", new BigDecimal("35.90"), "Hambúrgueres", "https://images.unsplash.com/photo-1553979459-d2229ba7433a?auto=format&fit=crop&w=900&q=85"));
            products.save(new Product("Pizza Pepperoni", "Molho da casa, mozzarella cremosa e pepperoni levemente picante.", new BigDecimal("49.90"), "Pizzas", "https://images.unsplash.com/photo-1628840042765-356cda07504e?auto=format&fit=crop&w=900&q=85"));
            products.save(new Product("Limonada da Casa", "Limão espremido na hora, hortelã e gelo.", new BigDecimal("9.90"), "Bebidas", "https://images.unsplash.com/photo-1513558161293-cdaf765edfd7?auto=format&fit=crop&w=900&q=85"));
        };
    }
}
