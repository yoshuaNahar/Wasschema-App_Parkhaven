package nl.parkhaven.wasschema.modules.appointment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static nl.parkhaven.wasschema.modules.util.Misc.TIMES_ARRAY;

@Service
public class AppointmentService {

    public static final String WASH_LIMIT_MET = "You have already met the wash limit of this week.";
    public static final String DATE_PASSED_OR_TAKEN = "Sorry, the date you entered already passed or has been taken.";
    public static final String REMOVE_APPOINTMENT_FAILED = "Failed to remove appointment. Check if given info is correct or if appointment isnt within 30 minutes!";
    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    private AppointmentDaoImpl appointmentDao;

    @Autowired
    public AppointmentService(AppointmentDaoImpl appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    public boolean dateFree(Appointment ap) {
        if (appointmentDao.read(ap).getUserId() == 0) {
            if (appointmentInThePast(ap, 5)) {
                return false;
            }
            return true;
        } else {
            logger.warn("Date taken. GebruikerId: " + ap.getUserId());
            return false;
        }
    }

    public void addAppointment(Appointment ap) {
        appointmentDao.update(ap);
    }

    public boolean removeAppointment(Appointment ap) {
        Integer userId = appointmentDao.checkBeforeDelete(ap);
        if (userId == null || userId != ap.getUserId()) {
            return false;
        }
        if (appointmentInThePast(ap, 30)) {
            return false;
        }
        return appointmentDao.delete(ap);
    }

    private boolean appointmentInThePast(Appointment ap, int timeInThePast) {
        if (ap.getDay() <= 7) {
            LocalDateTime now = LocalDateTime.now();
            LocalTime timeNowPlus = now.toLocalTime().plusMinutes(timeInThePast);
            int currentDayOfTheWeek = now.getDayOfWeek().getValue();
            if (ap.getDay() < currentDayOfTheWeek) {
                return true; // cant remove app in the past.
            } else if (currentDayOfTheWeek == ap.getDay()) {
                if (timeNowPlus.isAfter(TIMES_ARRAY[ap.getTime()])) {
                    return true;
                }
            }
        }
        return false;
    }

}
