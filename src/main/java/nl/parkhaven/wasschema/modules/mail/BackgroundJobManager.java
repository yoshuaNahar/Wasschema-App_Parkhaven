package nl.parkhaven.wasschema.modules.mail;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public final class BackgroundJobManager implements ServletContextListener {

	private ScheduledExecutorService scheduler;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new SendMailToWashers(), initialDelay(), 3600, TimeUnit.SECONDS);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		scheduler.shutdownNow();
	}

	// The initial delay before the timer 10800 seconds starts. This is needed because I should be able to start the application at every moment!
	// It checks the nearest time and the amount of seconds to that time is the initial delay.
	private long initialDelay() {
		final LocalTime[] timeArray = { LocalTime.of(5, 30, 0), LocalTime.of(7, 0, 0), LocalTime.of(8, 30, 0),
				LocalTime.of(10, 0, 0), LocalTime.of(11, 30, 0), LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0),
				LocalTime.of(16, 0, 0), LocalTime.of(17, 30, 0), LocalTime.of(19, 0, 0), LocalTime.of(20, 30, 0),
				LocalTime.of(22, 0, 0), LocalTime.of(23, 30, 0) };

		final LocalTime now = LocalTime.now();

		int i;
		for (i = 0; i < timeArray.length; i++) {
			if (now.isBefore(timeArray[i])) {
				break;
			}
		}
 
		return now.until(timeArray[i], ChronoUnit.SECONDS);
	}

}
