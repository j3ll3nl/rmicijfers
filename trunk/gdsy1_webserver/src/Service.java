
import java.net.Socket;
import java.io.*;


public class Service implements Runnable{

    public Control control;
    private Socket serverSocket;
    private String contentbase;
    private Server server;
    private int serviceLogNr;

    public Service(Control contr,Server s,Socket sk, String contentbase) throws Exception {
        if (Main.debug) System.out.println("Debug: Service Constructor"); //debug regel die alleen weergegeven word als Main.debug op true staat
        
        serverSocket = sk;
        this.contentbase = contentbase;
        this.server = s;
        this.control = contr;

        // Zorgt dat de Server thread hem kent.
        this.server.addservice(this,Thread.currentThread());

        SocketInputStream input = new  SocketInputStream(serverSocket.getInputStream());
		Request request = new Request(control,input);

        request.addServiceLogNr(serviceLogNr);

        String requestTemp = request.toString();
        requestTemp = requestTemp.replaceAll("Host", "\nHost");
        requestTemp = requestTemp.replaceAll("Accept-Encoding", "\nAccept-Encoding");
        requestTemp = requestTemp.replaceAll("User-Agent", "\nUser-Agent");
        requestTemp = requestTemp.replaceAll("Connection", "\nConnection");
        requestTemp = requestTemp.replaceAll("UA-CPU", "\nUA-CPU");
        requestTemp = requestTemp.replaceAll("Cache-Control", "\nCache-Control");
        requestTemp = requestTemp.substring(1, requestTemp.length()-1);


       	serviceLogNr = control.log("\nMethod=" +request.getMETHOD()+"\nVersion=" + request.getVERSION()+"\n"+requestTemp+"\n");

        Servlet svlt = new Servlet(control,serviceLogNr,this.contentbase);
        
        OutputStream os = serverSocket.getOutputStream();

        //if (Main.debug) System.out.println("Content" + content.toString());

        os.write(svlt.service(request).getBytes());

        //os.write(head);
        //os.write(body);
        os.close();

        this.closeSocket();
    }

    public void closeSocket(){
        control.log(this.serviceLogNr,"Socket gesloten");
        this.server.removeService(this);
        control.log(this.serviceLogNr,"Service afgesloten");

    }

    public void run() {
        
    }

}
