/*
 * Copyright 2024 Usbong
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @company: Usbong
 * @author: SYSON, MICHAEL B.
 * @date created: 20240522
 * @last updated: 20240627; from 20240626
 * @website: www.usbong.ph
 *
 */
/*
 Additional Notes:
 1) compile on Windows Machine;
	javac *.java

 2) Execute
	java UsbongMain
*/

/*
current error; 20240608; from 20240606
LWJGL; JVM platform Windows x86
however, Platform available on classpath: windows/x64
Failed to locate library: lwjgl.dll

answer: download Java SE 8 SDK; Windows x64

https://www.oracle.com/ph/java/technologies/javase/javase8-archive-downloads.html; last accessed: 20240608
*/

/*
//Reference:
1) https://docs.oracle.com/javase/tutorial/uiswing/painting/refining.html; last accessed: 20240622
SwingPaintDemo4.java; last accessed: 20240622; from 20240623

2) https://github.com/usbong/game-off-2023; last accessed: 20240623
*/

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;

//added by Mike, 20240622
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

//added by Mike, 20240622
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import java.util.TimerTask;
import java.util.Timer;

import java.awt.Toolkit;
import java.awt.Dimension;

//added by Mike, 20240622
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

//added by Mike, 20240623
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

//sound
/* //edited by Mike, 20240623
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.embed.swing.JFXPanel;
*/
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;

//TODO: -verify: in another computer with Java Virtual Machine

public class UsbongMain {

  //edited by Mike, 20240622
  //reference: Usbong Game Off 2023 Main
  //1000/60=16.66; 60 frames per second
  //1000/30=33.33; 30 frames
  //const fFramesPerSecondDefault=16.66;
  //const fFramesPerSecondDefault=33.33;
  private static final int updateDelay = 16;//20;
  private static MyPanel mp;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        //System.out.println("Created GUI on EDT? "+
        //SwingUtilities.isEventDispatchThread());

		JFrame f = new JFrame("Usbong Main Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//edited by Mike, 20240622
        //f.setSize(250,250);

		//edited by Mike, 20240618
	    //setSize(windowSize, windowSize);
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    double dWidth = screenSize.getWidth();
        double dHeight = screenSize.getHeight();

	    int iWidth = (int) Math.round(dWidth);
	    int iHeight = (int) Math.round(dHeight);

	    System.out.println("width: "+iWidth);
	    System.out.println("height: "+iHeight);

      //edited by Mike, 20240622
      //macOS still has menu row, etc.
      //f.setSize(iWidth, iHeight);
      f.setUndecorated(true); //removes close, minimize, maxime buttons in window

      //Reference: https://stackoverflow.com/questions/1155838/how-can-i-do-full-screen-in-java-on-osx; last accessed: 20240622
      //answered by: Michael Myers, 20090720T2105
      GraphicsDevice gd =
                  GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

      //macOS, Windows both have "Full Screen Support"
      if (gd.isFullScreenSupported()) {
          gd.setFullScreenWindow(f);
      } else { //note: in case, no "Full Screen Support"
          f.setSize(iWidth, iHeight);
          f.setVisible(true);
      }


		//edited by Mike, 20240622
		//f.add(new MyPanel());
		mp = new MyPanel(f);
		mp.setFocusable(true); //necessary to receive key presses
		f.add(mp);

    //removed by Mike, 20240622
    //f.setVisible(true);

/*  //edited by Mike, 20240623
    final JFXPanel fxPanel = new JFXPanel();
    String sCanonFilename = "../assets/audio/usbongGameOff2023AudioEffectsCannon.mp3";
    Media canonSound = new Media(new File(sCanonFilename).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(canonSound);
    mediaPlayer.play();
*/
	//playSound("../assets/audio/usbongGameOff2023AudioEffectsCannon.wav");
	//https://en.wikipedia.org/wiki/File:MIDI_sample.mid?qsrc=3044; last accessed: 20240623
	//midi files playable;
	//however, known warning appears: 
	//https://stackoverflow.com/questions/23720446/java-could-not-open-create-prefs-error; last accessed: 20240623
	//...java.util.prefs.WindowsPreferences <init>
	//WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 
    //0x80000002. Windows RegCreateKeyEx(...) returned error code 5.
	playSound("../assets/audio/MIDI_sample.mid");
	
		//timer.scheduleAtFixedRate(() -> mainRun(), 0, updateDelay, MILLISECONDS);

		TimerTask myTimerTask = new TimerTask(){
		   public void run() {
		     mp.update();

			 mp.repaint();
		   }
		};

		Timer myTimer = new Timer(true);
		myTimer.scheduleAtFixedRate(myTimerTask, 0, updateDelay); //60 * 1000);
    }
	
	//reference: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java;
	//last accessed: 20240623
	//answered by: Andrew Jenkins, 20160608T0441
	//edited by: Sebastian, 20200608T1929
	//note: .mp3 format not available
	private static void playSound(String soundFile) {
		  try {
			Clip clip = AudioSystem.getClip();
			File f = new File("" + soundFile);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  

			clip.open(audioIn);
			clip.start(); 
		  } catch (Exception e) {
			System.err.println(e.getMessage());
		  }		
	}
}

//note JPanel? JFrame? confusing at the start, without working sample code;
class MyPanel extends JPanel {

    RedSquare redSquare;

	//added by Mike, 20240622
	Dice myDice;
	JFrame myJFrameInstance;

    public MyPanel(JFrame f) {
		//added by Mike, 20240622
		myJFrameInstance = f;

		redSquare  = new RedSquare();
		myDice = new Dice();

        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                moveSquare(e.getX(),e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent e){
                moveSquare(e.getX(),e.getY());
            }
        });

		//added by Mike, 20240622
		addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent key) {
                System.out.println("Key pressed.");

				if (key.getKeyCode() == KeyEvent.VK_ESCAPE){
					System.out.println("ESC.");

					//TODO: -add: open exit menu
					myJFrameInstance.dispatchEvent(new WindowEvent(myJFrameInstance, WindowEvent.WINDOW_CLOSING));

				}
            }

			public void keyReleased(KeyEvent key) {}
            public void keyTyped(KeyEvent key) {}
		});
    }

    private void moveSquare(int x, int y){

        // Current square state, stored as final variables
        // to avoid repeat invocations of the same methods.
        final int CURR_X = redSquare.getX();
        final int CURR_Y = redSquare.getY();
        final int CURR_W = redSquare.getWidth();
        final int CURR_H = redSquare.getHeight();
        final int OFFSET = 1;

        if ((CURR_X!=x) || (CURR_Y!=y)) {

            // The square is moving, repaint background
            // over the old square location.
			//removed by Mike, 20240627
			//repaint already done by TimerTask			
            //repaint(CURR_X,CURR_Y,CURR_W+OFFSET,CURR_H+OFFSET);

            // Update coordinates.
            redSquare.setX(x);
            redSquare.setY(y);

            // Repaint the square at the new location.
/*			//removed by Mike, 20240627;
			//repaint already done by TimerTask
            repaint(redSquare.getX(), redSquare.getY(),
                    redSquare.getWidth()+OFFSET,
                    redSquare.getHeight()+OFFSET);
*/					
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

	  public void update() {
		  myDice.update();

		  //OK
		  //System.out.println("!!!");
	  }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //edited by Mike, 20240622
		//g.drawString("This is my custom Panel!",10,20);
		g.drawString("HALLO!",150,20);

        redSquare.paintSquare(g);

		myDice.draw(g);
    }
}

class RedSquare{

    private int xPos = 50;
    private int yPos = 50;
    private int width = 20;
    private int height = 20;

    public void setX(int xPos){
        this.xPos = xPos;
    }

    public int getX(){
        return xPos;
    }

    public void setY(int yPos){
        this.yPos = yPos;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void paintSquare(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(xPos,yPos,width,height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos,yPos,width,height);
    }
}

//added by Mike, 20240622
class Dice {
	private int iFrameCount=0;
	private int iFrameCountMax=4;
	private int iFrameCountDelay=0;
	private int iFrameCountDelayMax=20;
	
	//added by Mike, 20240626
	private int iRotationDegrees=0;
	private int iFrameWidth=128;
	private int iFrameHeight=128;
		
	private BufferedImage myBufferedImage;

    public Dice() {
	  try {
          //image = ImageIO.read(new File("D:/USBONG/res/count.png"));
		  myBufferedImage = ImageIO.read(new File("../res/count.png"));
      } catch (IOException ex) {
      }
	}

/*
    public void setX(int xPos){
        this.xPos = xPos;
    }

    public int getX(){
        return xPos;
    }

    public void setY(int yPos){
        this.yPos = yPos;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
*/
	public void update() {
		if (iFrameCountDelay<iFrameCountDelayMax) {
			iFrameCountDelay++;
		}
		else {
			iFrameCount=(iFrameCount+1)%iFrameCountMax;
			iFrameCountDelay=0;
		}
	}

//Additional Reference: 	https://docs.oracle.com/javase/tutorial/2d/advanced/examples/ClipImage.java; last accessed: 20240625	
  public void draw(Graphics g) {
	//TODO: -verify: if clip still has to be cleared
	Rectangle2D rect = new Rectangle2D.Float();

	//added by Mike, 20240621
	//iFrameCount=2;

	//rect.setRect(0, 0, 128, 128);
	//rect.setRect(iFrameCount*128, 0, 128, 128);
	
/*
	g.setClip(myClipArea);
	//added by Mike, 20240621
	//g.drawImage(myBufferedImage, 0, 0, this);
	g.drawImage(myBufferedImage, 0-iFrameCount*128, 0, null);
*/
    //added by Mike, 20240623
    AffineTransform identity = new AffineTransform();

    Graphics2D g2d = (Graphics2D)g;
    AffineTransform trans = new AffineTransform();
    trans.setTransform(identity);
    //300 is object position;
    //trans.translate(300-iFrameCount*128, 0);
    //trans.translate(-iFrameCount*128, 0);
	
	//added by Mike, 20240625
	//note clip rect has to also be updated;
	//trans.scale(2,2); //1.5,1.5

/*  //reference: https://stackoverflow.com/questions/8721312/java-image-cut-off; last accessed: 20240623
    //animating image doable, but shall need more computations;
    //sin, cos; Bulalakaw Wars;
    trans.translate(128/2,128/2);
    trans.rotate(Math.toRadians(45)); //input in degrees
    trans.translate(-128/2,-128/2);
*/
	//reminder: when objects moved in x-y coordinates, rotation's reference point also moves with the update;
	//"compounded translate and rotate"
	//https://stackoverflow.com/questions/32513508/rotating-java-2d-graphics-around-specified-point; last accessed: 20240625
	//answered by: MadProgrammer, 20150911T00:48; from 20150911T00:41
	
	//update x and y positions before rotate
	//object position x=300, y=0;
    //trans.translate(300,0);
    trans.translate(300,300);

	//scales from top-left as reference point
    //trans.scale(2,2);	
	//rotates using top-left as anchor
    //trans.rotate(Math.toRadians(45)); //input in degrees
	
	//rotate at center; put translate after rotate
	iRotationDegrees=(iRotationDegrees+10)%360;	
    trans.rotate(Math.toRadians(iRotationDegrees));
    trans.translate(-iFrameWidth/2,-iFrameHeight/2);

	//added by Mike, 20240625
	g2d.setTransform(trans);
	
	//note: clip rect has to move with object position
    //edited by Mike, 20240623
    //reminder:
    //300 is object position;
    //iFrameCount*128 is the animation frame to show;
    //-iFrameCount*128 is move the object position to the current frame;
    //rect.setRect(300+iFrameCount*128-iFrameCount*128, 0, 128, 128);
    rect.setRect(0+iFrameCount*iFrameWidth-iFrameCount*iFrameWidth, 0, iFrameWidth, iFrameHeight);
	
	Area myClipArea = new Area(rect);
		
    //edited by Mike, 20240625; from 20240623
    g2d.setClip(myClipArea);
    //g.setClip(myClipArea);

    //g2d.drawImage(image, trans, this);
    //g2d.drawImage(myBufferedImage, trans, null);
    //g2d.drawImage(myBufferedImage,300-iFrameCount*128, 0, null);
    g2d.drawImage(myBufferedImage,-iFrameCount*iFrameWidth, 0, null);

	//added by Mike, 20240625
	g2d.dispose();
  }
}
