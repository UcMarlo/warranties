package hire.me.warranties.application.api;

import hire.me.warranties.application.ComplaintService;
import hire.me.warranties.application.api.dto.CompliantDetailsResponse;
import hire.me.warranties.application.api.dto.CreateComplaintRequest;
import hire.me.warranties.application.api.dto.CreateComplaintResponse;
import hire.me.warranties.domain.idendifiers.ComplaintId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/complaint")
@RequiredArgsConstructor
public class ComplaintsRestController {
    private final ComplaintService complaintService;

    @PostMapping()
    public ResponseEntity<CreateComplaintResponse> createNewCompliant(@RequestBody CreateComplaintRequest createComplaintRequest){
        return ResponseEntity.ok(CreateComplaintResponse.fromCompliantId(
                complaintService.registerNewComplaint(createComplaintRequest.toCommand())
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompliantDetailsResponse> getComplaintDetails(@PathVariable String id) {
        return ResponseEntity.ok(CompliantDetailsResponse.fromComplaint(
                complaintService.findCompliantByComplaintId(new ComplaintId(id))
        ));
    }

    //TODO: pageable list of complaints with searching by userId
}
