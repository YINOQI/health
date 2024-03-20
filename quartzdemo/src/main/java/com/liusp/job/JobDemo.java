package com.liusp.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
/**
 * 自定义Job
 */
@Component
public class JobDemo {
    @Scheduled(cron = "0/10 * * * * ?")
    public void run(){
        System.out.println("job execute...");
    }
}