package com.flukebsk.quartzschedulerdatabase.action;


//import com.example.trainningSpring.service.HelloService;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@Log4j2
public class Test1 implements Job {

//    @Autowired
//    public HelloService helloService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        helloService.helloService();
        log.info(new Date());
    }
}
