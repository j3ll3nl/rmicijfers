import java.io.OutputStream;
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
 * This class is used to redirect any output directly towards the diagnostic outputfield in {@link GUI_Frame} using the method
 * {@link GUI_Frame#setText(String)}
 *
 */
public class MyOutputStream
extends OutputStream
{ 
  private GUI_Frame frame = null;
  
  /**
   * This creates an instance of this class.
   * @param mf contains an instance of {@link GUI_Frame}
   */
  public MyOutputStream(GUI_Frame mf)
  { 
	  frame = mf;
  }
  
  /**
   * This function executes {@link GUI_Frame#setText(String)} with as parameter 1 int casted to char.
   * @param b is the integer representation of 1 byte. 
   */
  public void write(int b)
  throws IOException
  { 
	  frame.setText(""+(char)b);
  }
}

