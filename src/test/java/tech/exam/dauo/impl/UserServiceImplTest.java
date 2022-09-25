package tech.exam.dauo.impl;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import tech.exam.dauo.dto.LoginDTO;
import tech.exam.dauo.dto.UserDTO;
import tech.exam.dauo.security.JwtTokenProvider;
import tech.exam.dauo.service.UserService;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void init(){
        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(1L);
        userDTO.setPassword("qwer1234");
        userDTO.setUsername("lee");
        userDTO.setRole("user");
        userService.join(userDTO);
    }
    @AfterEach
    void after(){
        userService.delete(1L);
    }


    @Test
    void 가입_테스트(){
        //given
        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(2L);
        userDTO.setPassword("qwer1234");
        userDTO.setUsername("lee");
        userDTO.setRole("user");
        userService.join(userDTO);
        //when
        Optional<UserDTO> user = userService.findUserByUsername(2L);
        //then
        Assert.assertEquals(user.get().getUserId(),userDTO.getUserId());
    }

    @Test
    void 로그인_테스트(){
        //given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId(1L);
        loginDTO.setPassword("qwer1234");
        //when
        String loginToken = userService.login(loginDTO);
        String createToken = jwtTokenProvider.createToken(1L, Collections.singletonList("user"));
        //then
        Assert.assertEquals(loginToken,createToken);
    }

    @Test
    void 사용자검색_테스트(){
        Optional<UserDTO> userByUsername = userService.findUserByUsername(1L);

        Assert.assertEquals("lee",userByUsername.get().getUsername());

    }
}