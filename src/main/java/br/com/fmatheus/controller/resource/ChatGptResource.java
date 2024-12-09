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
    @PostMapping("/categories")
    public void generateCategory(@RequestBody @Valid ChatGptRequest request) {
        this.facede.generateCategory(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/clients")
    public void generateClients() {
        this.facede.generateClients();
    }
}
