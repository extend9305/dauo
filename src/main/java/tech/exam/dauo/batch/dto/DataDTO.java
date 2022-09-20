package tech.exam.dauo.batch.dto;

public class DataDTO {
    private String regDtm;
    private String joinCnt;
    private String resignCnt;
    private String payAmt;
    private String usedAmt;
    private String salesAmt;

    public DataDTO() {
    }

    public DataDTO(String regDtm, String joinCnt, String resignCnt, String payAmt, String usedAmt, String salesAmt) {
        this.regDtm = regDtm;
        this.joinCnt = joinCnt;
        this.resignCnt = resignCnt;
        this.payAmt = payAmt;
        this.usedAmt = usedAmt;
        this.salesAmt = salesAmt;
    }

    public String getRegDtm() {
        return regDtm;
    }

    public void setRegDtm(String regDtm) {
        this.regDtm = regDtm;
    }

    public String getJoinCnt() {
        return joinCnt;
    }

    public void setJoinCnt(String joinCnt) {
        this.joinCnt = joinCnt;
    }

    public String getResignCnt() {
        return resignCnt;
    }

    public void setResignCnt(String resignCnt) {
        this.resignCnt = resignCnt;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public String getUsedAmt() {
        return usedAmt;
    }

    public void setUsedAmt(String usedAmt) {
        this.usedAmt = usedAmt;
    }

    public String getSalesAmt() {
        return salesAmt;
    }

    public void setSalesAmt(String salesAmt) {
        this.salesAmt = salesAmt;
    }
}
