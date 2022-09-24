package tech.exam.dauo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.exam.dauo.dao.UserMapper;
import tech.exam.dauo.dto.UserDTO;
import tech.exam.dauo.exception.UserNotFoundException;

import java.util.Arrays;

public interface CustomUserDetailsService {
    public UserDetails loadUserByUsername(String userId);
    public UserDTO addAuthorities(UserDTO userDto);
}
