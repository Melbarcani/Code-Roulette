package fr.esgi.projetannuel.service;

import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    public long computeScore(long initialInstructionsCount, long instructionsCount/*, long spentTime*/) {
        return initialInstructionsCount - instructionsCount /* - spentTime */;
    }
}
