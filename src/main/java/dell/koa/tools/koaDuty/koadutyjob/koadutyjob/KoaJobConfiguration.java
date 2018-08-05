package dell.koa.tools.koaDuty.koadutyjob.koadutyjob;

import dell.koa.tools.koaDuty.koadutyjob.koadutyjob.model.RequestParam;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class KoaJobConfiguration {
    @Value("${KOADUTY_API_URL}")
    private String apiUrl;

    @Value("${QUERY_ORG_NAME}")
    private String orgName;

    @Value("${ELASTIC_QUERY_INTERVAL_MINS}")
    private String interval;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;



    public static final Logger logger = LoggerFactory.getLogger(KoaJobConfiguration.class);

    @Bean
    public ListItemReader<RequestParam> reader() {
        RequestParam requestParam=new RequestParam();
        requestParam.setApiUrl(apiUrl);
        requestParam.setOrgName(orgName);
        requestParam.setInterval(interval);
        List<RequestParam> requestParamList=new ArrayList<>();
        requestParamList.add(requestParam);

        return new ListItemReader<RequestParam>(requestParamList);
    }


    @Bean
    public ItemWriter<RequestParam> writer() {
        return new ItemWriter<RequestParam>() {
            @Override
            public void write(List<? extends RequestParam> requestParams) throws Exception {
                requestParams.forEach(requestParam->{
                String apiUrl = requestParam.getApiUrl();
                String orgName = requestParam.getOrgName();
                String interval = requestParam.getInterval();

                try {
                    logger.info("Koaduty Alert Orchestration Started: LocalTime: " + new Date());
                    String koaDetyUrl=apiUrl + "alertorchestrator?logQueryInterval="+interval+"&orgName="+orgName;
                    logger.info("Using Koaduty API: " + koaDetyUrl);

                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost postRequest = new HttpPost(
                            koaDetyUrl);

                    postRequest.setEntity(null);

                    httpClient.execute(postRequest);
                    httpClient.getConnectionManager().shutdown();

                } catch (Exception e) {
                    logger.error("Error in executing koa duty job");
                }
                });
            }
        };
    }


    @Bean
    public JobLauncher jobLauncher (JobRepository jobRepo){
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepo);
        return simpleJobLauncher;
    }

        @Bean
        public Job transactionProcessingJob () {
            return jobBuilderFactory.get("transactionProcessingJob")
                    .flow(step1())
                    .end()
                    .build();
        }

        @Bean
        public Step step1 () {
            return stepBuilderFactory.get("step1")
                    .<RequestParam, RequestParam>chunk(1)
                    .reader(reader())
                    .writer(writer())
                    .build();
        }

    }
