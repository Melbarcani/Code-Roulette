package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.enumeration.Language;
import fr.esgi.projetannuel.model.Exercise;

public interface ICompilerService {
    String mergeCode(Exercise exercise);
}
