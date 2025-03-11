package hire.me.warranties.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Value;

@Embeddable
@Value
public class ComplaintContent {
    @Column(nullable = false, length = 4000)
    private final String value;

    public ComplaintContent(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Content cannot be blank");
        }
        if (value.length() > 4000) {
            throw new IllegalArgumentException("Content cannot exceed 4000 characters");
        }
        this.value = value;
    }
}
