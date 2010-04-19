import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.IOException;
/**
 * This class is used for reading the request from the client. This class is being instantiated by the {@link Service}
 **/
class MyInputStream extends FilterInputStream
{
	/**
	 * This creates an instance of this class.
	 * @param is contains the inPutStream.
	 */
	MyInputStream(InputStream is)
	{
		super(is);
	}

	/**
	 * This methods reads one line at a time and returns this line as a {@link String}
	 * @return String
	 * @throws IOException when reading fails
	 */
	public String readLine() throws IOException
	{
		int c1 = read();
		if (c1 == -1) throw new IOException("eof(1)");

		int c2 = read();
		if (c2 == -1) throw new IOException("eof(2)");

		String ret = "";
		while (c1 != '\r' || c2 != '\n')
		{
		  ret = ret + (char)c1;
		  c1 = c2;
		  c2 = read();
		  if (c2 == -1) throw new IOException("eof(3)");
		}

		return ret.length()>0 ? ret : null;
	}

}
