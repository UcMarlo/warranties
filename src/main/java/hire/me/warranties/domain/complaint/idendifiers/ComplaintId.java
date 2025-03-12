package hire.me.warranties.domain.complaint.idendifiers;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
@EqualsAndHashCode
public class ComplaintId {
    private final UUID value;

    public ComplaintId() {
        this.value = UUID.randomUUID();
    }

    public ComplaintId(String value) {
        this.value = UUID.fromString(value);
    }
}
