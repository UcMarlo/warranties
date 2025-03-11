package hire.me.warranties.domain.command;

import hire.me.warranties.domain.idendifiers.ProductId;
import hire.me.warranties.domain.idendifiers.UserId;
import lombok.Value;

@Value
public class CreateNewComplaintCommand {
    ProductId productId;
    UserId userId;
    String description;
    String createdFromIp;
}
