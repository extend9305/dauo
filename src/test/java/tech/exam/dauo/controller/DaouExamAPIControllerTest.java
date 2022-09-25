package tech.exam.dauo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import tech.exam.dauo.config.ConfigProperties;
import tech.exam.dauo.dto.DataDTO;
import tech.exam.dauo.dto.LoginDTO;
import tech.exam.dauo.dto.UserDTO;
import tech.exam.dauo.service.DauoExamService;
import tech.exam.dauo.service.UserService;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DaouExamAPIControllerTest {
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
        dauoExamService.delete("2022092210");
    }


    @Test
    void allData() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/dauo/api/alldata") // 넣어준 컨트롤러의 Http Method 와 URL 을 지정
                        .accept(MediaType.ALL) // accept encoding 타입을 지정
                        .header("X-AUTH-TOKEN",loginToken)
        )
                .andExpect(status().isOk());
    }

    @Test
    void oneSave() throws Exception {
        Map<String, String> input = new HashMap<>();
        // body에 json 형식으로 회원의 데이터를 넣기 위해서 Map을 이용한다.
        input.put("regDtm", "2022092210");
        input.put("joinCnt", "1");
        input.put("resignCnt", "1");
        input.put("payAmt", "300000");
        input.put("salesAmt", "90000");
        input.put("usedAmt", "90000");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/dauo/api/save") // 넣어준 컨트롤러의 Http Method 와 URL 을 지정
                        .header("X-AUTH-TOKEN",loginToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        //json 형식으로 데이터를 보낸다고 명시
                        .content(objectMapper.writeValueAsString(input))
                        //Map으로 만든 input을 json형식의 String으로 만들기 위해 objectMapper를 사용
                     )
                .andExpect(status().isOk());

    }

    @Test
    void findOneUpdate() throws Exception{
        DataDTO dataDTO = new DataDTO();

        dataDTO.setRegDtm("2022092210");
        dataDTO.setResignCnt("1");
        dataDTO.setJoinCnt("1");
        dataDTO.setSalesAmt("10000");
        dataDTO.setPayAmt("10000");
        dataDTO.setUsedAmt("10000");

        dauoExamService.save(dataDTO);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/dauo/api/update/2022092210") // 넣어준 컨트롤러의 Http Method 와 URL 을 지정
                        .accept(MediaType.ALL) // accept encoding 타입을 지정
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("usedAmt","20000")
                        .param("salesAmt","20000")
                        .param("payAmt","20000")
                        .param("resignCnt","3")
                        .param("joinCnt","3")
                        .header("X-AUTH-TOKEN",loginToken)
        ).andExpect(status().isOk());
    }


    @Test
    void findOneDelete() throws Exception{
        DataDTO dataDTO = new DataDTO();

        dataDTO.setRegDtm("2022092210");
        dataDTO.setResignCnt("1");
        dataDTO.setJoinCnt("1");
        dataDTO.setSalesAmt("10000");
        dataDTO.setPayAmt("10000");
        dataDTO.setUsedAmt("10000");

        dauoExamService.save(dataDTO);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/dauo/api/delete/2022092210") // 넣어준 컨트롤러의 Http Method 와 URL 을 지정
                        .header("X-AUTH-TOKEN",loginToken)
        ).andExpect(status().isOk());

        DataDTO findDauoExam = dauoExamService.getFindDauoExam("2022092210");
        Assert.assertNull(findDauoExam);

    }
}