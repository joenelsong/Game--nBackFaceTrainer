import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class StimulusComponent extends JPanel 
{
	protected BufferedImage mImage;
	
	protected StimulusComponent(URL path, String image) {
		
	    try { 
                System.out.println("Creating Image...");
                URL url = new URL(path, image);
                System.out.println("URL = "+url);
	       mImage = ImageIO.read(url);
               System.out.println("Read Image");
	    } catch (IOException ex) {
                System.out.println(ex);
	         // handle exception...
	    }
	}
	//"C:\Users\joey\Documents\NetBeansProjects\MainApp\build\classes\stimulus\faces\Jennifer.jpg"
        //"C:/Users/joey/Documents/NetBeansProjects/MainApp/build/classes/faces/Jennifer.jpg""
	@Override
	protected void paintComponent( Graphics g )
	{	
	     super.paintComponent(g);
	     g.drawImage(mImage, 100, 200, null); // see javadoc for more info on the parameters             
	 }

}

