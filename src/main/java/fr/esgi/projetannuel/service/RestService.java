package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.enumeration.Language;

import fr.esgi.projetannuel.model.CodeRequest;
import fr.esgi.projetannuel.model.CodeResult;
import fr.esgi.projetannuel.model.Constants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RestService {
    private final WebClient webClient;

    public RestService(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder
                .baseUrl(Constants.COMPILER_BASE_URL).build();
    }

    public CodeResult postCode(String code, Language language, String exerciseTitle, String userId) {
        CodeRequest codeRequest = new CodeRequest(userId, code, exerciseTitle);
        return webClient.post().uri("/" + language)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .bodyValue(codeRequest)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .retrieve().bodyToMono(CodeResult.class).block();
    }
}
