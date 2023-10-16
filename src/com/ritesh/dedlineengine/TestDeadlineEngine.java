package com.ritesh.dedlineengine;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import com.ritesh.model.DeadLineTimerTask;
import com.ritesh.service.DeadlineEngine;
import com.ritesh.service.impl.DeadlineEngineImpl;

public class TestDeadlineEngine {

	public static void main(String[] args) {
		
		Consumer<Long> handler = inp ->  {
			Timer timer = new Timer();
			TimerTask task = new DeadLineTimerTask(inp);
			timer.schedule(task, 100);
		};
		
		DeadlineEngine deadlineEngine = new DeadlineEngineImpl();
		long schedule = deadlineEngine.schedule(1000);
		deadlineEngine.schedule(2100);
		deadlineEngine.schedule(2000);
		deadlineEngine.schedule(3000);

		System.out.println(deadlineEngine.poll(1200, handler , 2));
		System.out.println(deadlineEngine.poll(1500, handler , 3));
		System.out.println("size is " +deadlineEngine.size());
		System.out.println(deadlineEngine.cancel(schedule));
		System.out.println("size after cancelling task " +deadlineEngine.size());

	}

}
