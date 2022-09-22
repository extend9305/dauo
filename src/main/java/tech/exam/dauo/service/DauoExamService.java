package tech.exam.dauo.service;

import tech.exam.dauo.dto.DataDTO;

import java.util.List;

public interface DauoExamService {
    public List<DataDTO>getAllList();
    public DataDTO getFindDauoExam(String regDtm);
    public void save(DataDTO dto);
    public void delete(String regDtm);
    public void update(DataDTO dto);

    public String getFindJoinCnt(String regDtm);
    public String getFindResignCnt(String regDtm);
    public String getFindPayAmt(String regDtm);
    public String getFindUsedAmt(String regDtm);
    public String getFindSalesAmt(String regDtm);

}
