package tech.exam.dauo.service;

import org.springframework.security.core.userdetails.UserDetails;
import tech.exam.dauo.dto.UserDTO;

public interface CustomUserDetailsService {
    public UserDetails loadUserByUsername(String userId);
    public UserDTO addAuthorities(UserDTO userDto);
}
