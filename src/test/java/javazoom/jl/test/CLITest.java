/*
 * 11/19/2004 : 1.0 moved to LGPL. 
 * 01/01/2004 : Initial version by E.B javalayer@javazoom.net
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */
 
package javazoom.jl.test;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.jlp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Simple player unit test.
 * It takes around 3-6% of CPU and 10MB RAM under Win2K/PIII/1GHz/JDK1.5.0
 * It takes around 10-12% of CPU and 10MB RAM under Win2K/PIII/1GHz/JDK1.4.1
 * It takes around 08-10% of CPU and 10MB RAM under Win2K/PIII/1GHz/JDK1.3.1
 * @since 0.4 
 */
public class CLITest {

	private static Properties props = null;
	private static String filename = null;
	private static PrintStream out;
	
	@BeforeAll
	protected static void setUp() throws Exception {
		props = new Properties();
		InputStream pin = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.mp3.properties");
		props.load(pin);
		String basefile = props.getProperty("basefile");
		String name = props.getProperty("filename");
		filename = basefile + name;
		out = System.out;
	}

	@Test
	@DisplayName("test play")
	public void testPlay() {
		String[] args = new String[1];
		args[0] = filename;
		jlp player = jlp.createInstance(args);
		try {
			player.play();
			out.println("Play");
		}
		catch (JavaLayerException e) {
			fail("JavaLayerException : "+e.getMessage());
		}
	}

}
