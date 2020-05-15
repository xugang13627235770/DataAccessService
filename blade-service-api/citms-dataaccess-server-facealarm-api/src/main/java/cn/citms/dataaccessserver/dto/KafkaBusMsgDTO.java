package cn.citms.dataaccessserver.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KafkaBusMsgDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;
    /**
     * 消息id
     */
    private String msgId;
    /**
     * 数据的采集时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date collectTime;
    /**
     * 数据平台
     */
    private String platformCode;
    /**
     * 数据分类
     */
    private String cateCode;
    /**
     * 原始数据
     */
    private T data;
}
