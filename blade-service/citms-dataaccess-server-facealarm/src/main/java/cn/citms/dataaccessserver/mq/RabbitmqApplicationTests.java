package cn.citms.dataaccessserver.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * 类RabbitmqApplicationTests的实现描述：TODO 类实现描述 
 * @author DELL 2020/5/14 20:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

    @Autowired
    private OrderSender orderSender;

    @Test
    public void testSend()
    {
        Order order = new Order();
        order.setId("201905200000002");
        order.setName("测试创建订单");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        orderSender.sendOrder(order);
    }
}
