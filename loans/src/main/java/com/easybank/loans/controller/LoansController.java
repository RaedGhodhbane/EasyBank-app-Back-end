package com.easybank.loans.controller;

import com.easybank.loans.constants.LoansConstants;
import com.easybank.loans.dto.ErrorResponseDTO;
import com.easybank.loans.dto.LoansContactInfoDTO;
import com.easybank.loans.dto.LoansDTO;
import com.easybank.loans.dto.ResponseDTO;
import com.easybank.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

    @Tag(
            name = "CRUD REST APIs for Loans in EazyBank",
            description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
    )
    @RestController
    @RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Validated
    public class LoansController {

        private final ILoanService iLoansService;

        private static final Logger logger = LoggerFactory.getLogger(LoansController.class);
        @Autowired
        public LoansController(ILoanService iLoansService) {
            this.iLoansService = iLoansService;
        }

        @Value("${build.version}")
        private String buildVersion;

        @Autowired
        private Environment environment;

        @Autowired
        private LoansContactInfoDTO loansContactInfoDTO;


        @Operation(
                summary = "Create Loan REST API",
                description = "REST API to create new loan inside EazyBank"
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "201",
                        description = "HTTP Status CREATED"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "HTTP Status Internal Server Error",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponseDTO.class)
                        )
                )
        }
        )
        @PostMapping("/create")
        public ResponseEntity<ResponseDTO> createLoan(@RequestParam
                                                      @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                      String mobileNumber) {
            iLoansService.createLoan(mobileNumber);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDTO(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
        }

        @Operation(
                summary = "Fetch Loan Details REST API",
                description = "REST API to fetch loan details based on a mobile number"
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "HTTP Status OK"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "HTTP Status Internal Server Error",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponseDTO.class)
                        )
                )
        }
        )
        @GetMapping("/fetch")
        public ResponseEntity<LoansDTO> fetchLoanDetails(@RequestHeader("easyBank-correlation-id")
                                                                 String correlationId,
                                                         @RequestParam
                                                         @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
            logger.debug("easyBank-correlation-id found: {}", correlationId);
            LoansDTO loansDto = iLoansService.fetchLoan(mobileNumber);
            return ResponseEntity.status(HttpStatus.OK).body(loansDto);
        }

        @Operation(
                summary = "Update Loan Details REST API",
                description = "REST API to update loan details based on a loan number"
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "HTTP Status OK"
                ),
                @ApiResponse(
                        responseCode = "417",
                        description = "Expectation Failed"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "HTTP Status Internal Server Error",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponseDTO.class)
                        )
                )
        }
        )
        @PutMapping("/update")
        public ResponseEntity<ResponseDTO> updateLoanDetails(@Valid @RequestBody LoansDTO loansDto) {
            boolean isUpdated = iLoansService.updateLoan(loansDto);
            if(isUpdated) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new ResponseDTO(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
            }else{
                return ResponseEntity
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ResponseDTO(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
            }
        }

        @Operation(
                summary = "Delete Loan Details REST API",
                description = "REST API to delete Loan details based on a mobile number"
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "HTTP Status OK"
                ),
                @ApiResponse(
                        responseCode = "417",
                        description = "Expectation Failed"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "HTTP Status Internal Server Error",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponseDTO.class)
                        )
                )
        }
        )
        @DeleteMapping("/delete")
        public ResponseEntity<ResponseDTO> deleteLoanDetails(@RequestParam
                                                             @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                             String mobileNumber) {
            boolean isDeleted = iLoansService.deleteLoan(mobileNumber);
            if(isDeleted) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(new ResponseDTO(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
            }else{
                return ResponseEntity
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ResponseDTO(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
            }
        }

        @Operation(
                summary = "Get build information",
                description = "Get Build information that is deployed into cards microservice"
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "HTTP Status OK"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "HTTP Status Internal Server Error",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponseDTO.class)
                        )
                )
        }
        )

        @GetMapping("/build-info")
        public ResponseEntity<String> getBuildInfo() {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(buildVersion);
        }


        @Operation(
                summary = "Get Java version",
                description = "Get Java versions details that is installed into cards microservice"
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "HTTP Status OK"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "HTTP Status Internal Server Error",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponseDTO.class)
                        )
                )
        }
        )
@GetMapping("/java-version")
        public ResponseEntity<String> getJavaVersion() {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(environment.getProperty("JAVA_HOME"));
}

        @Operation(
                summary = "Get Contact Info",
                description = "Contact Info details that can be reached out in case of any issues"
        )
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "HTTP Status OK"
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "HTTP Status Internal Server Error",
                        content = @Content(
                                schema = @Schema(implementation = ErrorResponseDTO.class)
                        )
                )
        }
        )
@GetMapping("/contact-info")
        public ResponseEntity<LoansContactInfoDTO> getContactInfo() {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(loansContactInfoDTO);
}
    }
