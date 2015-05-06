package nBackGame;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainApp extends JFrame
{
	protected JPanel buttonJPanel;
	protected final int NUMBEROFBUTTONS = 2;
	protected JButton[] buttonArray = new JButton[NUMBEROFBUTTONS];
	protected String[] buttonNames = {"YES", "NO"};
	
	// User Specified Game Variables //
	protected int mTotalNumTrials = 20;
	protected String[] mUserResponses = new String[mTotalNumTrials];
	protected int[] mUserResponseEVALS = new int[mTotalNumTrials];
	protected int mCurrentTrial = 0;
	
	// Answers
	protected int[] mAnswerKey = new int[mTotalNumTrials];
	
	// Images
	protected BufferedImage backgroundImage;
	private String mImagePath = "C:/Users/Jnelson/workspace/nBackGame/src/nBackGame/stimulus/";
	private String[] mFaceImages = {"faces/Arnold.jpg",
									"faces/George.jpg",
									"faces/Jennifer.jpg",
									"faces/LilWayne.jpg",
									"faces/UnknownFemale.jpg",
									"faces/UnknownFemale2.jpg",
									"faces/UnknownFemale3.jpg",
									"faces/UnknownFemale4.jpg",
									"faces/UnknownMale1.jpg",
									"faces/UnknownMale2.jpg",
								   };
	protected StimulusComponent mStim = new StimulusComponent(mImagePath, mFaceImages[0]);
	protected StimulusComponent mStartImage = new StimulusComponent("C:/Users/Jnelson/workspace/nBackGame/src/nBackGame/", "memorybackground.jpg");
	public MainApp(int n, int iti)  // n-back, and inter-trial-interval
	{
		super("nBackGame!");
		
		
		buttonJPanel = new JPanel();
		buttonJPanel.setLayout( new GridLayout( 1, NUMBEROFBUTTONS));
		
		// Create Response Buttons
		
		for ( int i=0; i < NUMBEROFBUTTONS; i++)
		{
			final String tempString = buttonNames[i];
			buttonArray[i] = new JButton(buttonNames[i]);
			
			// Add Action Listeners
			buttonArray[i].addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					evaluateUserResponse(tempString);
				}
			});
			buttonJPanel.add(buttonArray[i]);
		}

		add( buttonJPanel, BorderLayout.SOUTH);
		add(mStartImage, BorderLayout.CENTER);
		
		setSize(450,675);  // 6:9 aspect ratio, should look sometime like a smart phone screen
		setVisible(true); 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		add (mStim, BorderLayout.CENTER);
		setVisible(true); 
		
		
		
		
	}
	
	private void setContentPane(ImageIcon imageIcon) {
		// TODO Auto-generated method stub
		
	}

	protected void evaluateUserResponse(String resp)
	{
		
		mUserResponses[mCurrentTrial] = resp;
		if ((resp == "YES" && mAnswerKey[mCurrentTrial] == 1) || (resp == "NO" && mAnswerKey[mCurrentTrial] == 0)) {
			mUserResponseEVALS[mCurrentTrial] = 1;
		}
		else { 
			mUserResponseEVALS[mCurrentTrial] = 0; 
		}
	}

	public static void main(String[] args) 
	{
		MainApp MA = new MainApp(2, 3);
		MA.setResizable(true);
		//c.setForeground(Color.GRAY);
		MA.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
}
