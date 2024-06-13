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
 * @last updated: 20240613; from 20240612
 * @website: www.usbong.ph
 *
 */ 

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;

public class MyCanvas extends Canvas {
    public MyCanvas (){
		System.out.println("SIMULA!");
    }
    
	public void init() {		
		this.setBackground(Color.white);
        this.setSize(400, 400);
		this.repaint();
		
		System.out.println("INIT!");
	}
	
    public void paint(Graphics g) {
        super.paint(g);
		g.drawRect(100, 100, 100, 100);
		
		System.out.println("DITO!");
    }
}