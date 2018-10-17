package gov.epa.starter.rule;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("gov.epa.starter.rule")
public class HelloWorldRuleConfiguration {

	private static final String drlFile="hello_world.drl";
	
	@Bean
    public KieContainer kieContainer() {
        KieServices kieServices = KieServices.Factory.get();
        // From the kie services, a container is created from the classpath
       // return kieServices.getKieClasspathContainer();
        
        return kieServices.getKieClasspathContainer();
        

       /* KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(drlFile));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();

        return kieServices.newKieContainer(kieModule.getReleaseId());*/
        
    }
}
