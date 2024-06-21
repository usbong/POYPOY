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
 * @last updated: 20240621; from 20240620
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

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.event.*;
import java.util.concurrent.*;  
import static java.util.concurrent.TimeUnit.*;  

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;

//added by Mike, 20240620
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

//added by Mike, 20240614
import java.awt.Toolkit;
import java.awt.Dimension;

//added by Mike, 20240619
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

//Reference: https://stackoverflow.com/questions/24088094/jframe-mouselistener-does-not-work; last accessed: 20240618
//answer by: Resigned June 2023, 20240606T1837

public class UsbongMain extends JFrame {
  //added by Mike, 20240619
  private static UsbongMain instance;
  
  //private static ImagePanel myImagePanel;  
  private static BufferedImage myBufferedImage;
  //added by Mike, 20240621
  private static int iFrameCount;
  private static int iFrameCountMax;
  private static int iFrameCountDelay;
  private static int iFrameCountDelayMax;
  
  
  //public static final int windowSize = 2048;   
    
  //edited by Mike, 20240619
  //reference: Usbong Game Off 2023 Main
  //1000/60=16.66; 60 frames per second
  //1000/30=33.33; 30 frames 
  //const fFramesPerSecondDefault=16.66;
  //const fFramesPerSecondDefault=33.33;  
  public static final int updateDelay = 16;//20; 
  	
  private ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);  

  public UsbongMain() {
	  super("Usbong"); 

	  //added by Mike, 20240619	  
	  instance = this;
	  
	  //edited by Mike, 20240618
	  //setSize(windowSize, windowSize);    
	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  double dWidth = screenSize.getWidth();
      double dHeight = screenSize.getHeight();
		
	  int iWidth = (int) Math.round(dWidth);
	  int iHeight = (int) Math.round(dHeight);

	  System.out.println("width: "+iWidth);
	  System.out.println("height: "+iHeight);
			  
	  setSize(iWidth, iHeight);
	  //repaint();	  
	  	  
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	  
	  setUndecorated(true);
	  
	  JPanel panel = new JPanel();    
      panel.setFocusable(true);   
      panel.addKeyListener(new KeyListener() {    
            public void keyPressed(KeyEvent key) {  
                System.out.println("Key pressed."); 

				//added by Mike, 20240619
				if (key.getKeyCode() == KeyEvent.VK_ESCAPE){
					//TODO: -add: open exit menu
					instance.dispatchEvent(new WindowEvent(instance, WindowEvent.WINDOW_CLOSING));

/*					
					//note: more noticeable repaints after; timer problem?
					instance.dispose();
					instance.setUndecorated(false);
					instance.setVisible(true);
*/					
				}				
            }   
            public void keyReleased(KeyEvent key) {}    
            public void keyTyped(KeyEvent key) {}   
      }); 
      panel.addMouseListener(new MouseListener() {    
            public void mouseReleased(MouseEvent mouse) {}  
            public void mouseClicked(MouseEvent mouse) {}   
            public void mousePressed(MouseEvent mouse) {    
                System.out.println("Mouse clicked.");   
            }   
            public void mouseEntered(MouseEvent mouse) {}   
            public void mouseExited(MouseEvent mouse) {}    
      }); 
      panel.addMouseMotionListener(new MouseMotionListener() {    
            public void mouseDragged(MouseEvent mouse) {    
                System.out.println("Mouse dragged.");   
            }   
            public void mouseMoved(MouseEvent mouse) {} 
      }); 
	  	  
      add(panel); 
      panel.requestFocus();   
	  
	  //added by Mike, 20240619
/*	  
	  myImagePanel = new ImagePanel();
	  add(myImagePanel);
*/
	  //https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel; last accessed: 20240619
	  //answered by: Brendan Cashman, 20081118T1750
	  //edited by: Andrew Thompson, 20161018T2345

	  //TODO: -put: in another file
	  try {         
          //image = ImageIO.read(new File("D:/USBONG/res/count.png"));
		  myBufferedImage = ImageIO.read(new File("../res/count.png"));
      } catch (IOException ex) {			
      }	  

	  //added by Mike, 20240621
	  iFrameCount=0;
	  iFrameCountMax=4;
	  iFrameCountDelay=0;
	  iFrameCountDelayMax=20;
	  
	  //https://stackoverflow.com/questions/22982295/what-does-pack-do; last accessed: 20240613
	  //answer by sidgate, 20140410T0812
	  //"The pack method sizes the frame so that all its contents are at or above their preferred sizes."
	  //removed by Mike, 20240618
	  //pack(); 
	  
	  //note set after the previous set of instructions
      setVisible(true);
	  
	  //edited by Mike, 20240619
	  //timer.scheduleAtFixedRate(() -> repaint(), 0, updateDelay, MILLISECONDS);
	  //edited by Mike, 20240621
   	  timer.scheduleAtFixedRate(() -> run(), 0, updateDelay, MILLISECONDS);
  }
  
  public static void main(String args[]) {	  
    System.out.println("HALLO!");
	
	instance = new UsbongMain();
	//new UsbongMain();
  }
  
  //added by Mike, 20240619
  public void run() {
	  //TODO: -add: update() for the logic part, non-paint tasks
	  
	  repaint();
  }
  
  public void paint(Graphics g) { 
	super.paint(g);

	g.setColor(Color.white);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());

	g.setColor(Color.red);		
	//edited by Mike, 20240619
	//g.drawRect(100, 100, 100, 100);	
	g.drawRect(800, 400, 100, 100);	
	
	//added by Mike, 20240619
    //myImagePanel.paintComponent(g);	
	
	//added by Mike, 20240620
	//g.drawImage(myBufferedImage, 0, 0, this);  
	//TODO: -verify: if clip still has to be cleared
	Rectangle2D rect = new Rectangle2D.Float();
	
	//added by Mike, 20240621
	//iFrameCount=2;
	
	//rect.setRect(0, 0, 128, 128);
	//rect.setRect(iFrameCount*128, 0, 128, 128);
	//note: clip rect has to move with object position
	rect.setRect(iFrameCount*128-iFrameCount*128, 0, 128, 128);

	Area myClipArea = new Area(rect);
	g.setClip(myClipArea);	
	//added by Mike, 20240621	
	//g.drawImage(myBufferedImage, 0, 0, this);  
	g.drawImage(myBufferedImage, 0-iFrameCount*128, 0, this);  
	
	if (iFrameCountDelay<iFrameCountDelayMax) {
		iFrameCountDelay++;
	}
	else {
		iFrameCount=(iFrameCount+1)%iFrameCountMax;
		iFrameCountDelay=0;
	}
  }
}