package gov.epa.starter.infrastructure.scheduling;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.starter.domain.Message;
import gov.epa.starter.rule.HelloWorldService;

@Service
public class SampleQuartzJobServiceImpl implements SampleQuartzJobService {

	public static Logger logger = LoggerFactory.getLogger(SampleQuartzJobServiceImpl.class);
	
	@Autowired
	HelloWorldService helloWorldService;
	
	@Override
	public void executeSampleJob() {

		 logger.info("The sample quartz job has begun...");
        try {
        	 Thread.sleep(10000);
        	List<Message> msgs = new ArrayList();

            Message hello = new Message(1,"Hello World" ,Message.HELLO);
            Message goodbye = new Message(2,"GoodBye" ,Message.GOODBYE);
            msgs.add(hello);
            msgs.add(goodbye);
            
            helloWorldService.executeHelloWorldRule(msgs);
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            logger.error("Error while executing sample quartz job", e);
        } finally {
            logger.info("Sample quartz job has finished...");
        }
	}

}
