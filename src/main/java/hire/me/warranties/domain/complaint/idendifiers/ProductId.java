package hire.me.warranties.domain.complaint.idendifiers;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductId {
    private UUID value;

    public ProductId(String value) {
        this.value = UUID.fromString(value);
    }
}
