package tech.exam.dauo.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.exam.dauo.dto.DataDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DaouExamServiceImplTest {
    @Autowired
    DaouExamServiceImpl daouExamService;
    @BeforeEach
    void init() {
        DataDTO dataDTO = new DataDTO();
        dataDTO.setRegDtm("2022092501");
        dataDTO.setJoinCnt("1");
        dataDTO.setResignCnt("1");
        dataDTO.setPayAmt("200000");
        dataDTO.setSalesAmt("2000000");
        dataDTO.setUsedAmt("3000000");

        daouExamService.save(dataDTO);
    }
    @Test
    void 전체리스트조회() {
        //given
        //when
        List<DataDTO> allList = daouExamService.getAllList();
        //then
        Assert.assertEquals(allList.size(),1);
    }

    @Test
    void 데이터저장() {
        //given
        DataDTO dataDTO = new DataDTO();

        dataDTO.setRegDtm("2022092502");
        dataDTO.setJoinCnt("1");
        dataDTO.setResignCnt("1");
        dataDTO.setPayAmt("200000");
        dataDTO.setSalesAmt("2000000");
        dataDTO.setUsedAmt("3000000");
        //when
        daouExamService.save(dataDTO);
        DataDTO findDauoExam = daouExamService.getFindDauoExam("2022092502");

        //then
        Assert.assertEquals(findDauoExam,dataDTO);

    }

    @Test
    void 데이터삭제() {
        //given
        //when
        daouExamService.delete("2022092501");
        DataDTO findDauoExam = daouExamService.getFindDauoExam("2022092501");
        //then
        Assert.assertNull(findDauoExam);
    }

    @Test
    void 데이터변경() {
        //given
        DataDTO findDauoExam = daouExamService.getFindDauoExam("2022092501");

        DataDTO dataDTO = new DataDTO();
        dataDTO.setUsedAmt("9000");
        dataDTO.setPayAmt("7000");
        dataDTO.setResignCnt("123");
        dataDTO.setSalesAmt("222");
        dataDTO.setJoinCnt("1");
        dataDTO.setRegDtm("2022092501");
        //when
        daouExamService.update(dataDTO);
        DataDTO changeDauoExam = daouExamService.getFindDauoExam("2022092501");
        //then
        Assert.assertNotEquals(findDauoExam,changeDauoExam);

    }

}