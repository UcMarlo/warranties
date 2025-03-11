package hire.me.warranties.domain.idendifiers;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Embeddable
@Value
public class ProductId {
    private final UUID value;

    public ProductId(String value) {
        this.value = UUID.fromString(value);
    }
}
