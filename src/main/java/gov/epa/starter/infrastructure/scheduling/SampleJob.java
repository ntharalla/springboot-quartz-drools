package gov.epa.starter.infrastructure.scheduling;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class SampleJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());



	@Autowired
	private SampleJobService jobService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobService.executeSampleJob();
        logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());

        jobService.executeSampleJob();

        logger.info("Next job scheduled @ {}", context.getNextFireTime());
	}
	
	/*@Bean(name = "jobWithSimpleTriggerBean")
    public JobDetailFactoryBean sampleJob() {
        return QuartzConfiguration.createJobDetail(this.getClass());
    }
	
    @Bean(name = "jobWithSimpleTriggerBeanTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("jobWithSimpleTriggerBean") JobDetail jobDetail) {
    	return QuartzConfiguration.createTrigger(jobDetail,frequency,"sampleJob");
    }*/
}

