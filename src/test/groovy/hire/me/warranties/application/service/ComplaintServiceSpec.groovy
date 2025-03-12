package hire.me.warranties.application.service

import hire.me.warranties.domain.complaint.ComplaintContent
import hire.me.warranties.domain.complaint.command.CreateNewComplaintCommand
import hire.me.warranties.domain.complaint.Complaint
import hire.me.warranties.domain.complaint.ComplaintRepository
import hire.me.warranties.domain.geolocation.GeolocationData
import hire.me.warranties.domain.geolocation.IpGeolocationPort
import hire.me.warranties.domain.complaint.idendifiers.ComplaintId
import hire.me.warranties.domain.complaint.idendifiers.ProductId
import hire.me.warranties.domain.complaint.exception.ComplaintNotFoundException
import hire.me.warranties.domain.complaint.idendifiers.UserId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification
import spock.lang.Subject

class ComplaintServiceSpec extends Specification {

    ComplaintRepository complaintRepository = Mock()
    IpGeolocationPort ipGeolocationPort = Mock()

    @Subject
    ComplaintService complaintService = new ComplaintService(complaintRepository, ipGeolocationPort)

    def "should register a new complaint if no existing complaint is found"() {
        given:
        def command = new CreateNewComplaintCommand(
                new ProductId("1e603af2-c005-45dd-bbc0-514923a5669b"),
                new UserId("6173a5e0-a63d-4328-b9fd-e9b72c1cd443"),
                "The product is defective",
                "192.168.1.1"
        )
        def country = "Poland"

        when:
        def complaintId = complaintService.registerNewComplaint(command)

        then:
        1 * complaintRepository.findByProductIdAndReporterId(command.getProductId(), command.getUserId()) >> Optional.empty()
        1 * ipGeolocationPort.getGeolocationDataByIp("192.168.1.1") >> new GeolocationData(country)
        1 * complaintRepository.save(_)

        complaintId != null
    }

    def "should increment report count if complaint already exists"() {
        given:
        def command = new CreateNewComplaintCommand(
                new ProductId("1e603af2-c005-45dd-bbc0-514923a5669b"),
                new UserId("6173a5e0-a63d-4328-b9fd-e9b72c1cd443"),
                "I'm losing my patience because the product is not working.",
                "192.168.1.1"
        )
        def existingComplaint = Mock(Complaint)
        def existingComplaintId = new ComplaintId("1e603af2-c005-45dd-bbc0-514923a5669b")
        existingComplaint.getId() >> existingComplaintId

        when:
        def complaintId = complaintService.registerNewComplaint(command)

        then:
        1 * complaintRepository.findByProductIdAndReporterId(command.getProductId(), command.getUserId()) >> Optional.of(existingComplaint)
        1 * existingComplaint.incrementReportCount()
        complaintId == existingComplaintId
    }

    def "should throw exception if complaint not found by ID"() {
        given:
        def complaintId = new ComplaintId("1e603af2-c005-45dd-bbc0-514923a5669b")

        when:
        complaintService.findCompliantByComplaintId(complaintId)

        then:
        1 * complaintRepository.findById(complaintId) >> Optional.empty()
        def e = thrown(ComplaintNotFoundException)
        e.message == "Complaint with ID: 1e603af2-c005-45dd-bbc0-514923a5669b cannot be found"
    }

    def "should return complaint if found by ID"() {
        given:
        def complaintId = new ComplaintId("1e603af2-c005-45dd-bbc0-514923a5669b")
        def existingComplaint = Mock(Complaint)

        when:
        def complaint = complaintService.findCompliantByComplaintId(complaintId)

        then:
        1 * complaintRepository.findById(complaintId) >> Optional.of(existingComplaint)
        complaint == existingComplaint
    }

    def "should update complaint content when valid id and content are provided"() {
        given:
        def complaintId = new ComplaintId("1e603af2-c005-45dd-bbc0-514923a5669b")
        def existingComplaint = Mock(Complaint)
        def newContent = "Updated complaint content"

        when:
        complaintService.updateComplaint(complaintId, newContent)

        then:
        1 * complaintRepository.findById(complaintId) >> Optional.of(existingComplaint)
        1 * existingComplaint.updateCompliant({ it.value == newContent })
    }

    def "should throw ComplaintNotFoundException when complaint is not found"() {
        given:
        def complaintId = new ComplaintId("1e603af2-c005-45dd-bbc0-514923a5669b")

        and:
        complaintRepository.findById(complaintId) >> Optional.empty()

        when:
        complaintService.updateComplaint(
                complaintId,
                "My dog chewed the product, and now it's beyond repair, can I ask for a refund?"
        )

        then:
        def e = thrown(ComplaintNotFoundException)
        e.message == "Complaint with ID: 1e603af2-c005-45dd-bbc0-514923a5669b cannot be found"
    }

    def "should return a page of ComplaintDto objects when complaints are present"() {
        given:
        def pageable = PageRequest.of(0, 5)
        def complaints = [
                new Complaint(
                        new ProductId("1e603af2-c005-45dd-bbc0-514923a5669b"),
                        new ComplaintContent("Received a damaged item"),
                        new UserId("6173a5e0-a63d-4328-b9fd-e9b72c1cd443"),
                        "Poland"
                ),
                new Complaint(
                        new ProductId("1e603af2-c005-45dd-bbc0-514923a5669b"),
                        new ComplaintContent("Dog ate the item"),
                        new UserId("6173a5e0-a63d-4328-b9fd-e9b72c1cd443"),
                        "Germany"
                )
        ]
        def page = new PageImpl<>(complaints, pageable, complaints.size())

        and:
        complaintRepository.findAll(pageable) >> page

        when:
        Page<Complaint> result = complaintService.getAllComplaints(pageable)

        then:
        with(result) {
            content.size() == 2
            with( content[0]){
                reporterId.value.toString() == "6173a5e0-a63d-4328-b9fd-e9b72c1cd443"
                content.value == "Received a damaged item"
                country == "Poland"
            }
            with( content[1] ) {
                reporterId.value.toString() == "6173a5e0-a63d-4328-b9fd-e9b72c1cd443"
                content.value == "Dog ate the item"
                country == "Germany"
            }
        }
    }

    def "should return an empty page when no complaints are present"() {
        given:
        def pageable = PageRequest.of(0, 5)
        def emptyPage = new PageImpl<>([], pageable, 0)

        and:
        complaintRepository.findAll(pageable) >> emptyPage

        when:
        Page<Complaint> result = complaintService.getAllComplaints(pageable)

        then:
        result.content.isEmpty()
        result.totalElements == 0
        result.totalPages == 0
    }
}