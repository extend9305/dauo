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
                        .get("/dauo/api/alldata") // ????????? ??????????????? Http Method ??? URL ??? ??????
                        .accept(MediaType.ALL) // accept encoding ????????? ??????
                        .header("X-AUTH-TOKEN",loginToken)
        )
                .andExpect(status().isOk());
    }

    @Test
    void oneSave() throws Exception {
        Map<String, String> input = new HashMap<>();
        // body??? json ???????????? ????????? ???????????? ?????? ????????? Map??? ????????????.
        input.put("regDtm", "2022092210");
        input.put("joinCnt", "1");
        input.put("resignCnt", "1");
        input.put("payAmt", "300000");
        input.put("salesAmt", "90000");
        input.put("usedAmt", "90000");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/dauo/api/save") // ????????? ??????????????? Http Method ??? URL ??? ??????
                        .header("X-AUTH-TOKEN",loginToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        //json ???????????? ???????????? ???????????? ??????
                        .content(objectMapper.writeValueAsString(input))
                        //Map?????? ?????? input??? json????????? String?????? ????????? ?????? objectMapper??? ??????
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
                        .put("/dauo/api/update/2022092210") // ????????? ??????????????? Http Method ??? URL ??? ??????
                        .accept(MediaType.ALL) // accept encoding ????????? ??????
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
                        .delete("/dauo/api/delete/2022092210") // ????????? ??????????????? Http Method ??? URL ??? ??????
                        .header("X-AUTH-TOKEN",loginToken)
        ).andExpect(status().isOk());

        DataDTO findDauoExam = dauoExamService.getFindDauoExam("2022092210");
        Assert.assertNull(findDauoExam);

    }
}