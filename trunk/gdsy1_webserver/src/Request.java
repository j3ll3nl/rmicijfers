import java.io.*;
import java.util.*;
import java.io.InputStream;

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
 *When an request is made by an client, this class is being instantiated by {@link Service}
 */
public class Request extends HashMap<String, String>
{
	private static final long serialVersionUID = 4820941590042145694L;
	private MyInputStream  myInputStream;
	private String method=null;
	private String uri=null;
	private String version=null;

	/**
	 * This will create an instance of this class. When instantiated, this constructor will collect the requestline and the headers
	 * from the {@link MyInputStream}.
	 * @param is contains an instance of {@link MyInputStream}
	 */
	public Request(InputStream is) throws IOException
	{
		myInputStream = new MyInputStream(is);
	
		String requestline = myInputStream.readLine();
		String[] splitline = requestline.split(" ");
		method = splitline[0];
		uri = splitline[1];
		version = splitline[2];
		// nu de headers
		String line = "";
		while ((line=myInputStream.readLine()) != null)
		{
			String[] header = line.split(":",2);
			put(header[0], header[1]);
		}
	}

	/**
	 * Returns the HTTP-Method.
	 * @return {@link String}
	 */
	public String getMETHOD()
	{
		return method;
	}

	/**
	 * Returns the URI that was requested by the client.
	 * @return {@link String}
	 */
	public String getURI()
	{
		return uri;
	}

	/**
	 * Returns the HTTP-Version
	 * @return {@link String}
	 */
	public String getVERSION()
	{
		return version;
	}

	/**
	 * Returns the Requestline plus the headers from the request made by the client.
	 * @return {@link String}
	 */
	public String toString()
	{
		String s;
		String h;
		s= "\r\n--------Request---------\r\n\r\n"+getMETHOD() + " " + getURI() + " " + getVERSION();

		for(String key : keySet())
		{
			h = key;
			s+="\n"+h+": "+get(h);
		}
		s+="\r\n\r\n-----End of Request-----\r\n";
		return s;
	}

}