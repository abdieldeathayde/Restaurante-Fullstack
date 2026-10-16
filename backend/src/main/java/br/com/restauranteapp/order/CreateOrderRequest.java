package br.com.restauranteapp.order;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record CreateOrderRequest(
    @NotBlank @Size(max = 120) String customerName,
    @NotBlank @Email @Size(max = 180) String customerEmail,
    @NotEmpty @Size(max = 50) List<@Valid OrderLineRequest> items
) {
    public record OrderLineRequest(
        @NotNull @Positive Long productId,
        @Min(1) @Max(100) int quantity
    ) {}
}
