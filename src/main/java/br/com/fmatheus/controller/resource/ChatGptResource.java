package br.com.fmatheus.controller.resource;

import br.com.fmatheus.controller.dto.request.ChatGptRequest;
import br.com.fmatheus.controller.facade.ChatGptFacede;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatGptResource {

    private final ChatGptFacede facede;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void generate(@RequestBody @Valid ChatGptRequest request) {
        this.facede.generate(request);
    }
}
