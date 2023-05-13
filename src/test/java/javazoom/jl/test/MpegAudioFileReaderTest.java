/*
 *   MpegAudioFileReaderTest - JavaZOOM : http://www.javazoom.net
 *
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
 *
 */
package javazoom.jl.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Properties;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * MpegAudioFileReader unit test.
 * It matches test.mp3 properties to test.mp3.properties expected results.
 * As we don't ship test.mp3, you have to generate your own test.mp3.properties
 * Uncomment out = System.out; in setUp() method to generated it on stdout from 
 * your own MP3 file.
 */
public class MpegAudioFileReaderTest {

	private static String basefile=null;
	private static String baseurl=null;
	private static String filename=null;
	private static String fileurl=null;
	private static String name=null;
	private static Properties props = null;
	private static PrintStream out = null;

	@BeforeAll
	protected static void setUp() throws Exception {
		props = new Properties();
		InputStream pin = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.mp3.properties");
		props.load(pin);
		basefile = props.getProperty("basefile");
		baseurl = new File(basefile).toURI().toURL().toString();
		name = props.getProperty("filename");
		filename = basefile + name;	
		fileurl = baseurl + name;	
		out = System.out;
	}

	@Test
	@DisplayName("test get AudioFileFormat")
	public void testGetAudioFileFormat() {
			_testGetAudioFileFormatFile();
			_testGetAudioFileFormatURL();
			_testGetAudioFileFormatInputStream();
	}

	@Test
	@DisplayName("test get AudioInputStream")
	public void testGetAudioInputStream() {
			_testGetAudioInputStreamFile();
			_testGetAudioInputStreamURL();
			_testGetAudioInputStreamInputStream();			
	}
	
	/*
	 * Test for AudioFileFormat getAudioFileFormat(File)
	 */
	public void _testGetAudioFileFormatFile() {
		if (out != null) out.println("*** testGetAudioFileFormatFile ***");
		try {
			File file = new File(filename);
			AudioFileFormat baseFileFormat= AudioSystem.getAudioFileFormat(file);			
			dumpAudioFileFormat(baseFileFormat,out,file.toString());
			out.println("FrameLength: " + baseFileFormat.getFrameLength());
			out.println("ByteLength: " + baseFileFormat.getByteLength());
		}
		catch (UnsupportedAudioFileException | IOException e) {
			fail("testGetAudioFileFormatFile: " + e.getMessage());
		}
	}
	
	/*
	 * Test for AudioFileFormat getAudioFileFormat(URL)
	 */
	public void _testGetAudioFileFormatURL() {
		if (out != null) out.println("*** testGetAudioFileFormatURL ***");
		try {
			URL url = new URL(fileurl);
			AudioFileFormat baseFileFormat= AudioSystem.getAudioFileFormat(url);			
			dumpAudioFileFormat(baseFileFormat,out,url.toString());
		}
		catch (UnsupportedAudioFileException | IOException e) {
			fail("testGetAudioFileFormatURL: " + e.getMessage());
		}
	}

	/*
	 * Test for AudioFileFormat getAudioFileFormat(InputStream)
	 */
	public void _testGetAudioFileFormatInputStream() {
		if (out != null) out.println("*** testGetAudioFileFormatInputStream ***");
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filename));
			AudioFileFormat baseFileFormat= AudioSystem.getAudioFileFormat(in);			
			dumpAudioFileFormat(baseFileFormat,out,in.toString());
			in.close();
		} catch (UnsupportedAudioFileException | IOException e) {
			fail("testGetAudioFileFormatInputStream: " + e.getMessage());
		}
	}

	/*
	 * Test for AudioInputStream getAudioInputStream(InputStream)
	 */
	public void _testGetAudioInputStreamInputStream() {
		if (out != null) out.println("*** testGetAudioInputStreamInputStream ***");
		try {
			InputStream fin = new BufferedInputStream(new FileInputStream(filename));
			AudioInputStream in= AudioSystem.getAudioInputStream(fin);		
			dumpAudioInputStream(in,out,fin.toString());
			fin.close();
			in.close();
		}
		catch (UnsupportedAudioFileException | IOException e) {
			fail("testGetAudioInputStreamInputStream: " + e.getMessage());
		}
	}
	
	/*
	 * Test for AudioInputStream getAudioInputStream(File)
	 */
	public void _testGetAudioInputStreamFile() {
		if (out != null) out.println("*** testGetAudioInputStreamFile ***");
		try {
			File file = new File(filename);
			AudioInputStream in= AudioSystem.getAudioInputStream(file);
			dumpAudioInputStream(in,out,file.toString());
			in.close();		
		}
		catch (UnsupportedAudioFileException | IOException e) {
			fail("testGetAudioInputStreamFile: " + e.getMessage());
		}
	}

	/*
	 * Test for AudioInputStream getAudioInputStream(URL)
	 */
	public void _testGetAudioInputStreamURL() {
		if (out != null) out.println("*** testGetAudioInputStreamURL ***");
		try {
			URL url = new URL(fileurl);
			AudioInputStream in= AudioSystem.getAudioInputStream(url);		
			dumpAudioInputStream(in,out,url.toString());
			in.close();
		}
		catch (UnsupportedAudioFileException | IOException e) {
			fail("testGetAudioInputStreamURL: " + e.getMessage());
		}
	}
	
	private void dumpAudioFileFormat(AudioFileFormat baseFileFormat, PrintStream out, String info) throws UnsupportedAudioFileException {
		AudioFormat baseFormat = baseFileFormat.getFormat(); 
		if (out != null) {
			// AudioFileFormat
			out.println("  -----  "+info+"  -----");		
			out.println("    ByteLength="+ baseFileFormat.getByteLength());		
			out.println("    FrameLength="+ baseFileFormat.getFrameLength());		
			out.println("    Type="+ baseFileFormat.getType());		
			// AudioFormat					
			out.println("    SourceFormat="+baseFormat.toString());
			out.println("    Channels="+ baseFormat.getChannels());		
			out.println("    FrameRate="+ baseFormat.getFrameRate());		
			out.println("    FrameSize="+ baseFormat.getFrameSize());		
			out.println("    SampleRate="+ baseFormat.getSampleRate());		
			out.println("    SampleSizeInBits="+ baseFormat.getSampleSizeInBits());		
			out.println("    Encoding="+ baseFormat.getEncoding());						
		}
	}
	
	private void dumpAudioInputStream(AudioInputStream in, PrintStream out, String info) throws IOException {
		AudioFormat baseFormat = in.getFormat();
		if (out != null) {
			out.println("  -----  "+info+"  -----");		
			out.println("    Available="+in.available());
			out.println("    FrameLength="+in.getFrameLength());
			// AudioFormat					
			out.println("    SourceFormat="+baseFormat.toString());
			out.println("    Channels="+ baseFormat.getChannels());		
			out.println("    FrameRate="+ baseFormat.getFrameRate());		
			out.println("    FrameSize="+ baseFormat.getFrameSize());		
			out.println("    SampleRate="+ baseFormat.getSampleRate());		
			out.println("    SampleSizeInBits="+ baseFormat.getSampleSizeInBits());		
			out.println("    Encoding="+ baseFormat.getEncoding());							
		}
	}

}
