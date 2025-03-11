package hire.me.warranties.domain.idendifiers;

import jakarta.persistence.Embeddable;
import lombok.Value;

import java.util.UUID;

@Embeddable
@Value
public class UserId {
    private final UUID value;

    public UserId(String value) {
        this.value = UUID.fromString(value);
    }
}

