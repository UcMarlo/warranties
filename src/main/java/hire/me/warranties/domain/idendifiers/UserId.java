package hire.me.warranties.domain.idendifiers;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class UserId {
    private UUID value;

    public UserId(String value) {
        this.value = UUID.fromString(value);
    }
}

