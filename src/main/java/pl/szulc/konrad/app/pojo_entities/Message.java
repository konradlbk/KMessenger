package pl.szulc.konrad.app.pojo_entities;


public class Message {

    private Integer id;
    private String content;
    private String sender;
    private String receiver;
    private String sendDate;





    public Message(String content, String sender, String receiver, String sendDate) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.sendDate = sendDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {

            String s = "Content";
            StringBuilder underlinedContent = new StringBuilder();
            for (char c : s.toCharArray()) {
                underlinedContent.append(c).append('\u0332');
            }

        return "Date: " + sendDate + "\n" + "Sender: " + sender  +"\n"+
                "Receiver: " + receiver +"\n"+ "\n"+"|"+
                underlinedContent+"|:" + "\n" + "        "  + content + "\n" + "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ " +
                "_ _ _"+ "\n";
    }
}

