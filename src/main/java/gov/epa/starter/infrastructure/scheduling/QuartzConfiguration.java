package gov.epa.starter.infrastructure.scheduling;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.collections4.CollectionUtils;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import gov.epa.starter.infrastructure.config.AutowiringSpringBeanJobFactory;

@Configuration
public class QuartzConfiguration {

	@Value("${cron.frequency.jobwithsimpletrigger}")
	private long frequency;
	
	@Autowired
	List<Trigger> listOfTrigger;

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setAutoStartup(true);
		factory.setDataSource(dataSource);
		factory.setJobFactory(jobFactory);
		factory.setQuartzProperties(quartzProperties());

		// Here we will set all the trigger beans we have defined.
		if (!CollectionUtils.isEmpty(listOfTrigger)) {
			factory.setTriggers(listOfTrigger.toArray(new Trigger[listOfTrigger.size()]));
		}

		return factory;
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean(name = "jobWithSimpleTriggerBean")
    public JobDetailFactoryBean sampleJob() {
        return createJobDetail(SampleJob.class);
    }
	
	@Bean(name = "jobWithSimpleTriggerBeanTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("jobWithSimpleTriggerBean") JobDetail jobDetail) {
    	return createTrigger(jobDetail,frequency,"sampleJob");
    }
	
	@Bean(name = "jobWithSimpleQuartzTriggerBean")
    public JobDetailFactoryBean sampleQuartzJob() {
        return createJobDetail(SampleQuartzJob.class);
    }
	
	@Bean(name = "jobWithSimpleQuartzTriggerBeanTrigger")
    public SimpleTriggerFactoryBean sampleQuartzJobTrigger(@Qualifier("jobWithSimpleQuartzTriggerBean") JobDetail jobDetail) {
    	return createTrigger(jobDetail,frequency,"sampleQuartzJob");
    }
	
	public static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs,String name) {
		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setName(name);
		factoryBean.setStartDelay(0L);
		factoryBean.setRepeatInterval(pollFrequencyMs);
		factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		// in case of misfire, ignore all missed triggers and continue :
		factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
		return factoryBean;
	}

	// Use this method for creating cron triggers instead of simple triggers:
	public static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setCronExpression(cronExpression);
		factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		return factoryBean;
	}

	public static JobDetailFactoryBean createJobDetail(Class jobClass) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(jobClass);
		// job has to be durable to be stored in DB:
		factoryBean.setDurability(true);
		return factoryBean;
	}
}
