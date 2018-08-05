package dell.koa.tools.koaDuty.koadutyjob.koadutyjob;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableTask
@EnableBatchProcessing
public class KoadutyJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoadutyJobApplication.class, args).stop();
	}


}
