package hire.me.warranties.application;

import hire.me.warranties.domain.command.CreateNewComplaintCommand;
import hire.me.warranties.domain.complaint.Complaint;
import hire.me.warranties.domain.complaint.ComplaintContent;
import hire.me.warranties.domain.complaint.ComplaintRepository;
import hire.me.warranties.domain.exception.ComplaintNotFoundException;
import hire.me.warranties.domain.geolocation.IpGeolocationPort;
import hire.me.warranties.domain.idendifiers.ComplaintId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final IpGeolocationPort ipGeolocationPort;

    @Transactional
    public ComplaintId registerNewComplaint(CreateNewComplaintCommand command) {
        Optional<Complaint> optionalComplaint = complaintRepository.findByProductIdAndReporterId(
                command.getProductId(),
                command.getUserId()
        );

        if (optionalComplaint.isPresent()) {
            Complaint complaint = optionalComplaint.get();
            complaint.incrementReportCount();
            return complaint.getId();
        }
        String country = ipGeolocationPort.findCountryByIP(command.getCreatedFromIp()).getCountry();

        Complaint complaint = new Complaint(
                command.getProductId(),
                new ComplaintContent(command.getDescription()),
                command.getUserId(),
                country
        );

        complaintRepository.save(complaint);
        return complaint.getId();
    }

    @Transactional
    public Complaint findCompliantByComplaintId(ComplaintId complaintId){
        return complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint with ID: " + complaintId.getValue() + " cannot be found"));
    }
}
