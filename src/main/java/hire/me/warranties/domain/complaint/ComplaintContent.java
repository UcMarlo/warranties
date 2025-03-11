package hire.me.warranties.domain.complaint;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class ComplaintContent {
    @Column(nullable = false, length = 4000)
    private String value;

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
