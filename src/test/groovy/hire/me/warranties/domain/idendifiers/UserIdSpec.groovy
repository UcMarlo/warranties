package hire.me.warranties.domain.idendifiers

import spock.lang.Specification


class UserIdSpec extends Specification {

    def "should create a valid UserId with given UUID"(){
        given:
        def uuidValue = "1e603af2-c005-45dd-bbc0-514923a5669b"

        when:
        def userId = new UserId(uuidValue)

        then:
        userId != null
        userId.value == UUID.fromString(uuidValue)
    }

}