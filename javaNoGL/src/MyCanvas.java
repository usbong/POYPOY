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
 * @date created: 20240612
 * @last updated: 20240614; from 20240613
 * @website: www.usbong.ph
 *
 */ 

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;

//added by Mike, 20240614
import java.awt.Toolkit;
import java.awt.Dimension;

public class MyCanvas extends Canvas {
    public MyCanvas (){
		System.out.println("SIMULA!");
    }
	
	public void init() {		
		//edited by Mike, 20240614	
/*		
		this.setBackground(Color.white);
        this.setSize(400, 400);
		this.repaint();
*/
				
		//https://stackoverflow.com/questions/3680221/how-can-i-get-screen-resolution-in-java; last accessed: 20240614
		//answer by: Colin Hebert, 20100909T2015
		//edited by: Devon_C_Miller, 20140404T2008
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double dWidth = screenSize.getWidth();
		double dHeight = screenSize.getHeight();
		
		int iWidth = (int) Math.round(dWidth);
		int iHeight = (int) Math.round(dHeight);

		System.out.println("width: "+iWidth);
		System.out.println("height: "+iHeight);

		this.setBackground(Color.white);
        this.setSize(iWidth, iHeight);
		this.repaint();
		
		System.out.println("INIT!");
	}
	
    public void paint(Graphics g) {
        super.paint(g);
		g.drawRect(100, 100, 100, 100);
		
		System.out.println("DITO!");
    }
	
}