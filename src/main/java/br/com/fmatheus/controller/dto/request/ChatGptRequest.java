package br.com.fmatheus.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptRequest {
    @NotBlank
    private String user;
}
