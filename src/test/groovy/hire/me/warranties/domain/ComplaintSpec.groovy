package hire.me.warranties.domain

import hire.me.warranties.domain.idendifiers.ProductId
import hire.me.warranties.domain.idendifiers.UserId
import spock.lang.Specification


class ComplaintSpec extends Specification {

    def "should create a valid Complaint with all fields"() {
        given:
        def productId = new ProductId("1e603af2-c005-45dd-bbc0-514923a5669b")
        def content = new ComplaintContent("Product does not work as expected.")
        def reporterId = new UserId("6173a5e0-a63d-4328-b9fd-e9b72c1cd443")
        def country = "Poland"

        when:
        def complaint = new Complaint(productId, content, reporterId, country)

        then:
        complaint.id != null
        complaint.productId == productId
        complaint.content == content
        complaint.creationDate !=null
        complaint.reporterId == reporterId
        complaint.country == country
        complaint.reportCount == 1
    }

    def "should increment report count"() {
        given:
        def productId = new ProductId("1e603af2-c005-45dd-bbc0-514923a5669b")
        def content = new ComplaintContent("Product issue.")
        def reporter = new UserId("6173a5e0-a63d-4328-b9fd-e9b72c1cd443")
        def country = "Poland"
        def complaint = new Complaint(productId, content, reporter, country)

        when:
        complaint.incrementReportCount()

        then:
        complaint.getReportCount() == 2
    }
}
