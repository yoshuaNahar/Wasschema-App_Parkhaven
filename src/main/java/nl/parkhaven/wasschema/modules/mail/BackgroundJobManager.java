package nl.parkhaven.wasschema.modules.mail;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Autowired;

import nl.parkhaven.wasschema.modules.util.MailService;

import static nl.parkhaven.wasschema.modules.util.Misc.TIMES_ARRAY;

@WebListener
public class BackgroundJobManager implements ServletContextListener {

	private ScheduledExecutorService scheduler;

	@Autowired
	private EmailDao emailDao;

	@Autowired
	private MailService mailSenderService;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new SendMailToWashersJob(emailDao, mailSenderService), initialDelay(), 3600,
				TimeUnit.SECONDS);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		scheduler.shutdownNow();
	}

	// The initial delay before the timer 10800 seconds starts. This is needed
	// because I should be able to start the application at every moment!
	// It checks the nearest time and the amount of seconds to that time is the
	// initial delay.
	private long initialDelay() {
		final LocalTime now = LocalTime.now();

		int i;
		for (i = 0; i < TIMES_ARRAY.length; i++) {
			if (now.isBefore(TIMES_ARRAY[i])) {
				break;
			}
		}

		if (i == 13) {
			i = 0;
		}

		return now.until(TIMES_ARRAY[i], ChronoUnit.SECONDS);
	}

}
