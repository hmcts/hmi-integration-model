package uk.gov.hmcts.futurehearings.hmi.integration.junit5.header.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Builder
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
public class BusinessHeaderDTO {
    /*"Destination": "CFT",
            "Ocp-Apim-Subscription-Key": "587c56f59a604054b1ce73dac0ea5b28",
            "Host": "hmi-apim-svc-test.azure-api.net",
            "Ocp-Apim-Trace": "true",
            "Company-Name": "HMCTS",
            "Source": "SnL",
            "DateTime": "datetimestring",
            "Content-Type": "application/json",
            "RequestType": "TypeOfCase"*/

    private String destination;
    private String companyName;
    private String source;
    private String dateTime;
    private String requestType;
}
