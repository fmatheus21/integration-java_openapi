package br.com.fmatheus.controller.util;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.ModelType;

import java.nio.file.Files;
import java.nio.file.Path;

public class ApplicationUtil {

    private ApplicationUtil() {
        throw new IllegalStateException(getClass().getName());
    }

    public static int tokenCounter(String value) {
        var registry = Encodings.newDefaultEncodingRegistry();
        var encoding = registry.getEncodingForModel(ModelType.GPT_3_5_TURBO);
        return encoding.countTokens(value);
    }

    public static String carregarClientesDoArquivo() {
        try {
            var path = Path.of(ClassLoader.getSystemResource("files/lista_de_compras_100_clientes.csv")
                    .toURI());
            return Files.readAllLines(path).toString();
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao carregar o arquivo!", e);
        }
    }

}
