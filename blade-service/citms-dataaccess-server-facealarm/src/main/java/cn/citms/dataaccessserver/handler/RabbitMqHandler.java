package cn.citms.dataaccessserver.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 * 延迟队列处理器
 * </p>

 */
@Slf4j
@Component
public class RabbitMqHandler {

    @RabbitListener(queues = "xg-order")
    @RabbitHandler
    public void directHandlerManualAck(Message message, Channel channel) {
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        try {
//            log.info("延迟队列，手动ACK，接收消息：{}", JSONUtil.toJsonStr(messageStruct));
            // 通知 MQ 消息已被成功消费,可以ACK了
            final long deliveryTag = message.getMessageProperties().getDeliveryTag();
            channel.basicAck(deliveryTag, false);
            String msg = new String(message.getBody());
            JSONObject resultJson = JSON.parseObject(msg);

            log.info("接受到mq数据》》》》》：{}", resultJson);
        } catch (IOException e) {
            try {
                // 处理失败,重新压入MQ
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
