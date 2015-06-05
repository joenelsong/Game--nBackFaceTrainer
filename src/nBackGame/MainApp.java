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
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
	protected int mTotalNumTrials = 10;
	protected int nbackN;
	protected String[] mUserResponses;
	protected int[] mUserResponseEVALS;
	protected int mCurrentTrial = 0;
	protected int mITI;
	
	// Answers
	protected int[] mAnswerKey;
	
	protected int[] mLastImage;
	
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
	private int imgCount = 0; // Initialized at 1 because first image is displayed
	
	protected StimulusComponent mStim;
	
	// Feedback
	JLabel mFeedback = new JLabel("Feedback: ");
	
	//protected StimulusComponent mStartImage = new StimulusComponent("C:/Users/Jnelson/workspace/nBackGame/src/nBackGame/", "memorybackground.jpg");
	
	// Test Set //
	private String[] mTestSet;
	public MainApp(int n, int iti, int numOfTrials, String stimulusType)  // n-back, and inter-trial-interval
	{
		super("nBackGame!");
		
		nbackN = n;
		mLastImage = new int[n];
		mITI = iti;
		
		mTotalNumTrials = numOfTrials;
		mAnswerKey = new int[mTotalNumTrials];
		mUserResponses = new String[mTotalNumTrials];
		mUserResponseEVALS = new int[mTotalNumTrials];
		
		CreateTestSet();
		CreateAnswerSet();
		
		mStim = new StimulusComponent(mImagePath, mTestSet[imgCount]);
		imgCount++;
		
		PrintAnswerKey();
		
		PopulateFrame();
		
		
		
		setSize(450,675);  // 6:9 aspect ratio, should look sometime like a smart phone screen
		setVisible(true); 
				
		add (mStim, BorderLayout.CENTER);
		add (mFeedback, BorderLayout.NORTH);
		
		setVisible(true); 
		setResizable(true);
		//c.setForeground(Color.GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void PopulateFrame() 
	{
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
					
					evaluateUserResponse(tempString); // Evaluates Users Response
					
							// Save Previous Image (useful if doing realtime stimulus generation, but I've opted to create TestSet ahead of time
							// mLastImage[imgCount%nbackN] = imgCount%mFaceImages.length;
							//imgCount = (imgCount + 1) % mFaceImages.length;
					
					/** Inter-Trial Interval Code **/
					getContentPane().removeAll();
					
					mStim = new StimulusComponent("C:/Users/Jnelson/workspace/nBackGame/src/nBackGame/", "ChangeBlinder.png");
					add(mStim, BorderLayout.CENTER);
					PopulateFrame();
					getContentPane().revalidate();
					getContentPane().repaint();
					
					try {
						Thread.sleep(1000*mITI);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			       /** Logic to Create and places the next Image **/
					getContentPane().removeAll(); // Removes old Image					

					mStim = new StimulusComponent(mImagePath, mTestSet[imgCount]);
					add (mFeedback, BorderLayout.NORTH);
					add(mStim, BorderLayout.CENTER);
					PopulateFrame();
					getContentPane().revalidate();
					getContentPane().repaint();
					imgCount++;
					
					if (imgCount == mTestSet.length){
						for ( int i=0; i < NUMBEROFBUTTONS; i++){
							buttonArray[i].setEnabled(false);
							
						// RUN Display Feedback Function
						}
					}
					
				} 
				
			});
			buttonJPanel.add(buttonArray[i]);
		}

		add( buttonJPanel, BorderLayout.SOUTH);
	}
	
	protected void evaluateUserResponse(String resp)
	{
		
		// Generic Feedback Message while waiting for n-back to be valid
		if (imgCount <= nbackN) {
			mFeedback.setText("Feedback: "+"Waiting . . .");	
		}
		else {
			//System.out.print("Evaluating Response... ");
			mUserResponses[imgCount] = resp;
			if ((resp == "YES" && mAnswerKey[imgCount] == 1) || (resp == "NO" && mAnswerKey[imgCount] == 0)) {
				mUserResponseEVALS[imgCount] = 1; // correct
				mFeedback.setText("Feedback: "+"Correct Response :)");
			}
			else { 
				mUserResponseEVALS[imgCount] = 0; // Incorrect
				mFeedback.setText("Feedback: "+"Incorrect Response :(");
			}
			
			if (imgCount+1 == mTotalNumTrials) {
			PrintEvals(); 
			}
			}
		
		
	}
	
	protected void EndGameFeedBack()
	{
		JLabel mFeedback = new JLabel("WEE");
		add (mStim, BorderLayout.CENTER);
	}
	
	private void CreateTestSet()
	{
		mTestSet = new String[mTotalNumTrials];
		Random randomGenerator = new Random();
		
		// Generate Random Test Set
		for (int i = 0; i < mTotalNumTrials; i++){
			int randomInt = randomGenerator.nextInt(mFaceImages.length);
			mTestSet[i] = mFaceImages[randomInt];
		}
			
		/** Enforce 25% Targets via random selection of indices and ensure no duplicate indices **/
			float percentTargets = (float) 0.25;
			int maxIndex = mTotalNumTrials-1;
			int numTargets = Math.round(percentTargets*mTotalNumTrials);
			System.out.println("numTargets = " + numTargets);
			
			Random rng = new Random(); // Ideally just create one instance globally
			// Note: use LinkedHashSet to maintain insertion order
			Set<Integer> generated = new LinkedHashSet<Integer>();
			
			// Creates the set of indices
			while (generated.size() < numTargets)
			{
			    Integer next = rng.nextInt(maxIndex-nbackN) + nbackN; // Picks from only valid target indices
			    // As we're adding to a set, this will automatically do a containment check
			    generated.add(next);
			}
			List<?> listIndices = new ArrayList(generated); // convert set to indexable list
			    System.out.println("Random Number: " + listIndices.get(0) + ", " + listIndices.get(1) + ", " +  listIndices.get(2));
			
		// sets 25% targets
		for (int i = 0; i < numTargets; i++){
			mTestSet[(int) listIndices.get(i)] = mTestSet[(int) listIndices.get(i)-nbackN];
		}
	}
	private void CreateAnswerSet()
	{
		// Generate Answer Key
		for (int i = nbackN; i < mTotalNumTrials; i++){
			mAnswerKey[i] = (mTestSet[i] == mTestSet[i-nbackN]) ? 1 : 0; // 1 if target, 0 if decoy
		}
	}
	
/** TEST CODE **/
	private void PrintEvals(){
		System.out.printf("User Response Evaluations: %5s", " ");
		for (int i = 0; i< mUserResponseEVALS.length; i++){
			System.out.print(mUserResponseEVALS[i]+", ");
		}
		System.out.println();
	}
	
	private void PrintAnswerKey(){
		System.out.printf("Answer Key: %20s", " ");
		for (int i = 0; i< mAnswerKey.length; i++){
			System.out.print(mAnswerKey[i]+", ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) 
	{
		MainApp MA = new MainApp(2, 0, 21, "faces"); // 2-back, 2 second ITI, 20 total trials
	
	}
	
}
