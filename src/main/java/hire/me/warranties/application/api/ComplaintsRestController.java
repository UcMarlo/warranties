package hire.me.warranties.application.api;

import hire.me.warranties.application.api.dto.request.CreateComplaintRequest;
import hire.me.warranties.application.api.dto.request.UpdateComplaintContentRequest;
import hire.me.warranties.application.api.dto.response.ComplaintMetadataResponse;
import hire.me.warranties.application.api.dto.response.CompliantDetailsResponse;
import hire.me.warranties.application.api.dto.response.CreateComplaintResponse;
import hire.me.warranties.application.service.ComplaintService;
import hire.me.warranties.domain.complaint.idendifiers.ComplaintId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/list")
    public ResponseEntity<Page<ComplaintMetadataResponse>> getComplaints(Pageable pageable) {
        return ResponseEntity.ok(complaintService.getAllComplaints(pageable)
                .map(ComplaintMetadataResponse::toComplaintMetadataResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComplaint(@PathVariable String id, @RequestBody UpdateComplaintContentRequest updateComplaintContentRequest) {
        complaintService.updateComplaint(
                new ComplaintId(id),
                updateComplaintContentRequest.getContent());

        return ResponseEntity.noContent().build();
    }

}
