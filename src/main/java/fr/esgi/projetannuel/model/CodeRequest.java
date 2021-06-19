package fr.esgi.projetannuel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class CodeRequest {
    private String userId;
    private String code;
    private String exerciseTitle;
}
