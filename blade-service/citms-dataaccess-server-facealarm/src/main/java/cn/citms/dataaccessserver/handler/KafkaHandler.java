package cn.citms.dataaccessserver.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * <p>
 * kafka消息处理器
 * </p>
 */
@Component
@Slf4j
public class KafkaHandler {

    @KafkaListener(topics = "test1", containerFactory = "ackContainerFactory")
    public void handleMessage(ConsumerRecord record, Acknowledgment acknowledgment) {
        try {
            String message = (String) record.value();
            log.info("kafka收到消息: {}", message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 手动提交 offset
            acknowledgment.acknowledge();
        }
    }

   /* @KafkaListener(topics = KafkaConsts.TOPIC_TEST, containerFactory = "kafkaListenerContainerFactory")
    public void handleMessage(List<ConsumerRecord> records, Acknowledgment acknowledgment) {
        try {
            records.forEach(record->{
                String message = (String) record.value();
                log.info("收到消息: {}", message);
                    }
            );

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 手动提交 offset
            acknowledgment.acknowledge();
        }
    }*/
}
