
import java.io.IOException;
import java.util.*;



public class Request extends HashMap<String, String> {
    public Control control;
	private String method=null;
	private String uri=null;
	private String version=null;
    private SocketInputStream socketInputStream;
    private int serviceLogNr;
    private String fullRequest;

	public Request(Control contr, SocketInputStream sis) throws IOException
	{
        this.control = contr;

        socketInputStream = new SocketInputStream(sis);

		String requestline = socketInputStream.readLine();

        this.fullRequest = " " + requestline;

		String[] inputSplit = requestline.split(" ");
		method = inputSplit[0];
		uri = inputSplit[1];
		version = inputSplit[2];

		String lijn = "";
		while ((lijn=socketInputStream.readLine()) != null)
		{
			String[] headerlijn = lijn.split(":",2);
			put(headerlijn[0], headerlijn[1]);
		}
    
	}

	public synchronized String getMETHOD()
	{
		return method;
	}

	public synchronized String getRequestURI()
	{
		return uri;
	}

	public synchronized String getVERSION()
	{
		return version;
	}

    public void addServiceLogNr( int serviceLogNr) {
        this.serviceLogNr = serviceLogNr;
    }

    @Override
    protected void finalize() throws Throwable {
        if (Main.debug) System.out.println("Debug: Response finalize(): Request gefinalized."); //debug regel die alleen weergegeven word als Main.debug op true staat
        control.log(this.serviceLogNr,"Request gefinalized.");
    }
}
