package com.example.jwtapp;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedular {

//	 @Scheduled(fixedRate = 1000)
//	   public void fixedRateSch() {
//	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//
//	      Date now = new Date();
//	      String strDate = sdf.format(now);
//	      System.out.println("Fixed Rate scheduler:: " + strDate);
//	   }

//	@Scheduled(fixedDelay = 1000, initialDelay = 5000)
//	public void fixedDelaySch() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//		Date now = new Date();
//		String strDate = sdf.format(now);
//		System.out.println("Fixed Delay scheduler:: " + strDate);
//	}

//	@Scheduled(cron = "0 53 11 19 4 *")
//	public void scheduleTaskUsingCronExpression() {
//	 
//	    long now = System.currentTimeMillis() / 1000;
//	    System.out.println(
//	      "schedule tasks using cron jobs - " + now);
//	}

	@Async
	@Scheduled(fixedRate = 3000)
	public void doSomething() {
		System.out.println("Scheduled job is running with thread: " + Thread.currentThread().getName() + " at time: "
				+ LocalDateTime.now());
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
