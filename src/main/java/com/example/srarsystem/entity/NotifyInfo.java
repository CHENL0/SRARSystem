package com.example.srarsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Chen
 * @createTime 2019121 20:52
 * @description the class of notify
 */
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Entity
public class NotifyInfo {
    @Id
    private String notifyId;
    private String notifyFor;
    private String notifyBy;
    private String notifyType;
    private String notifyMessage;
    private String notifyMain;
    private String notifyDate;
    private int notifyStatus;

}
