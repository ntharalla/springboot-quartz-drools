package gov.epa.starter.infrastructure.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SampleJobServiceImpl implements SampleJobService {

	public static Logger logger = LoggerFactory.getLogger(SampleJobServiceImpl.class);
	@Override
	public void executeSampleJob() {
		 logger.info("The sample job has begun...");
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            logger.error("Error while executing sample job", e);
        } finally {
            logger.info("Sample job has finished...");
        }
	}

}
