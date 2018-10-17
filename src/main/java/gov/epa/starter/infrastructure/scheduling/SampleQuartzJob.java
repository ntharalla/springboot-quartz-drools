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
public class SampleQuartzJob implements Job {

	Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	private SampleQuartzJobService jobQuartzService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobQuartzService.executeSampleJob();
        logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());

        jobQuartzService.executeSampleJob();

        logger.info("Next job scheduled @ {}", context.getNextFireTime());


	}

	
	
}


