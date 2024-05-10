package sendemail;

import java.util.List;

public class Email {

    private String from;
    private List<String> to;
    private String subject;
    private String content;

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return this.to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
