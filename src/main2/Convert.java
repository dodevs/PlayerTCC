package main2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Manindar
 */
public class Convert implements Runnable {
	private static final Logger LOG = Logger.getLogger(Convert.class.getName());
	String path;
	String form;

	public Convert(String name ) throws UnsupportedEncodingException {
		path = name;
		
	}

	@Override
	public void run() {
		try {

			// byte[] byteArray = path.getBytes();

			// String value = new String(byteArray, "ISO-8859-1");
			// System.out.println(value);

			String line;
			String value = path;
			String baseUrl = FilenameUtils.getFullPath(value);
			String mp3File = baseUrl + FilenameUtils.getBaseName(value) + ".mp3";
			
			String cmd = "ffmpeg -i \"" + value + "\" \"" + mp3File + "\"";
			System.out.println(cmd);
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cmd);
			builder.redirectErrorStream(true);
			Process p = builder.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				System.out.println(line);
			}
			
			Files.delete(Paths.get(path));


		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, null, e);
		}

	}

}
