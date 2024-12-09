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

import java.time.Duration;
import java.util.Arrays;

import static br.com.fmatheus.controller.util.ApplicationUtil.carregarClientesDoArquivo;
import static br.com.fmatheus.controller.util.ApplicationUtil.tokenCounter;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatGptFacede {

    private final OpenApiProperties apiProperties;

    public void generateCategory(ChatGptRequest request) {

        this.tokenCount(request.getUser());

        var system = """
                Você é um categorizador de produtos e deve responder apenas o nome da categoria do produto.
                Exemplo de uso:
                Pergunta: Bola de futebol.
                Resposta: Esportes
                """;

        log.info(request.getUser());
        log.info(system);

        var service = new OpenAiService(this.apiProperties.getKey(), Duration.ofSeconds(30)); //Timeout de 30 segundos

        var chatRequest = ChatCompletionRequest.builder()
                .model(this.apiProperties.getModel())
                .messages(Arrays.asList(
                        new ChatMessage(ChatMessageRole.USER.value(), request.getUser()),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), system)
                ))
                .n(5) //Numero de respostas que deseja que retorne
                .build();

        service.createChatCompletion(chatRequest).getChoices().forEach(c -> log.info(c.getMessage().getContent()));
    }

    public void generateClients() {
        var promptSistema = """
                Identifique o perfil de compra de cada cliente.
                A resposta deve ser:
                Cliente - descreva o perfil do cliente em três palavras
                """;

        var clients = carregarClientesDoArquivo();
        log.info("clients: {}", clients);

        this.tokenCount(clients);
        var request = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(Arrays.asList(
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), promptSistema),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), clients)
                ))
                .build();


        var service = new OpenAiService(this.apiProperties.getKey(), Duration.ofSeconds(30)); //Timeout de 30 segundos

        log.info(service.createChatCompletion(request).getChoices().get(0).getMessage().getContent());
    }


    private void tokenCount(String value) {
        var tokens = tokenCounter(value);
        log.info("tokens: {}", tokens);
    }

}
