package tech.exam.dauo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tech.exam.dauo.dto.LoginDTO;
import tech.exam.dauo.dto.UserDTO;
import tech.exam.dauo.service.DauoExamService;
import tech.exam.dauo.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SignControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private DauoExamService dauoExamService;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ObjectMapper objectMapper;

    private String loginToken;

    @BeforeEach
    public void before() {
        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(1L);
        userDTO.setPassword("qwer1234");
        userDTO.setUsername("lee");
        userDTO.setRole("user");
        userService.join(userDTO);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId(1L);
        loginDTO.setPassword("qwer1234");
        //when
        loginToken = userService.login(loginDTO);
    }

    @AfterEach
    void after(){
        userService.delete(1L);
    }


    @Test
    void join() throws Exception{
        Map<String, String> input = new HashMap<>();
        // body에 json 형식으로 회원의 데이터를 넣기 위해서 Map을 이용한다.
        input.put("userId", "2");
        input.put("password", "qwer1234");
        input.put("username","lee");
        input.put("role", "user");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/join") // 넣어준 컨트롤러의 Http Method 와 URL 을 지정
                        .contentType(MediaType.APPLICATION_JSON)
                        //json 형식으로 데이터를 보낸다고 명시
                        .content(objectMapper.writeValueAsString(input))
                //Map으로 만든 input을 json형식의 String으로 만들기 위해 objectMapper를 사용
        )
                .andExpect(status().isCreated());
    }

    @Test
    void login() throws Exception {
        Map<String, String> input = new HashMap<>();
        // body에 json 형식으로 회원의 데이터를 넣기 위해서 Map을 이용한다.
        input.put("userId", "1");
        input.put("password", "qwer1234");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/login") // 넣어준 컨트롤러의 Http Method 와 URL 을 지정
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN",loginToken)
                        //json 형식으로 데이터를 보낸다고 명시
                        .content(objectMapper.writeValueAsString(input))
                //Map으로 만든 input을 json형식의 String으로 만들기 위해 objectMapper를 사용
        )
                .andExpect(status().isOk());

    }

}