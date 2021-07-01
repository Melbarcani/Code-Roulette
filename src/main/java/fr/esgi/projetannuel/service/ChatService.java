package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Chat;
import fr.esgi.projetannuel.model.Constants;
import fr.esgi.projetannuel.model.Message;
import fr.esgi.projetannuel.repository.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    public Chat findById(String id) {
        return chatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.USER, id));
    }

    public Chat create(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat addMessage(Chat chat, Message message) {
        chat.getMessages().add(message);
        return chatRepository.save(chat);
    }

    @Transactional
    public void deleteById(String id) {
        chatRepository.deleteById(id);
    }
}
