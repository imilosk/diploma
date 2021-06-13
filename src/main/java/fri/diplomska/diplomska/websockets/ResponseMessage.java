package fri.diplomska.diplomska.websockets;


public class ResponseMessage {

    private String progress;
    private String message;

    public ResponseMessage() {
    }

    public ResponseMessage(String userName, String message) {
        super();
        this.progress = userName;
        this.message = message;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "userName='" + progress + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}