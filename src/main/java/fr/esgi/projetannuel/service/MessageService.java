package fr.esgi.projetannuel.service;

import fr.esgi.projetannuel.exception.ResourceNotFoundException;
import fr.esgi.projetannuel.model.Constants;
import fr.esgi.projetannuel.model.Message;
import fr.esgi.projetannuel.model.Session;
import fr.esgi.projetannuel.model.User;
import fr.esgi.projetannuel.repository.MessageRepository;
import fr.esgi.projetannuel.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message findById(String id) {
        return messageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.USER, id));
    }

    public Message create(Message message) {
        return messageRepository.save(message);
    }

    @Transactional
    public void deleteById(String id) {
        messageRepository.deleteById(id);
    }
}
