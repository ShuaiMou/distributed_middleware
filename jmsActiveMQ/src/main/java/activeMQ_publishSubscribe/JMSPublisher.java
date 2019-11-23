package activeMQ_publishSubscribe;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSPublisher {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKEUPL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final int SENDNUM = 10;//发送消息的数量

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        MessageProducer messageProducer;

        connectionFactory = new ActiveMQConnectionFactory(JMSPublisher.USERNAME, JMSPublisher.PASSWORD, JMSPublisher.BROKEUPL);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            destination = session.createTopic("FirstTopic");

            messageProducer = session.createProducer(destination);
            sendMessage(session, messageProducer);
            session.commit();

        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (null != connection){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void sendMessage(Session session, MessageProducer messageProducer) throws JMSException {
        for (int i = 0; i < JMSPublisher.SENDNUM; i++){
            TextMessage message = session.createTextMessage("ActiveMQ 发布的消息 ：" + i);
            System.out.println("发布消息 ：" + "ActiveMQ 发布的消息 ：" + i);
            messageProducer.send(message);
        }
    }
}
