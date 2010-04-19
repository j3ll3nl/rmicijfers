import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.io.*;

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
 *This is the Actual Server which listens to a given port. this is being instantiated by {@link Control}.
 **/
class Server extends ServerSocket
implements Runnable
{
	private String codeBase;
	private String version;
	/**
	 * This creates an instance of this class.
	 * @param address contains the adress where the server is going to listen (always at the Local Host)
	 * @param port contains the port where the server should bind to
	 * @param codeBase contains the directory where the codeBase is located.
	 * @param version will contain the version of the software
	 * @throws IOException when the {@link ServerSocket} cannot be created
	 */
	public Server(InetAddress address, int port, String cb, String v) throws IOException
	{
		super(port, 50, address);
		version = v;
		codeBase = cb;
	}

	/**
	 * This method is member of {@link Thread}. This method will let the {@link ServerSocket} listen to the given port and
	 * instantiate an new {@link Service} everytime a client connects.
	 */
	public void run()
	{
		try
		{
			while(true)
			{
				System.out.println(this);
				Socket socket = super.accept();
				socket.setKeepAlive(true);
				new Thread(new Service(socket, codeBase, version)).start();
			}
		}
		catch(SocketException se)
		{
			System.out.println("An Socket Exception occurred");
		}
		catch(Exception e)
		{
			System.out.println("An unknown Exception occurred");
		}
	}
}