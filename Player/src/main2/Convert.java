package main2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.axet.vget.VGet;


/**
 *
 * @author Manindar
 */
public class Convert implements Runnable {
	 private static final Logger LOG = Logger.getLogger(Convert.class.getName());
	 String path;
	 public Convert(String name){

			path = name;

		}
	@Override
	public void run() {
		   
        try {
            String line;
            String mp4File = "C:\\hai.mp4";
            String mp3File = "C:\\Convert2.mp4";

            // ffmpeg -i input.mp4 output.avi as it's on www.ffmpeg.org
            String cmd = "ffmpeg -i " + path + " " + mp3File;
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor();
            System.out.println("Video converted successfully!");
            in.close();
        } catch (IOException | InterruptedException e) {
            LOG.log(Level.SEVERE, null, e);
        }
		
	}
        
        
    }

