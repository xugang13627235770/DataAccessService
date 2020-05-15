package cn.citms.dataaccessserver.mq;

import lombok.Data;

import java.io.Serializable;

/**
 * 类Order的实现描述：TODO 类实现描述 
 * @author DELL 2020/5/14 19:59
 */
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = -1844786744376934382L;

    private String id;
    private String name;
    private String messageId;  //存储消息发送的唯一标识

    public Order() {
        super();
    }

    public Order(String id, String name, String messageId) {
        super();
        this.id = id;
        this.name = name;
        this.messageId = messageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

}
