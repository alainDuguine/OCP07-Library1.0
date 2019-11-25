package org.alain.library.batch;

import io.swagger.client.api.LoanApi;
import io.swagger.client.api.UserApi;
import io.swagger.client.model.LoanDto;
import io.swagger.client.model.UserDto;
import org.alain.library.batch.configuration.EmailConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Component
public class Controller {

    private final LoanApi loanApi;
    private final UserApi userApi;
    private final EmailConfiguration emailConfiguration;

    @Value("${batch.username}")
    private String BATCH_USERNAME;
    @Value("${batch.password}")
    private String BATCH_PASSWORD;

    public Controller(LoanApi loanApi, UserApi userApi, EmailConfiguration emailConfiguration) {
        this.loanApi = loanApi;
        this.userApi = userApi;
        this.emailConfiguration = emailConfiguration;
    }

    @Scheduled(cron = "${mailScheduling.delay}" )
    public void getLateLoan() throws IOException {
        String authorisation = "Basic " + Base64.getEncoder().encodeToString((BATCH_USERNAME+":"+BATCH_PASSWORD).getBytes());
        List<LoanDto> loanDtoList = loanApi.checkAndGetLateLoans(authorisation).execute().body();
        if (loanDtoList == null || loanDtoList.isEmpty()){
            System.out.println("No late loans to send email to");
        }else {
            this.sendEmail(loanDtoList);
        }
//        this.sendEmail();
    }

    private void sendEmail(List<LoanDto> loanDtoList) throws IOException {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfiguration.getHost());
        mailSender.setPort(this.emailConfiguration.getPort());
        mailSender.setUsername(this.emailConfiguration.getUsername());
        mailSender.setPassword(this.emailConfiguration.getPassword());

//        SimpleMailMessage mailMessage = new SimpleMailMessage();
////            UserDto user = userApi.getUser(loan.getUserId()).execute().body();
//            mailMessage.setFrom(BATCH_USERNAME);
////            mailMessage.setTo(user.getEmail().toLowerCase());
//            mailMessage.setTo("1fb592b2dc-564e4f@inbox.mailtrap.io");
//            mailMessage.setSubject("Open Library.fr : Votre emprunt est en retard !");
//            mailMessage.setText(
//                    "Bonjour !\n" +
//                    "Le 12/12/2019, vous avez emprunté le livre suivant :\n" +
//                    "\tTitre : 20000 lieux sous les mers\n" +
//                    "\tNuméro : 123456789 \n" +
//                    "\nL'emprunt est terminé depuis le : 12/12/2019 et le livre ne nous a pas été retourné" +
//                    "\nMerci de nous retourner ce livre dès que possible, afin que d'autres personnes puissent en profiter également.");
//            mailSender.send(mailMessage);

        for(LoanDto loan : loanDtoList) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            UserDto user = userApi.getUser(loan.getUserId()).execute().body();
            mailMessage.setFrom(BATCH_USERNAME);
//            mailMessage.setTo(user.getEmail().toLowerCase());
            mailMessage.setTo("1fb592b2dc-564e4f@inbox.mailtrap.io");
            mailMessage.setSubject("Open Library.fr : Votre emprunt est en retard !");
            mailMessage.setText(
                    "Bonjour !\n" +
                    "Le " + loan.getStartDate() + ", vous avez emprunté le livre suivant :\n" +
                    "\tTitre : " + loan.getBookCopy().getBook().getTitle() + "\n" +
                    "\tNuméro : " + loan.getBookCopy().getBarcode() + "\n" +
                    "\nL'emprunt est terminé depuis le : " + loan.getEndDate() + " et le livre ne nous a pas été retourné" +
                    "\nMerci de nous retourner ce livre dès que possible, afin que d'autres personnes puissent en profiter également.");
            mailSender.send(mailMessage);
        }
    }
}
