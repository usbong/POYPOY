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
 * @last updated: 20240618; from 20240615
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

//added by Mike, 20240614
import java.awt.Toolkit;
import java.awt.Dimension;

//Reference: https://stackoverflow.com/questions/24088094/jframe-mouselistener-does-not-work; last accessed: 20240618
//answer by: Resigned June 2023, 20240606T1837

public class UsbongMain extends JFrame {
  //public static final int windowSize = 2048;   
  
  //TODO: -reverify: this; 60fps?
  public static final int updateDelay = 20; 
  	
  private ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);  

  public UsbongMain() {
	  super("Usbong"); 
	  
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

	  //https://stackoverflow.com/questions/22982295/what-does-pack-do; last accessed: 20240613
	  //answer by sidgate, 20140410T0812
	  //"The pack method sizes the frame so that all its contents are at or above their preferred sizes."
	  //removed by Mike, 20240618
	  //pack(); 
	  
	  //note set after the previous set of instructions
      setVisible(true);
	  
	  //edited by Mike, 20240618
	  //timer.scheduleAtFixedRate(() -> repaint(), 0, 200, MILLISECONDS);   
	  timer.scheduleAtFixedRate(() -> repaint(), 0, updateDelay, MILLISECONDS);   	  
  }
  
  public static void main(String args[]) {	  
    System.out.println("HALLO!");
	
	//instance = new UsbongMain();
	new UsbongMain();
  }
  
  public void paint(Graphics g) { 
	super.paint(g);

	g.setColor(Color.white);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());

	g.setColor(Color.red);		
	g.drawRect(100, 100, 100, 100);	
  }  
}