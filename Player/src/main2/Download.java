package main2;

import java.io.File;
import java.net.URL;
import com.github.axet.vget.VGet;



public class Download implements Runnable {
	private String link;

	public Download(String name){

		link = name;

	}

	public void run(){
		
		  try {
	            String url = link;
	            String path = "C:\\";
	            VGet v = new VGet(new URL(url), new File(path));
	            System.out.println("downlading..");
	            v.download();
	            System.out.println("Sucesso");
	            
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
		

	}






}
