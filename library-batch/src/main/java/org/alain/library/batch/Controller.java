package org.alain.library.batch;

import io.swagger.client.api.LoanApi;
import io.swagger.client.model.LoanDto;
import lombok.extern.slf4j.Slf4j;
import org.alain.library.batch.configuration.EmailBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class Controller {

    private final LoanApi loanApi;
    private EmailBuilder emailBuilder;
    private JavaMailSender mailSender;

    @Value("${batch.username}")
    private String BATCH_USERNAME;
    @Value("${batch.password}")
    private String BATCH_PASSWORD;

    public Controller(LoanApi loanApi, EmailBuilder emailBuilder, JavaMailSender mailSender) {
        this.loanApi = loanApi;
        this.emailBuilder = emailBuilder;
        this.mailSender = mailSender;
    }

    @Scheduled(cron = "${mailScheduling.delay}" )
    public void getLateLoan() throws IOException, InterruptedException {
        String authorisation = "Basic " + Base64.getEncoder().encodeToString((BATCH_USERNAME+":"+BATCH_PASSWORD).getBytes());
        List<LoanDto> loanDtoList = loanApi.checkAndGetLateLoans(authorisation).execute().body();
        if (loanDtoList == null || loanDtoList.isEmpty()){
            log.info("No late loans to send email to");
        }else {
            for (LoanDto loan : loanDtoList){
                this.prepareAndSend(loan);
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }

//    private void sendEmail(LoanDto loanDto){
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(this.emailConfiguration.getHost());
//        mailSender.setPort(this.emailConfiguration.getPort());
//        mailSender.setUsername(this.emailConfiguration.getUsername());
//        mailSender.setPassword(this.emailConfiguration.getPassword());
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            UserDto user = userApi.getUser(loan.getUserId()).execute().body();
//            mailMessage.setFrom(BATCH_USERNAME);
////            mailMessage.setTo(user.getEmail().toLowerCase());
//            mailMessage.setTo("1fb592b2dc-564e4f@inbox.mailtrap.io");
//            mailMessage.setSubject("Open Library.fr : Votre emprunt est en retard !");
//            mailMessage.setText(
//                    "Bonjour !\n" +
//                    "Le " + loan.getStartDate() + ", vous avez emprunté le livre suivant :\n" +
//                    "\tTitre : " + loan.getBookCopy().getBook().getTitle() + "\n" +
//                    "\tNuméro : " + loan.getBookCopy().getBarcode() + "\n" +
//                    "\nL'emprunt est terminé depuis le : " + loan.getEndDate() + " et le livre ne nous a pas été retourné" +
//                    "\nMerci de nous retourner ce livre dès que possible, afin que d'autres personnes puissent en profiter également.");
//            mailSender.send(mailMessage);
//        }
//    }

    private void prepareAndSend(LoanDto loanDto){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(BATCH_USERNAME);
//            messageHelper.setTo(loanDto.getUserEmail());
            messageHelper.setTo(" 1fb592b2dc-564e4f@inbox.mailtrap.io");
            messageHelper.setSubject("OpenLibrary.fr : Votre emprunt est en retard !");
            String content = emailBuilder.build(loanDto);
            messageHelper.setText(content, true);
        };
        try{
            mailSender.send(messagePreparator);
        }catch (MailException e){
            log.error("Error while sending email about loan " + loanDto.getId() + "\n"+ e.getMessage());
        }
    }
}
