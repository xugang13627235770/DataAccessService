package cn.citms.dataaccessserver.config.kafka;

import cn.citms.dataaccessserver.mq.Order;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 类RabbitmqApplicationTests的实现描述：TODO 类实现描述 
 * @author DELL 2020/5/14 20:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaApplicationTests {

    @Resource
    private KafkaTemplate defaultKafkaTemplate;

    @Test
    public void testSend()
    {
        Order order = new Order();
        order.setId("201905200000002");
        order.setName("测试创建订单");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());

        defaultKafkaTemplate.send("test1", JSON.toJSONString(order));
    }
}
