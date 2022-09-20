package tech.exam.dauo.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import tech.exam.dauo.batch.dto.DataDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SimpleTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String SAVE_PATH = getClass().getClassLoader().getResource("").toString()+"/resources/target";

        String DATA_DIRECTORY = "C:\\workspace\\com\\com\\src\\main\\resources\\target";
        File dir = new File(DATA_DIRECTORY);
        File[] files = dir.listFiles();

        BufferedReader br;

        List<DataDTO> dataList = new ArrayList<>();


        for (File file:files) {
            String fileName = file.getName();
            String ext = fileName.substring(fileName.lastIndexOf(".")+1);

            switch (ext){
                case "txt" :
                    dataList.add(readDefalut(file));
                    break;
                case "csv" :
                    dataList.add(readDefalut(file));
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
    public  DataDTO readDefalut(File file) {
        DataDTO dto = new DataDTO();
        try (BufferedReader br = new BufferedReader(new FileReader(file));){
            String s;

            while ((s = br.readLine()) != null) {
                String[] item = s.split("\\|");
                dto.setRegDtm(item[0]);
                dto.setJoinCnt(item[1]);
                dto.setResignCnt(item[2]);
                dto.setPayAmt(item[3]);
                dto.setUsedAmt(item[4]);
                dto.setSalesAmt(item[5]);
            }
        }catch (FileNotFoundException e){
            log.info(e.getMessage());
        }catch (IOException e){
            log.info(e.getMessage());
        }

        return dto;
    }
}
