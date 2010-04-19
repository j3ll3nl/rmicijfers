import java.io.*;
import java.util.*;
import javax.activation.MimetypesFileTypeMap;

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
 * This Class will handle the Request-Types requested by the client
 */
public class Servlet
{
	private Response response;
	private String codeBase;

	/**
	 * This creates an instance of this class.
	 * @param rq the {@link Request} object
	 * @param rs the {@link Response} object
	 * @param codeBase will contain the directory where the codeBase is located
	 */
	public Servlet(Request rq, Response rs, String newCodeBase)
	{
		response = rs;
		codeBase = newCodeBase;
	}

	/** This method will be executed when an GET is requested. It will retrieve the file from the filesystem within the codeBase
	 * and adds the correct headers. The method will then set the correct code using {@link Response#setCODE(int)} and {@link Response#setREASON(String)}
	 * and stores the message in the Response Object using {@link Response#setBYTES(byte[])}
	 * @param uri this is an relative URI to the location of the requested file within the codebase
	 */
	public void doGET(String newuri)
	{
		String separator = File.separator;
		String uri = newuri.substring(1);
		String folder = uri.replace("/", separator);
		String contentType = "text/html";
		byte[] bodyBytes = null;
        File file = new File(codeBase+"" + uri);
        Date lastModifiedDate;
        
        
		try
		{
			System.out.print("Retrieving file: "+codeBase+""+separator+""+folder+"...");
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			
			bodyBytes = new byte[bis.available()];
			bis.read(bodyBytes);

			response.setCODE(200);
			response.setREASON("OK");
			System.out.println("200 - OK");
		}
		catch (FileNotFoundException e)
		{
			response.setCODE(404);
			response.setREASON("Not Found");
			System.out.println("404 - Not Found");
		}
		catch(Exception o)
		{
			System.out.println("Unknown Exception Error in: servlet.doGET()");
		}

		if(file.exists() && file.isFile())
		{
			lastModifiedDate = new Date(file.lastModified());	
			
			contentType = new MimetypesFileTypeMap().getContentType(file);
		}
		else
		{
			lastModifiedDate = new Date();		
		}
		response.setBYTES(bodyBytes, lastModifiedDate, contentType);
	}
}