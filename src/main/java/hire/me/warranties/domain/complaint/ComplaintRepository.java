package hire.me.warranties.domain.complaint;

import hire.me.warranties.domain.complaint.idendifiers.ComplaintId;
import hire.me.warranties.domain.complaint.idendifiers.ProductId;
import hire.me.warranties.domain.complaint.idendifiers.UserId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ComplaintRepository extends CrudRepository<Complaint, ComplaintId> {

    Optional<Complaint> findByProductIdAndReporterId(ProductId productId, UserId reporterId);

}
