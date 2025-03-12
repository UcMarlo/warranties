package hire.me.warranties.domain.complaint.command;

import hire.me.warranties.domain.complaint.idendifiers.ProductId;
import hire.me.warranties.domain.complaint.idendifiers.UserId;
import lombok.Value;

@Value
public class CreateNewComplaintCommand {
    ProductId productId;
    UserId userId;
    String description;
    String createdFromIp;
}
