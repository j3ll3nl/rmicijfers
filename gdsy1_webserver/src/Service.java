import java.net.Socket;
import java.io.*;
import java.io.IOException;

/*
This file is part of Diagnostic Webserver.

Diagnostic Webserver is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Diagnostic Webserver is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Diagnostic Webserver.  If not, see <http://www.gnu.org/licenses/>.

Copyright 2008, Henze Berkheij & Mark van de Haar
*/

/**
 * This class handles all incoming client requests. This class is being instantiated by {@link Server} when a client is connecting to the server.
 */
public class Service
implements Runnable
{
  InputStream myinputstream;
  private Socket socket = null;
  private String codeBase;
  private String version;

  /**
   * This creates an instance of this class.
   * @param socket contains the socket that wants to instantiate this class
   * @param codeBase contains the directory where the codeBase is located
   * @param version will contain the version of the software
   */
  public Service(Socket newSocket, String newCodeBase, String newVersion)
  {
	version = newVersion;
    socket = newSocket;
    codeBase = newCodeBase;
    
  }

  /**
   * This method is member of {@link Thread}. This method will handle the clients request and returns a reponse.
   */
  public void run()
  {
	  boolean quit = false; 
	  while(!quit)
	  {
	  	try
	    {
	    	socket.setSoTimeout(30000);
	    	System.out.println("Socket Timeout set to "+socket.getSoTimeout());		
    		myinputstream = new MyInputStream(socket.getInputStream());
		    Request request = new Request(myinputstream);
	     	
		    System.out.println(request);
	    	Response response = new Response(socket.getOutputStream(), version);
	        Servlet servlet = new Servlet(request, response, codeBase);
	        servlet.doGET(request.getURI());
	        System.out.println(response);
	    	response.send();
	    }
	    catch(IOException ioe)
	    {
	    	try 
	    	{
	    		System.out.println("Closing socket after Time-out");
				socket.close();
				quit = true;
			} 
	    	catch (IOException e) 
			{
	    		System.out.println("Failed to close socket after Socket Time-out");
	    		quit = true;
			}
	    }
	 }
  }
}
