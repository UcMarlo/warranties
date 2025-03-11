package hire.me.warranties.domain.idendifiers;

import jakarta.persistence.Embeddable;
import lombok.Value;

import java.util.UUID;

@Embeddable
@Value
public class ComplaintId {
    private final UUID value;

    public ComplaintId() {
        this.value = UUID.randomUUID();
    }

    public ComplaintId(String value) {
        this.value = UUID.fromString(value);
    }
}
