package main2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.github.axet.vget.VGet;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Download implements Runnable {
	private String link;
	private String way;
	private int tipo;
	private int format;
	private String path2;

	String tru;
	String form;
	private static final Logger LOG = Logger.getLogger(Download.class.getName());

	public Download(String path,String name, String string, int tipo2, int format2) {

		link = name;
		way = string;
		tipo = tipo2;
		format = format2;
		path2 = path;
	}

	public void run() {

		try {
			if (tipo == 0) {
				tru = "False";
			} else if (tipo == 1) {
				tru = "True";
			}

			if (format == 0) {
				form = "mp4";

			} else if(format == 1) {
				form = "mp4";
			} else {
				form = "webm";
			}
			String Path_Python = System.getenv("ProgramFiles(x86)");
			Path_Python = Path_Python + "\\Player Diti\\python.exe";
			
			String cmd = Path_Python + " \""+path2+"\" " + link + " " + way + " " + tru + "," + form;
			System.out.println(cmd);
			
			
			String line;
			String nome = null;
			System.out.println("Downloading..");
			Process p = Runtime.getRuntime().exec(cmd);
	        //printa o retorno
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				nome = line;
			}
	        
	        way = way +"\\"+ nome;
	        if(format != 0 ) {
	        Thread thread = new Thread(new Convert(way));
    		thread.start();
	        }
			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
        
	}

}
