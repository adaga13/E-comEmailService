package org.scaler.ecomemailservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailEventDto {

    private String from;

    private String to;

    private String body;

    private String subject;
}
