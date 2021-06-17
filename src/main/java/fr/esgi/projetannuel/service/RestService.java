package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.enumeration.Language;
import fr.esgi.projetannuel.model.Constants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RestService {
    private final WebClient webClient;

    public RestService(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.baseUrl(Constants.COMPILER_URL).build();
    }

    public String postCode(String code, Language language) {
        return webClient.post().uri("/" + language)
                .body(Mono.just(code), String.class)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .retrieve().bodyToMono(String.class).block();
    }
}
