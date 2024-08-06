package br.com.project.futureBank.entity.DTOS;
import java.util.UUID;

public class EmailDTO {
    private UUID userId;
    private String emailTo;
    private String subject;
    private String text;

    public String getText() {
        return text;
    }

    public String getSubject() {
        return subject;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setText(String text) {
        this.text = text;
    }
}
