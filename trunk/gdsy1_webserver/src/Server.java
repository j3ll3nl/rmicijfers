
import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Server extends ServerSocket implements Runnable {
    private String contentbase;
    private HashMap<Service,Thread> services;
    private Control control;

    public Server(Control contr,InetAddress h, int p, String c) throws IOException{
        super(p,50, h);

        if (Main.debug) System.out.println("Debug: Server Constructor"); //debug regel die alleen weergegeven word als Main.debug op true staat

        this.contentbase = c;
        this.control = contr;

        this.services = new HashMap<Service,Thread>();
    }

    public boolean isAlive(){
        if(isAlive()){
            return true;
        }
        return false;
    }

    public void removeServive(){
        control.log(1,"removeService()");
        removeServive();
    }

    public void run()
	{   if (Main.debug) System.out.println("Debug: Server run()"); //debug regel die alleen weergegeven word als Main.debug op true staat

        try
		{
			while(true)
			{
				Socket serverSocket = super.accept();
				new Thread(new Service(control,this,serverSocket,contentbase)).start();
			}
		}
		catch(SocketException e)
		{
			control.log(1,"Een fout in de socket:" + e.getMessage());
		}
		catch(Exception e)
		{
			control.log(1,e.getMessage());
		} finally{}
	}

    public void addservice(Service s,Thread t) {
        this.services.put(s, t);
    }

    public void removeService(Service s) {
        this.services.remove(s);
    }

    public boolean threadsClosed() {
        for (Service s: this.services.keySet()) {
            Thread t = this.services.get(s);

            if (t.isAlive())
                return false;
        }
        return true;
    }

    public void closeThreads() {
      control.log(1,"Thread wordt gesloten");
      for (Service s: this.services.keySet()) {
            Thread t = this.services.get(s);

            if (t.isAlive())
                t.interrupt();
            this.removeService(s);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        control.log(1,"Server gefinalized.");
    }
}
