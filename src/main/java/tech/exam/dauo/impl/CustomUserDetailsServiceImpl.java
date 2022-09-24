package tech.exam.dauo.impl;

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

@RequiredArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userMapper.findByUserId(Long.valueOf(userId))
                .map(user -> addAuthorities(user))
                .orElseThrow(() -> new UserNotFoundException(userId + "> 찾을 수 없습니다."));
    }

    private UserDTO addAuthorities(UserDTO userDto) {
        userDto.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(userDto.getRole())));

        return userDto;
    }
}
