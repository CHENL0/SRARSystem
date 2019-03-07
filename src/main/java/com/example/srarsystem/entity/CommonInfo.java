package com.example.srarsystem.entity;

/**
 * @author Chen
 * @createTime 02 12:53
 * @description
 */
public class CommonInfo {
    private int delFlag;
    private int delFlagAudit;

    public CommonInfo() {
        this.delFlag = 0;
        this.delFlagAudit = 0;
    }

    public int getDelFlagAudit() {
        return delFlagAudit;
    }

    public void setDelFlagAudit(int delFlagAudit) {
        this.delFlagAudit = delFlagAudit;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
