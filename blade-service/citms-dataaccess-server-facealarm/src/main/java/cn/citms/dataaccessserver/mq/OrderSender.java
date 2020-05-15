package cn.citms.dataaccessserver.mq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类OrderSender的实现描述：TODO 类实现描述 
 * @author DELL 2020/5/14 19:58
 */
@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    //发送消息方法调用：构建自定义对象消息
    public void sendOrder(Order order) {

        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(order.getMessageId());

        rabbitTemplate.convertAndSend("Udsp.Exchange.Default",
                "Udsp.Routing.#",
                JSONObject.toJSONString(order),         //消息体内容
                correlationData);   //消息唯一ID
    }
}
