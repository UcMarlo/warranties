package hire.me.warranties.domain.complaint;

import hire.me.warranties.domain.idendifiers.ComplaintId;
import hire.me.warranties.domain.idendifiers.ProductId;
import hire.me.warranties.domain.idendifiers.UserId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "complaints")
@Getter
@ToString
@NoArgsConstructor
public class Complaint {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id", nullable = false))
    private ComplaintId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "product_id", nullable = false))
    private ProductId productId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "reporter_id", nullable = false))
    private UserId reporterId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "content", nullable = false))
    private ComplaintContent content;

    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(nullable = false)
    private String country;

    @Column(name = "report_count", nullable = false)
    private int reportCount;

    public Complaint(
            ProductId productId,
            ComplaintContent content,
            UserId reporterId,
            String country
    ) {
        this.id = new ComplaintId();
        this.productId = requireNonNullArgument(productId, "Product ID cannot be null");
        this.content = requireNonNullArgument(content, "Content cannot be null");
        this.reporterId = requireNonNullArgument(reporterId, "ReporterId cannot be null");
        this.country = requireNonNullArgument(country, "Country cannot be null");
        this.creationDate = ZonedDateTime.now();
        this.reportCount = 1;
    }

    public void updateCompliant(ComplaintContent content){
        this.content = content;
    }

    public void incrementReportCount() {
        this.reportCount++;
    }

    private <T> T requireNonNullArgument(T obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
        return obj;
    }
}
