package tech.exam.dauo.service;

import tech.exam.dauo.dto.DataDTO;

import java.util.List;

public interface DauoExamService {
    public List<DataDTO>getDaouExamList();
    public void setDaouExam(DataDTO dto);
}
