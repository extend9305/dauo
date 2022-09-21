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
    public List<DataDTO> getDaouExamList() {
        return daouExamMapper.getDaouExamList();
    }

    @Override
    public void setDaouExam(DataDTO dto) {
        daouExamMapper.setDaouExam(dto);
    }
}
