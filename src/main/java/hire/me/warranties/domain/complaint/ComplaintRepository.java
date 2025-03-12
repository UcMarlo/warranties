package hire.me.warranties.domain.complaint;

import hire.me.warranties.domain.complaint.idendifiers.ComplaintId;
import hire.me.warranties.domain.complaint.idendifiers.ProductId;
import hire.me.warranties.domain.complaint.idendifiers.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComplaintRepository extends JpaRepository<Complaint, ComplaintId> {

    Optional<Complaint> findByProductIdAndReporterId(ProductId productId, UserId reporterId);

}
