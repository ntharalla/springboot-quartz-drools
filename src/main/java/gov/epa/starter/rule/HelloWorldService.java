package gov.epa.starter.rule;

import java.util.List;

import gov.epa.starter.domain.Message;

public interface HelloWorldService {

	void executeHelloWorldRule(List<Message> msgs);

}
