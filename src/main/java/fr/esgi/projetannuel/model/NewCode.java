package fr.esgi.projetannuel.model;

import fr.esgi.projetannuel.enumeration.Language;
import lombok.Data;

import java.util.List;

@Data
public class NewCode {
    private String title;
    private String code;
    private String description;
    private Language language;
    private String[] tests;
    private String compilationOutput;
    private long compilationScore;
    private String status;
    List<String> codeViolationList;
}
