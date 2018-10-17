package gov.epa.starter.rule;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.starter.domain.Message;

@Service
public class HelloWorldRuleServiceImpl implements HelloWorldService{

	public static Logger logger = LoggerFactory.getLogger(HelloWorldRuleServiceImpl.class);

	@Autowired
	private KieContainer kContainer;


	@Override
	public void executeHelloWorldRule(List<Message> msgs) {
		
        // From the container, a session is created based on
        // its definition and configuration in the META-INF/kmodule.xml file
        KieSession kieSession = kContainer.newKieSession("HelloWorldKS");

        // Once the session is created, the application can interact with it
        // In this case it is setting a global as defined in the
        // org/drools/examples/helloworld/HelloWorld.drl file
        kieSession.setGlobal( "list",
                            new ArrayList<Object>() );

        // The application can also setup listeners
        //kieSession.addEventListener( new DebugAgendaEventListener() );
        //kieSession.addEventListener( new DebugRuleRuntimeEventListener() );
        


		msgs.forEach(m ->kieSession.insert(m));
		kieSession.fireAllRules();
		kieSession.dispose();
		logger.debug("Hello World Service completed" );		
	}

}
