package br.edu.ufersa.tracesuport.TraceSuport.domain.services;

import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Request.UserRequestDTO;
import br.edu.ufersa.tracesuport.TraceSuport.api.DTO.Response.UserResponseDTO;
import br.edu.ufersa.tracesuport.TraceSuport.domain.entities.User;
import br.edu.ufersa.tracesuport.TraceSuport.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO create(UserRequestDTO request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        user = userRepository.save(user);

        return new UserResponseDTO(user);
    }

    public UserResponseDTO update(Long id, UserRequestDTO user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setName(user.getName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            updatedUser = userRepository.save(updatedUser);

            return new UserResponseDTO(updatedUser);
        } else {
            return null;
        }
    }

    public UserResponseDTO getUserById(Long id) {
        UserResponseDTO response = new UserResponseDTO(userRepository.findById(id).get());

        return response;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
