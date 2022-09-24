package tech.exam.dauo.service;

import org.springframework.stereotype.Service;
import tech.exam.dauo.dto.LoginDTO;
import tech.exam.dauo.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    //Optional<UserDTO> findUserByUsername(String username);
    UserDTO findByUserId(Long userId);
    public UserDTO join(UserDTO userDto);
    public String login(LoginDTO loginDTO);
}
