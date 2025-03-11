package hire.me.warranties.domain

import hire.me.warranties.domain.complaint.Complaint
import hire.me.warranties.domain.complaint.ComplaintContent
import hire.me.warranties.domain.idendifiers.ProductId
import hire.me.warranties.domain.idendifiers.UserId
import spock.lang.Specification


class ComplaintSpec extends Specification {

    def "should create a valid Complaint with all fields"() {
        given:
        def productId = new ProductId("1e603af2-c005-45dd-bbc0-514923a5669b")
        def content = new ComplaintContent("The product is defective")
        def reporterId = new UserId("6173a5e0-a63d-4328-b9fd-e9b72c1cd443")
        def country = "Poland"

        when:
        def complaint = new Complaint(productId, content, reporterId, country)

        then:
        complaint.id != null
        complaint.productId == productId
        complaint.content == content
        complaint.creationDate != null
        complaint.reporterId == reporterId
        complaint.country == country
        complaint.reportCount == 1
    }

    def "should increment report count"() {
        given:
        def productId = new ProductId("1e603af2-c005-45dd-bbc0-514923a5669b")
        def content = new ComplaintContent("I'm losing my patience because the product is not working.")
        def reporter = new UserId("6173a5e0-a63d-4328-b9fd-e9b72c1cd443")
        def country = "Poland"
        def complaint = new Complaint(productId, content, reporter, country)

        when:
        complaint.incrementReportCount()

        then:
        complaint.getReportCount() == 2
    }

    def "should throw IllegalArgumentException if ProductId is null"() {
        given:
        def content = new ComplaintContent("The product is defective")
        def reporterId = new UserId("6173a5e0-a63d-4328-b9fd-e9b72c1cd443")
        def country = "Poland"

        when:
        new Complaint(null, content, reporterId, country)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Product ID cannot be null"
    }

    def "should throw IllegalArgumentException if ComplaintContent is null"() {
        given:
        def productId = new ProductId("1e603af2-c005-45dd-bbc0-514923a5669b")
        def reporterId = new UserId("6173a5e0-a63d-4328-b9fd-e9b72c1cd443")
        def country = "Poland"

        when:
        new Complaint(productId, null, reporterId, country)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Content cannot be null"
    }

    def "should throw IllegalArgumentException if ReporterId is null"() {
        given:
        def productId = new ProductId("1e603af2-c005-45dd-bbc0-514923a5669b")
        def content = new ComplaintContent("The product is defective")
        def country = "Poland"

        when:
        new Complaint(productId, content, null, country)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "ReporterId cannot be null"
    }

    def "should throw IllegalArgumentException if Country is null"() {
        given:
        def productId = new ProductId("1e603af2-c005-45dd-bbc0-514923a5669b")
        def content = new ComplaintContent("The product is defective")
        def reporterId = new UserId("6173a5e0-a63d-4328-b9fd-e9b72c1cd443")

        when:
        new Complaint(productId, content, reporterId, null)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Country cannot be null"
    }

    def "should update complaint content"() {
        given:
        def productId = new ProductId("1e603af2-c005-45dd-bbc0-514923a5669b")
        def reporterId = new UserId("6173a5e0-a63d-4328-b9fd-e9b72c1cd443")
        def country = "poland"
        def oldComplaintContent = new ComplaintContent("The product is defective")
        def newComplaintContent = new ComplaintContent("I'm losing my patience because the product is not working.")
        def complainToUpdate = new Complaint(productId, oldComplaintContent, reporterId, country)

        when:
        complainToUpdate.updateCompliant(newComplaintContent)

        then:
        complainToUpdate.content != oldComplaintContent
        complainToUpdate.content == newComplaintContent
        complainToUpdate.id != null
        complainToUpdate.productId == productId
        complainToUpdate.productId == productId
        complainToUpdate.creationDate != null
        complainToUpdate.reporterId == reporterId
        complainToUpdate.country == country
        complainToUpdate.reportCount == 1
    }
}