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
 * @last updated: 20240613; from 20240612
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

TODO: -download Java SE 8 SDK; Windows x64

https://www.oracle.com/ph/java/technologies/javase/javase8-archive-downloads.html; last accessed: 20240608

## --
*/

//import java.awt.Canvas;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.BorderLayout;

/*
import java.io.*;
import java.nio.*;
import java.util.*;
*/

public class UsbongMain {	 

  private static UsbongMain instance;

  public UsbongMain() {
  }
	
  public static void main(String args[]) {
    System.out.println("HALLO!");
	
	instance = new UsbongMain();
	
	MyCanvas myCanvas = new MyCanvas();
    myCanvas.init();
	
	JFrame myJFrame = new JFrame();
	myJFrame.setLayout(new BorderLayout());
	
	final JPanel myJPanel = new JPanel(new BorderLayout());
	myJPanel.add(myCanvas);
	
	myJFrame.add(myJPanel, BorderLayout.CENTER);
	
	//https://stackoverflow.com/questions/22982295/what-does-pack-do; last accessed: 20240613
	//answer by sidgate, 20140410T0812
	//"The pack method sizes the frame so that all its contents are at or above their preferred sizes."
	myJFrame.pack(); 
	
	myJFrame.setVisible(true);
  }
}