package dell.koa.tools.koaDuty.koadutyjob.koadutyjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KoaJobRunner implements CommandLineRunner {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job transactionProcessingJob;

    public static final Logger logger = LoggerFactory.getLogger(KoaJobRunner.class);
    //https://www.petrikainulainen.net/programming/spring-framework/spring-batch-tutorial-reading-information-from-a-database/
    @Override
    public void run(String... args) throws Exception {

        logger.info("Starting Batch Job with Unique Parameter");

        JobParameters param = new JobParametersBuilder().addString("JobID",
                String.valueOf(System.currentTimeMillis())).toJobParameters();
        try {
            jobLauncher.run(transactionProcessingJob, param);
        } catch (JobExecutionAlreadyRunningException e) {
            logger.error("JobExecutionAlreadyRunningException error");
        } catch (JobRestartException e) {
            logger.error("JobRestartException error");
        } catch (JobInstanceAlreadyCompleteException e) {
            logger.error("JobInstanceAlreadyCompleteException error");
        } catch (JobParametersInvalidException e) {
            logger.error("JobParametersInvalidException error");
        }

    }
}
