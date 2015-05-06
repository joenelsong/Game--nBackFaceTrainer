package nBackGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class StimulusComponent extends JPanel 
{
	protected BufferedImage mImage;
	
	protected StimulusComponent(String path, String image) {
		
	    try {                
	       mImage = ImageIO.read(new File(path+image));
	    } catch (IOException ex) {
	         // handle exception...
	    }
	}
	
	@Override
	protected void paintComponent( Graphics g )
	{	
	     super.paintComponent(g);
	     g.drawImage(mImage, 100, 200, null); // see javadoc for more info on the parameters             
	 }

}

