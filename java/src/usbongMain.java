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
 * @last updated: 20240608; from 20240606
 * @website: www.usbong.ph
 *
 */ 
/*
 Additional Notes:
 1) compile on Windows Machine;
 //reminder machine specs; example: Windows 64-bit system
 
 //javac -cp .;org/joml.jar;org/lwjgl.jar;org/lwjgl.opengl.jar;org/lwjgl.opengl.natives.windows.jar usbongMain.java

 javac -cp .;org/joml.jar;org/lwjgl.jar;org/lwjgl-natives-windows.jar;org/lwjgl-opengl.jar;org/lwjgl-opengl-natives-windows.jar usbongMain.java
 
 reminder: change the filenames to compile successfully
 
 1.1) https://github.com/JOML-CI/JOML/releases; last accessed: 20240522
 joml-1.10.5.jar
 
 1.2) https://github.com/LWJGL/lwjgl3/releases; last accessed: 20240522
 lwjgl/lwjgl/lwjgl.jar
 
 1.3) https://github.com/LWJGL/lwjgl3/releases; last accessed: 20240522
 lwjgl/lwjgl-opengl/lwjgl-opengl
 
 1.4) https://github.com/LWJGL/lwjgl3/releases; last accessed: 20240522
 lwjgl/lwjgl-opengl-natives-windows
 
 1.5) https://github.com/LWJGL/lwjgl3-demos; last accessed: 20240522
 lwjgl3-demos-main/src/org/lwjgl/demo/util/IOUtils.java
 note: java file (not jar)
 
 2) Execute
 
 //java -cp .;org/joml.jar;org/lwjgl.jar;org/lwjgl.opengl.jar;org/lwjgl.opengl.natives.windows.jar usbongMain

 java -cp .;org/joml.jar;org/lwjgl.jar;org/lwjgl-natives-windows.jar;org/lwjgl-opengl.jar;org/lwjgl-opengl-natives-windows.jar usbongMain
 
*/ 

/*
current error; 20240608; from 20240606
LWJGL; JVM platform Windows x86
however, Platform available on classpath: windows/x64
Failed to locate library: lwjgl.dll

TODO: -download Java SE 8 SDK; Windows x64

https://www.oracle.com/ph/java/technologies/javase/javase8-archive-downloads.html; last accessed: 20240608

## --

fixed; 20240606; error on 20240605
UnsatisfiedLinkError: Failed to locate library: lwjgl_opengl.dll

expects solution to be similar to adding classpath of "org/lwjgl-natives-windows-x86.jar"

Reference:
https://stackoverflow.com/questions/38934607/lwjgl-failed-to-load-a-library; last accessed: 20240605
answer by: Mectb 3a Marty, 20190613T0833
edited by: Cà phê đen, 20190613T0920
*/

/*
fixed; 20240605
Reference:
lwjgl3-master/lwjgl3-master/modules/samples/src/test/java/org/lwjgl/demo/opengl/GLXGears.java

compiles, but error during execution;

NoClassDefFoundError: org/joml/Matrix4d

jar file; 
*/

import org.joml.*;
import org.lwjgl.*;

//import static org.lwjgl.opengl.GL30C.*;
//import org.lwjgl.opengl.natives.windows.*;

import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.io.*;
import java.nio.*;
import java.util.*;

//import static org.joml.Math.*;
//import static org.joml.*; //added by Mike, 20240522

import org.lwjgl.demo.util.IOUtils;

import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
 
public class usbongMain {	 

	private static usbongMain instance;

    private final Matrix4d
        P   = new Matrix4d(),
        MVP = new Matrix4d();
    private final Matrix4x3d
        V   = new Matrix4x3d(),
        M   = new Matrix4x3d(),
        MV  = new Matrix4x3d();
		
	private final Matrix3d normal = new Matrix3d();
    private final Vector3d light  = new Vector3d();

    private final FloatBuffer vec3f = BufferUtils.createFloatBuffer(3);
    private final FloatBuffer mat3f = BufferUtils.createFloatBuffer(3 * 3);
    private final FloatBuffer mat4f = BufferUtils.createFloatBuffer(4 * 4);		
		
	// ---------------------

    private long   count;
    private double startTime;

    private double distance = 40.0f;
    private double angle;

  //if none, "java.lang.NoClassDefFoundError"
  public usbongMain() {
	System.err.println("GL_VENDOR: " + glGetString(GL_VENDOR));
    
	System.err.println("GL_RENDERER: " + glGetString(GL_RENDERER));
    
	System.err.println("GL_VERSION: " + glGetString(GL_VERSION));
	

	GLCapabilities caps = GL.getCapabilities();
        if (!caps.OpenGL20) {
            throw new IllegalStateException("This demo requires OpenGL 2.0 or higher.");
        }
  }
	
  public static void main(String args[]) {
    System.out.println("HALLO!");
	
	instance = new usbongMain();
  }
}