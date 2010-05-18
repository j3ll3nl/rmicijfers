
import java.text.SimpleDateFormat;
import java.util.*;

public class Response extends HashMap<String, String> {
    private Control control;
    private String statusLine;
    private String generalHeader;
    private String responseHeader;
    private String entityHeader;
    private String contentType;
    private byte[] entityBody;
    private int serviceLogNr;

    public Response(int seviceLogNr, Control contr){
        this.control = contr;
        this.serviceLogNr = seviceLogNr;
    }
    
    public synchronized byte[] getBytes(){
        this.setGeneralHeader();
        this.setResponseHeader();
        this.setEntityHeader();

        String fullResponse = "";

        fullResponse = this.getStatusLine() + "\n" +  this.getGeneralHeader() + "\n" + this.getResponseHeader() + "\n" + this.getEntityHeader();
        fullResponse += (this.getEntityBody() != null) ? "\n" + this.contentType : "";
        fullResponse += (this.getEntityBody() != null) ? "\nContent-Length:" + this.getEntityBody().length : "";
        fullResponse += (this.getEntityBody() != null) ? "\n\n" : "";

        if (this.getEntityBody() != null) {

            byte[] head = fullResponse.getBytes();
            byte[] body = getEntityBody();

            byte[] content = new byte[head.length + body.length];

            int c = 0;

            for (byte h : head) {
                content[c] = h;
                c++;
            }

            for (byte b : body) {
                content[c] = b;
                c++;
            }
            if (Main.debug) System.out.println(new String(head));
            control.log(this.serviceLogNr,"\n" + new String(head) + "\n");

            return content;
        } else {
            if (Main.debug)  System.out.println(fullResponse);
            control.log(this.serviceLogNr,"\n" + fullResponse + "\n");

            return fullResponse.getBytes();
        }
    }

    public synchronized void setStatusLine(String statusCode, String reasonPhrase) {
        this.statusLine = "HTTP/1.1 " + statusCode + " " + reasonPhrase + " ";
    }

    public synchronized void setStatusLine(String statusCode) {
        this.statusLine = "HTTP/1.1 " + statusCode + " reason unknown ";
    }

    public synchronized String getStatusLine() {
        return this.statusLine;
    }

    public synchronized void setGeneralHeader() {
        Date d = new Date();
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("EEE, d MMM yyyy H:mm:ss",Locale.UK);

        // add HTTPdate
        this.generalHeader = "Date:" + formatter.format(d) + " GMT";// Tue, 15 Nov 1994 08:12:31 GMT;
    }

    public synchronized String getGeneralHeader() {
        return this.generalHeader;
    }
    public synchronized void setResponseHeader() {
        this.responseHeader = "Server:1337SERV/0.1";
    }

    public synchronized String getResponseHeader() {
        return this.responseHeader;
    }

    public synchronized void setEntityHeader() {
        this.entityHeader = "Allow:GET,HEAD";
    }

    public synchronized String getEntityHeader() {
        return this.entityHeader;
    }
    
    public synchronized void setEntityBody(byte[] s, String filetype,String fileName) {
        this.contentType = "Content-Type:" + filetype;
        this.contentType += "\nContent-Name:" + fileName;
        this.entityBody = s;
    }

    public synchronized byte[] getEntityBody() {
        return this.entityBody;
    }

    @Override
    protected synchronized void finalize() throws Throwable {
        if (Main.debug) System.out.println("Debug: Response finalize(): Response gefinalized."); //debug regel die alleen weergegeven word als Main.debug op true staat
        control.log(this.serviceLogNr,"Response gefinalized.");
    }
}
