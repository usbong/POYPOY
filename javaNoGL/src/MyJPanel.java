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
 * @last updated: 20240615; from 20240614
 * @website: www.usbong.ph
 *
 */ 

//import java.awt.Canvas;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.BorderLayout;

//added by Mike, 20240615
import java.awt.event.*;

/*
import java.io.*;
import java.nio.*;
import java.util.*;
*/

//Reference: https://coderanch.com/t/675524/java/MousePressed-KeyPressed; last accessed: 20240615
//TODO: -fix: MouseListener inputs not received
//TODO: -fix: KeyListener not received after MouseListener input

public class MyJPanel extends JPanel implements ActionListener, KeyListener, MouseListener {
	
  //MyJPanel instance;	
  private JFrame myJFrame;
	
  public MyJPanel() {	  
	super(new BorderLayout());
	
	myJFrame = new JFrame();
	  
    addKeyListener(this);
    addMouseListener(this);
	
    myJFrame.addKeyListener(this);
    myJFrame.addMouseListener(this);
		
    setFocusable(true);	  
    myJFrame.setFocusable(true);	  

  }
  
  @Override
  public void actionPerformed(ActionEvent arg0) {	  
	  System.out.println("AT ACTION PERFORMED!");	
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {
    System.out.println("MOUSE CLICKED!");
  }
	
  @Override
  public void mouseEntered(MouseEvent arg0) {
  }
	
  @Override
  public void mouseExited(MouseEvent arg0) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
		
	//System.out.println("MOUSE PRESSED!");

//		if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
			myJFrame.dispatchEvent(new WindowEvent(myJFrame, WindowEvent.WINDOW_CLOSING));
//		}
  }
	
  @Override
  public void mouseReleased(MouseEvent e) {	
  }
	
  @Override
  public void keyPressed(KeyEvent e) {
	System.out.println("KEY PRESSED!");
	if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
		myJFrame.dispatchEvent(new WindowEvent(myJFrame, WindowEvent.WINDOW_CLOSING));
    }
  }
	
  @Override
  public void keyReleased(KeyEvent e) {	
  }
	
  @Override
  public void keyTyped(KeyEvent arg0) {
  }
	
  public void init() {
    System.out.println("JPANEL INIT!");

	MyCanvas myCanvas = new MyCanvas();
    myCanvas.init();

	this.setLayout(new BorderLayout());
	
	//added by Mike, 20240615
/*	
	JFrame myJFrame = new JFrame();
*/	
	myJFrame.setLayout(new BorderLayout());
	
	myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	myJFrame.setUndecorated(true);
	
	//final JPanel myJPanel = new JPanel(new BorderLayout());
	this.add(myCanvas);
	
	myJFrame.add(this, BorderLayout.CENTER);
	//this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

/*		
	JFrame myJFrame = new JFrame();
	myJFrame.setLayout(new BorderLayout());
	
	//added by Mike, 20240615
	myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	myJFrame.setUndecorated(true);
	
	final JPanel myJPanel = new JPanel(new BorderLayout());
	myJPanel.add(myCanvas);
	
	myJFrame.add(myJPanel, BorderLayout.CENTER);
	
	//added by Mike, 20240615
	myJFrame.addKeyListener(new KeyListener(){
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("KEY PRESSED!");

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				
			}

			if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
				myJFrame.dispatchEvent(new WindowEvent(myJFrame, WindowEvent.WINDOW_CLOSING));
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("KEY TYPED!");
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("KEY RELEASED!");
		}
	});
*/
	
	//https://stackoverflow.com/questions/22982295/what-does-pack-do; last accessed: 20240613
	//answer by sidgate, 20140410T0812
	//"The pack method sizes the frame so that all its contents are at or above their preferred sizes."
	myJFrame.pack(); 
	
	myJFrame.setVisible(true);
  }
}