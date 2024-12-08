package br.com.fmatheus.controller.facade;

import br.com.fmatheus.config.properties.OpenApiProperties;
import br.com.fmatheus.controller.dto.request.ChatGptRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatGptFacede {

    private final OpenApiProperties apiProperties;

    public void generate(ChatGptRequest request) {

        log.info(request.getUser());
        log.info(request.getSystem());

        var service = new OpenAiService(this.apiProperties.getKey());

        var chatRequest = ChatCompletionRequest.builder()
                .model(this.apiProperties.getModel())
                .messages(Arrays.asList(
                        new ChatMessage(ChatMessageRole.USER.value(), request.getUser()),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), request.getSystem())
                ))
                .build();

        service.createChatCompletion(chatRequest).getChoices().forEach(c -> System.out.println(c.getMessage().getContent()));
    }
}
