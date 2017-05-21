package nl.parkhaven.wasschema.modules.mail;

import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.MailService;

import java.util.List;
import java.util.stream.Collectors;

class SendMailToUsersJob implements Runnable {

    private EmailDao emailDao;
    private MailService mailSenderService;

    SendMailToUsersJob(EmailDao emailDao, MailService mailSender) {
        this.emailDao = emailDao;
        this.mailSenderService = mailSender;
    }

    @Override
    public void run() {
        List<User> users = emailDao.getUsersThatHaveAnAppointmentIn3Hours().stream().filter(user -> user.getEmail() != null).collect(Collectors.toList());
        for (User user : users) {
            mailSenderService.sendMailThreeHoursReminder(user);
        }
    }

}
