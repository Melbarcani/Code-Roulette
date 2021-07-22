package fr.esgi.projetannuel.model.Dto;

import fr.esgi.projetannuel.model.UserInGame;

import java.util.ArrayList;
import java.util.List;

public class GameDto {
    public String id;

    public List<UserInGame> usersInGame = new ArrayList<>();
}
