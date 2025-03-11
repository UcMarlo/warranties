package hire.me.warranties.domain;

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
        this.productId = Objects.requireNonNull(productId, "Product ID cannot be null");
        this.content = Objects.requireNonNull(content, "Content cannot be null");
        this.creationDate = ZonedDateTime.now();
        this.reporterId = Objects.requireNonNull(reporterId, "reporterId cannot be null");
        this.country = Objects.requireNonNull(country, "Country cannot be null");
        this.reportCount = 1;
    }

    public void incrementReportCount() {
        this.reportCount++;
    }
}
