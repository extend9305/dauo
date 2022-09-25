package tech.exam.dauo.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import tech.exam.dauo.config.ConfigProperties;
import tech.exam.dauo.dao.DaouExamMapper;
import tech.exam.dauo.dto.DataDTO;

import java.io.*;

@Slf4j
public class SimpleTasklet implements Tasklet {
    DaouExamMapper daouExamMapper;
    @Autowired
    ConfigProperties configProperties;

    public SimpleTasklet(DaouExamMapper daouExamMapper) {
        this.daouExamMapper = daouExamMapper;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String SAVE_PATH = getClass().getClassLoader().getResource("").toString()+"/resources/target";

        String DATA_DIRECTORY = "C:\\workspace\\com\\com\\src\\main\\resources\\target";
        File dir = new File(DATA_DIRECTORY);
        File[] files = dir.listFiles();

        BufferedReader br;


        for (File file:files) {
            String fileName = file.getName();
            String ext = fileName.substring(fileName.lastIndexOf(".")+1);

            switch (ext){
                case "txt" :
                    readDefalut(file);
                    break;
                case "csv" :
                    readDefalut(file);
                    break;
                default:
                    break;
            }
        }

        return RepeatStatus.FINISHED;
    }
    /*
        csv,txt 파일 파싱가능.
     */
    public  void readDefalut(File file) {
        DataDTO dto = new DataDTO();
        try (BufferedReader br = new BufferedReader(new FileReader(file));){
            String s;

            while ((s = br.readLine()) != null) {
                log.info(dto.toString());
                String[] item = s.split("\\|");

                dto.setRegDtm(item[0].replaceAll("-","").replaceAll(" ","").trim());
                dto.setJoinCnt(item[1].replaceAll(",","").trim());
                dto.setResignCnt(item[2].replaceAll(",","").trim());
                dto.setPayAmt(item[3].replaceAll(",","").trim());
                dto.setUsedAmt(item[4].replaceAll(",","").trim());
                dto.setSalesAmt(item[5].replaceAll(",","").trim());

                daouExamMapper.save(dto);
            }

        }catch (FileNotFoundException e){
            log.info(e.getMessage());
        }catch (IOException e){
            log.info(e.getMessage());
        }
    }
}
