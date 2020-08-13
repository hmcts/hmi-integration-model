package uk.gov.hmcts.futurehearings.hmi.integration.junit5.header.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PayloadHeaderDTOFactory {

    public static final SystemHeaderDTO buildStandardSytemHeaderPart(final String contentType,
                                                                      final String trace,
                                                                      final String host,
                                                                      final String subscriptionKey) {
        return SystemHeaderDTO.builder()
                .contentType(contentType)
                .trace(trace)
                .host(host)
                .subscriptionKey(subscriptionKey).build();
    }

    public static final BusinessHeaderDTO buildStandardBuinessHeaderPart(final String companyName,
                                                                          final String dateTime,
                                                                          final String source,
                                                                          final String destination,
                                                                          final String requestType) {
        return BusinessHeaderDTO.builder()
                .companyName(companyName)
                .dateTime(dateTime)
                .source(source)
                .destination(destination).requestType(requestType).build();
    }

    public static final Map<String,String> convertToMap (final SystemHeaderDTO systemHeaderDTO,
                                                           final BusinessHeaderDTO businessHeaderDTO) {
        Map<String, String>  headerMap = new HashMap<>();
        headerMap.put("Ocp-Apim-Subscription-Key",systemHeaderDTO.subscriptionKey());
        headerMap.put("Ocp-Apim-Trace",systemHeaderDTO.trace());
        headerMap.put("Host",systemHeaderDTO.host());
        headerMap.put("Content-Type",systemHeaderDTO.contentType());

        headerMap.put("Source",businessHeaderDTO.source());
        headerMap.put("Destination",businessHeaderDTO.destination());
        headerMap.put("Company-Name",businessHeaderDTO.companyName());
        headerMap.put("DateTime",businessHeaderDTO.dateTime());
        headerMap.put("RequestType",businessHeaderDTO.requestType());
        return headerMap;
    }


}
