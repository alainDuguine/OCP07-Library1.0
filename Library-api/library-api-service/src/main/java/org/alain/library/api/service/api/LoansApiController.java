package org.alain.library.api.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.alain.library.api.service.dto.InlineResponse200;
import org.alain.library.api.service.dto.LoanDto;
import org.alain.library.api.service.dto.LoanStatusDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-31T15:23:24.407+01:00")

@Controller
public class LoansApiController implements LoansApi {

    private static final Logger log = LoggerFactory.getLogger(LoansApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public LoansApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<LoanDto> addLoan(@ApiParam(value = "Loan object that needs to be added to the database" ,required=true )  @Valid @RequestBody LoanDto loanForm) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<LoanDto>(objectMapper.readValue("{  \"endDate\" : \"endDate\",  \"bookCopyId\" : 6,  \"currentStatus\" : \"{}\",  \"id\" : 0,  \"userId\" : 1,  \"startDate\" : \"startDate\"}", LoanDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<LoanDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<LoanDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<LoanStatusDto> extendLoan(@ApiParam(value = "Id of loan",required=true) @PathVariable("id") Long id,@ApiParam(value = "User identification" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<LoanStatusDto>(objectMapper.readValue("{  \"date\" : \"date\",  \"id\" : 0,  \"status\" : \"status\"}", LoanStatusDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<LoanStatusDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<LoanStatusDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<LoanDto> getLoan(@ApiParam(value = "Id of loan to return",required=true) @PathVariable("id") Long id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<LoanDto>(objectMapper.readValue("{  \"endDate\" : \"endDate\",  \"bookCopyId\" : 6,  \"currentStatus\" : \"{}\",  \"id\" : 0,  \"userId\" : 1,  \"startDate\" : \"startDate\"}", LoanDto.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<LoanDto>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<LoanDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InlineResponse200> getLoanHistory(@ApiParam(value = "Id of loan",required=true) @PathVariable("id") Long id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<InlineResponse200>(objectMapper.readValue("{  \"loan\" : {    \"endDate\" : \"endDate\",    \"bookCopyId\" : 6,    \"currentStatus\" : \"{}\",    \"id\" : 0,    \"userId\" : 1,    \"startDate\" : \"startDate\"  },  \"statuses\" : [ {    \"endDate\" : \"endDate\",    \"bookCopyId\" : 6,    \"currentStatus\" : \"{}\",    \"id\" : 0,    \"userId\" : 1,    \"startDate\" : \"startDate\"  }, {    \"endDate\" : \"endDate\",    \"bookCopyId\" : 6,    \"currentStatus\" : \"{}\",    \"id\" : 0,    \"userId\" : 1,    \"startDate\" : \"startDate\"  } ]}", InlineResponse200.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InlineResponse200>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<InlineResponse200>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<LoanDto>> getLoans(@ApiParam(value = "Status values as filter in research", allowableValues = "loaned, returned, prolonged, late, closed") @Valid @RequestParam(value = "status", required = false) List<String> status) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<LoanDto>>(objectMapper.readValue("[ {  \"endDate\" : \"endDate\",  \"bookCopyId\" : 6,  \"currentStatus\" : \"{}\",  \"id\" : 0,  \"userId\" : 1,  \"startDate\" : \"startDate\"}, {  \"endDate\" : \"endDate\",  \"bookCopyId\" : 6,  \"currentStatus\" : \"{}\",  \"id\" : 0,  \"userId\" : 1,  \"startDate\" : \"startDate\"} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<LoanDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<LoanDto>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
