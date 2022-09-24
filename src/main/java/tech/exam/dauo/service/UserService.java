package tech.exam.dauo.service;

import org.springframework.stereotype.Service;
import tech.exam.dauo.dto.LoginDTO;
import tech.exam.dauo.dto.UserDTO;

public interface UserService {
    public UserDTO findByUserId(Long userId);
    public String login(LoginDTO loginDto);
    public UserDTO join(UserDTO userDto);
}
