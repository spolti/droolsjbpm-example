package com.droolsjbpm.examples.jmsapi;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.drools.core.command.runtime.process.SignalEventCommand;
import org.drools.core.command.runtime.process.StartProcessCommand;
import org.jbpm.services.task.commands.GetTaskAssignedAsPotentialOwnerCommand;
import org.kie.api.command.Command;
import org.kie.services.client.api.command.exception.RemoteCommunicationException;
import org.kie.services.client.serialization.JaxbSerializationProvider;
import org.kie.services.client.serialization.SerializationConstants;
import org.kie.services.client.serialization.SerializationException;
import org.kie.services.client.serialization.jaxb.impl.JaxbCommandsRequest;
import org.kie.services.client.serialization.jaxb.impl.JaxbCommandsResponse;

public class JmsAPI {

	private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
	private static final String DEFAULT_USERNAME = "myUser";
	private static final String DEFAULT_PASSWORD = "myPass";
	private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
	private static final String PROVIDER_URL = "remote://localhost:4447";
	private static final String DEPLOYMENT_ID = "org.kie.example:project1:1.0.0-SNAPSHOT"; //changing it according your deployment
	private static final String PROCESS_ID = "project1.test";
	private static final long PROCESS_INSTANCE_ID = 15;	// needed if you're doing an operation on // a PER_PROCESS_INSTANCE deployment


	public static void main(String args[]) throws MalformedURLException, NamingException {
		
		JmsAPI jms = new JmsAPI();
		
		// commandType = Start/Signal
		
		//Starting the Process
		jms.runCommandsJMS("start", null,null);
		//Log message: [stdout] (Thread-3) Process Started, waiting signal
		
		//Before to signal the process change the PROCESS_INSTANCE_ID variable to the correspondent Process ID.
		//Signaling the parent Process:
		jms.runCommandsJMS("signal", "GO","GO");

		
		//Signaling the Sug Process:
		jms.runCommandsJMS("signal", "END","END");
		//Log message: INFO  [stdout] (Thread-3) SubProcess Finished...
	}
	
	
	public void runCommandsJMS (String commandType, String signalName, String signalRef) throws NamingException {
		
		Context context = null;
		
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
        env.put(Context.SECURITY_PRINCIPAL, System.getProperty("username", DEFAULT_USERNAME));
        env.put(Context.SECURITY_CREDENTIALS, System.getProperty("password", DEFAULT_PASSWORD));
        context = new InitialContext(env);
		
		// Create JaxbCommandsRequest instance and add commands
		//start          
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("myVar", "ABCD");
	

		// Create JMS connection
		ConnectionFactory connectionFactory;
		try {
			connectionFactory = (ConnectionFactory) context.lookup(DEFAULT_CONNECTION_FACTORY);

		} catch (NamingException e) {
			throw new RuntimeException("Unable to lookup JMS connection factory.", e);

		}

		// Setup queues
		Queue sendQueue, responseQueue;
		try {
			sendQueue = (Queue) context.lookup("jms/queue/KIE.SESSION");
			responseQueue = (Queue) context.lookup("jms/queue/KIE.RESPONSE");

		} catch (NamingException ne) {
			throw new RuntimeException("Unable to lookup send or response queue", ne);

		}

		// Send command request
		String humanTaskUser = DEFAULT_USERNAME;
		JaxbCommandsResponse cmdResponse = null;

        //Command<?> cmd =  new SignalEventCommand( "GO", "GO");
       
		JaxbCommandsRequest req;
			
		if (commandType.toUpperCase().equals("START")) {
			Command<?> cmd =  new StartProcessCommand(PROCESS_ID);
			req = new JaxbCommandsRequest(DEPLOYMENT_ID, cmd);
			req.getCommands().add(new GetTaskAssignedAsPotentialOwnerCommand(DEFAULT_USERNAME,"en-US"));
			cmdResponse = sendJmsCommands(DEPLOYMENT_ID, null, humanTaskUser, req, connectionFactory,
					sendQueue, responseQueue, DEFAULT_USERNAME, DEFAULT_PASSWORD, 5);
			
		} else if (commandType.toUpperCase().equals("SIGNAL")) {
	
			Command<?> cmd =  new SignalEventCommand(PROCESS_INSTANCE_ID, signalName, signalRef);
			req = new JaxbCommandsRequest(DEPLOYMENT_ID, cmd);
			req.getCommands().add(new GetTaskAssignedAsPotentialOwnerCommand(DEFAULT_USERNAME,"en-US"));
			cmdResponse = sendJmsCommands(DEPLOYMENT_ID, PROCESS_INSTANCE_ID, humanTaskUser, req, connectionFactory,
					sendQueue, responseQueue, DEFAULT_USERNAME, DEFAULT_PASSWORD, 5);

		}
		
	}

	private static JaxbCommandsResponse sendJmsCommands(String deploymentId,
			Long processInstanceId, String user, JaxbCommandsRequest req,
			ConnectionFactory factory, Queue sendQueue, Queue responseQueue,
			String jmsUser, String jmsPassword, int timeout) {

		req.setProcessInstanceId(processInstanceId);

		req.setUser(user);

		Connection connection = null;
		Session session = null;
		String corrId = UUID.randomUUID().toString();
		String selector = "JMSCorrelationID = '" + corrId + "'";
		JaxbCommandsResponse cmdResponses = null;

		try {
			// setup
			MessageProducer producer;
			MessageConsumer consumer;

			try {
				if (jmsPassword != null) {
					connection = factory.createConnection(jmsUser, jmsPassword);
					System.out.println("user: "+jmsUser);
					System.out.println("pasword: "+jmsPassword);

				} else {
					connection = factory.createConnection(jmsUser, jmsPassword);

				}

				session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
				producer = session.createProducer(sendQueue);
				consumer = session.createConsumer(responseQueue, selector);
				connection.start();

			} catch (JMSException jmse) {
				throw new RemoteCommunicationException("Unable to setup a JMS connection.", jmse);

			}

			JaxbSerializationProvider serializationProvider = new JaxbSerializationProvider();
			BytesMessage msg = null;

			try {
				msg = session.createBytesMessage();
				// set properties
				msg.setJMSCorrelationID(corrId);
				msg.setIntProperty(SerializationConstants.SERIALIZATION_TYPE_PROPERTY_NAME,	JaxbSerializationProvider.JMS_SERIALIZATION_TYPE);

				Collection<Class<?>> extraJaxbClasses = serializationProvider.getExtraJaxbClasses();

				if (!extraJaxbClasses.isEmpty()) {

					String extraJaxbClassesPropertyValue = JaxbSerializationProvider.classSetToCommaSeperatedString(extraJaxbClasses);
					msg.setStringProperty(SerializationConstants.EXTRA_JAXB_CLASSES_PROPERTY_NAME,extraJaxbClassesPropertyValue);
					msg.setStringProperty(SerializationConstants.DEPLOYMENT_ID_PROPERTY_NAME,deploymentId);

				}

				// serialize request
				String xmlStr = serializationProvider.serialize(req);
				msg.writeUTF(xmlStr);

			} catch (JMSException jmse) {
				jmse.printStackTrace();

			} catch (SerializationException se) {
				se.printStackTrace();

			}

			// send
			try {
				producer.send(msg);

			} catch (JMSException jmse) {
				jmse.printStackTrace();
			}

			// receive
			Message response = null;

			try {
				response = consumer.receive(timeout);

			} catch (JMSException jmse) {
				jmse.printStackTrace();

			}

			if (response == null) {
				System.out.println("Response is empty, leaving");
				return null;

			}
			// extract response
			assert response != null : "Response is empty.";
			try {
				String xmlStr = ((BytesMessage) response).readUTF();
				cmdResponses = (JaxbCommandsResponse) serializationProvider	.deserialize(xmlStr);

			} catch (JMSException jmse) {
				jmse.printStackTrace();

			} catch (SerializationException se) {
				se.printStackTrace();

			}

			assert cmdResponses != null : "Jaxb Cmd Response was null!";

		} finally {
			if (connection != null) {
				try {
					connection.close();
					session.close();

				} catch (JMSException jmse) {
					jmse.printStackTrace();

				}
			}
		}
		return cmdResponses;

	}
}