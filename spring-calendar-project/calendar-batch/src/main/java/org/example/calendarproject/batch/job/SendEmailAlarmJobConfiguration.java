package org.example.calendarproject.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SendEmailAlarmJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private static final int CHUNK_SIZE = 3;

    @Bean
    public Job sendEmailAlarmJob(
            Step sendScheduleAlarmStep,
            Step sendEngagementAlarmStep
    ) {
        return jobBuilderFactory.get("sendEmailAlarmJob")
                .start(sendScheduleAlarmStep)
                .next(sendEngagementAlarmStep)
                .build();
    }

    @Bean
    public Step sendScheduleAlarmStep(
            ItemReader<SendMailBatchReq> sendScheduleAlarmReader,
            ItemWriter<SendMailBatchReq> sendAlarmWriter
    ) {
        return stepBuilderFactory.get("sendScheduleAlarmStep")
                .<SendMailBatchReq, SendMailBatchReq>chunk(CHUNK_SIZE)
                .reader(sendScheduleAlarmReader)
                .writer(sendAlarmWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step sendEngagementAlarmStep(
            ItemReader<SendMailBatchReq> sendEngagementAlarmReader,
            ItemWriter<SendMailBatchReq> sendAlarmWriter
    ) {
        return stepBuilderFactory.get("sendEngagementAlarmStep")
                .<SendMailBatchReq, SendMailBatchReq>chunk(CHUNK_SIZE)
                .reader(sendEngagementAlarmReader)
                .writer(sendAlarmWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public JdbcCursorItemReader<SendMailBatchReq> sendScheduleAlarmReader(){
        return new JdbcCursorItemReaderBuilder<SendMailBatchReq>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(SendMailBatchReq.class))
                .sql("select s.id, s.start_at, s.title, u.email as user_mail from schedules s inner join users u on s.writer_id=u.id where s.start_at >= now() + interval 10 minute and s.start_at < now() + interval 11 minute")
                .name("jdbcCursorItemReader")
                .build();
    }

    @Bean
    public JdbcCursorItemReader<SendMailBatchReq> sendEngagementAlarmReader(){
        return new JdbcCursorItemReaderBuilder<SendMailBatchReq>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(SendMailBatchReq.class))
                .sql("select s.id, s.start_at, s.title, u.email as user_mail from engagements e inner join schedules s on e.event_id=s.id inner join users u on s.writer_id=u.id  where s.start_at >= now() + interval 10 minute and s.start_at < now() + interval 11 minute and e.request_status = 'ACCEPTED'")
                .name("jdbcCursorItemReader")
                .build();
    }

    @Bean
    public ItemWriter<SendMailBatchReq> sendEmailAlarmWriter(){
        return list ->{
            list.forEach(l->System.out.println(l.toString()));
            new RestTemplate().postForObject(
                "http://localhost:8080/api/batch/mail", list, Object.class
        );
        };
    }

}
