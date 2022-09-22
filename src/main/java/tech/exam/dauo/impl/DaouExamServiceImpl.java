package tech.exam.dauo.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.exam.dauo.dao.DaouExamMapper;
import tech.exam.dauo.dto.DataDTO;
import tech.exam.dauo.service.DauoExamService;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DaouExamServiceImpl implements DauoExamService {
    private final DaouExamMapper daouExamMapper;

    @Override
    public List<DataDTO> getAllList() {
        return daouExamMapper.getAllList();
    }

    @Override
    public void save(DataDTO dto) {

        dto.setRegDtm(dto.getRegDtm().replaceAll("-","").replaceAll(" ","").trim());
        dto.setJoinCnt(dto.getJoinCnt().replaceAll(",","").trim());
        dto.setResignCnt(dto.getResignCnt().replaceAll(",","").trim());
        dto.setPayAmt(dto.getPayAmt().replaceAll(",","").trim());
        dto.setUsedAmt(dto.getUsedAmt().replaceAll(",","").trim());
        dto.setSalesAmt(dto.getSalesAmt().replaceAll(",","").trim());

        daouExamMapper.save(dto);
    }

    @Override
    public void delete(String regDtm) {
        daouExamMapper.delete(regDtm);
    }

    @Override
    public String getFindJoinCnt(String regDtm) {
        DataDTO dto = daouExamMapper.getFindDauoExam(regDtm);
        return dto.getJoinCnt();
    }

    @Override
    public String getFindResignCnt(String regDtm) {
        DataDTO dto = daouExamMapper.getFindDauoExam(regDtm);
        return dto.getJoinCnt();
    }

    @Override
    public String getFindPayAmt(String regDtm) {
        DataDTO dto = daouExamMapper.getFindDauoExam(regDtm);
        return dto.getPayAmt();
    }

    @Override
    public String getFindUsedAmt(String regDtm) {
        DataDTO dto = daouExamMapper.getFindDauoExam(regDtm);
        return dto.getUsedAmt();
    }

    @Override
    public String getFindSalesAmt(String regDtm) {
        DataDTO dto = daouExamMapper.getFindDauoExam(regDtm);
        return dto.getSalesAmt();
    }

    @Override
    public DataDTO getFindDauoExam(String regDtm) {
        return daouExamMapper.getFindDauoExam(regDtm);
    }

    @Override
    public void update(DataDTO dto) {
        dto.setRegDtm(dto.getRegDtm().replaceAll("-","").replaceAll(" ","").trim());
        dto.setJoinCnt(dto.getJoinCnt().replaceAll(",","").trim());
        dto.setResignCnt(dto.getResignCnt().replaceAll(",","").trim());
        dto.setPayAmt(dto.getPayAmt().replaceAll(",","").trim());
        dto.setUsedAmt(dto.getUsedAmt().replaceAll(",","").trim());
        dto.setSalesAmt(dto.getSalesAmt().replaceAll(",","").trim());

        daouExamMapper.update(dto);
    }
}
