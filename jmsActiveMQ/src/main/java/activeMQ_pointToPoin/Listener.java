package activeMQ_pointToPoin;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息监听，第二种consumer
 */
public class Listener implements MessageListener{
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("收到的消息 : " + ((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
