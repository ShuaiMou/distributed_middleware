package activeMQ_publishSubscribe;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息监听，发布订阅模式
 * 消费者1
 */
public class Listener1 implements MessageListener{
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("收订阅者1收到的消息 : " + ((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
