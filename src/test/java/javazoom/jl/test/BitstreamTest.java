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

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Bitstream unit test.
 * It matches test.mp3 properties to test.mp3.properties expected results.
 * As we don't ship test.mp3, you have to generate your own test.mp3.properties
 * Uncomment out = System.out; in setUp() method to generated it on stdout from 
 * your own MP3 file.
 * @since 0.4
 */
public class BitstreamTest {

	private static String basefile = null;
	private static String name = null;
	private static String filename = null;
	private static PrintStream out = null;
	private static Properties props = null;
	private static FileInputStream mp3in = null;
	private static Bitstream in = null;

	@BeforeAll
	protected static void setUp() throws Exception {
		props = new Properties();
		InputStream pin = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.mp3.properties");
		props.load(pin);
		basefile = props.getProperty("basefile");
		name = props.getProperty("filename");
		filename = basefile + name;
		mp3in = new FileInputStream(filename);
		in = new Bitstream(mp3in);
		out = System.out;
	}

	/*
	 * @see TestCase#tearDown()
	 */
	@AfterAll
	protected static void tearDown() throws Exception {
		in.close();
		mp3in.close();
	}

	@Test
	@DisplayName("test stream")
	public void testStream() {
		try {
			InputStream id3in = in.getRawID3v2();
			int size = id3in.available();
			Header header = in.readFrame();
			if (out != null) {
				out.println("--- "+filename+" ---");
				out.println("ID3v2Size="+size);
				out.println("version="+header.version());
				out.println("version_string="+header.version_string());
				out.println("layer="+header.layer());
				out.println("frequency="+header.frequency());
				out.println("frequency_string="+header.sample_frequency_string());
				out.println("bitrate="+header.bitrate());
				out.println("bitrate_string="+header.bitrate_string());
				out.println("mode="+header.mode());
				out.println("mode_string="+header.mode_string());
				out.println("slots="+header.slots());
				out.println("vbr="+header.vbr());
				out.println("vbr_scale="+header.vbr_scale());
				out.println("max_number_of_frames="+header.max_number_of_frames(mp3in.available()));
				out.println("min_number_of_frames="+header.min_number_of_frames(mp3in.available()));
				out.println("ms_per_frame="+header.ms_per_frame());
				out.println("frames_per_second="+(float) ((1.0 / (header.ms_per_frame())) * 1000.0));
				out.println("total_ms="+header.total_ms(mp3in.available()));
				out.println("SyncHeader="+header.getSyncHeader());
				out.println("checksums="+header.checksums());
				out.println("copyright="+header.copyright());
				out.println("original="+header.original());
				out.println("padding="+header.padding());
				out.println("framesize="+header.calculate_framesize());
				out.println("number_of_subbands="+header.number_of_subbands());
			}
			in.closeFrame();
		}
		catch (BitstreamException e) {
			fail("BitstreamException : "+e.getMessage());
		}		
		catch (IOException e) {
			fail("IOException : "+e.getMessage());
		}
	}
}
