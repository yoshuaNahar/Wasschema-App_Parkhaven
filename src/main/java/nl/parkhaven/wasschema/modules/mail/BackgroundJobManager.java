package nl.parkhaven.wasschema.modules.mail;

import nl.parkhaven.wasschema.modules.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static nl.parkhaven.wasschema.modules.util.Misc.TIMES_ARRAY;

@Service
public class BackgroundJobManager implements ApplicationListener<ContextRefreshedEvent> {

    // http://stackoverflow.com/questions/9173132/stop-scheduled-timer-when-shutdown-tomcat/9186070#9186070
    // This isnt good because I dont know how to let Spring initalize before ServletContextListener.
    // This class is called before DI which is no good

    // http://stackoverflow.com/questions/3994860/how-to-execute-jobs-just-after-spring-loads-application-context
    // This is what I use (second answer)
    private ScheduledExecutorService scheduler;

    @Autowired private EmailDao emailDao;
    @Autowired private MailService mailSenderService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new SendMailToUsersJob(emailDao, mailSenderService), initialDelay(), 5400,
                TimeUnit.SECONDS);
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
