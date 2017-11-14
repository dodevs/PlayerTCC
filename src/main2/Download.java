package main2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.axet.vget.VGet;




public class Download implements Runnable {
	private String link;
	private String way;
	private int tipo;
	private int format;

	String tru;
	String form;
	private static final Logger LOG = Logger.getLogger(Download.class.getName());

	public Download(String name, String string, int tipo2, int format2){

		link = name;
		way = string;
		tipo = tipo2;
		format = format2;
		
		
		

	}

	public void run(){
		
		  try {	            
	            if(tipo == 0) {
	            	tru = "False";	            	
	            } else if(tipo == 1) {
	            	tru = "True";
	            }
	            
	            if(format == 0) {
	            	form = "mp4";
	            	
	            }else {
	            	form = "webm";
	            }
	           		
	    		
	    		String a = "python C:\\Users\\Thiago\\Desktop\\teste.py "+link+" "+way+ " "+tru+","+form;	    		;
	    	//	System.out.println(Runtime.getRuntime().exec("python \"C:\\Users\\Thiago\\Desktop\\new 1.py\" "));
	    		System.out.println(Runtime.getRuntime().exec(a));
	    		
	    		/*
	            String line2;
	            String mp4File = "\"C:\\Users\\Thiago\\Desktop\\Nova\\Class A - Ns Dois - Clipe Oficial.mp4\" ";
	            String mp3File = "C:\\Users\\Thiago\\Desktop\\Nova2.mp3"; 

	            // ffmpeg -i input.mp4 output.avi as it's on www.ffmpeg.org
	            String cmd = "ffmpeg -i " + mp4File + " " + mp3File;
	            Process p2 = Runtime.getRuntime().exec(cmd);
	            BufferedReader in = new BufferedReader(
	                    new InputStreamReader(p2.getErrorStream()));
	            while ((line2 = in.readLine()) != null) {
	                System.out.println(line2);
	            }
	            p2.waitFor();
	            System.out.println("Video converted successfully!");
	            in.close();
	        } catch (IOException | InterruptedException e) {
	            LOG.log(Level.SEVERE, null, e);
	        }
			
	            
	            
	            /*
	            VGet v = new VGet(new URL(url), new File(path));
	            System.out.println("downlading..");
	            v.download();
	            System.out.println("Sucesso");
	            */
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
		

	}






}
