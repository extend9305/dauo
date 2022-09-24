package tech.exam.dauo.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.exam.dauo.dao.UserMapper;
import tech.exam.dauo.dto.LoginDTO;
import tech.exam.dauo.dto.UserDTO;
import tech.exam.dauo.exception.DuplicatedUsernameException;
import tech.exam.dauo.exception.LoginFailedException;
import tech.exam.dauo.exception.UserNotFoundException;
import tech.exam.dauo.security.JwtTokenProvider;
import tech.exam.dauo.service.UserService;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findByUserId(Long userId) {
        return userMapper.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다."));
    }

    @Override
    public String login(LoginDTO loginDto) {
        UserDTO userDto = userMapper.findUserByUsername(loginDto.getUserId())
                .orElseThrow(() -> new LoginFailedException("잘못된 아이디입니다"));

        if (!passwordEncoder.matches(loginDto.getPassword(), userDto.getPassword())) {
            throw new LoginFailedException("잘못된 비밀번호입니다");
        }

        return jwtTokenProvider.createToken(userDto.getUserId(), Collections.singletonList(userDto.getRole()));
    }

    @Override
    public UserDTO join(UserDTO userDto) {
        if (userMapper.findUserByUsername(userDto.getUserId()).isPresent()) {
            throw new DuplicatedUsernameException("이미 가입된 유저입니다");
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userMapper.join(userDto);

        return userMapper.findUserByUsername(userDto.getUserId()).get();
    }
}
