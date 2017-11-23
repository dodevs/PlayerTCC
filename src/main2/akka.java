package main2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class akka {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException  {
		String path = System.getProperty("user.home");
		path=path+"\\documents\\dir.txt";
		
		PrintWriter writer = new PrintWriter(path);
		writer.println("The first line");

		writer.close();

}
}
