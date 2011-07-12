/*
 * Copyright (c) 2011 Andrew Johnson <hpmamv@gmail.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in 
 * the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the 
 * Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.nlogneg.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Example_Of_Object_Serialization {
	public static class Test implements Serializable{
		private static final long serialVersionUID = 354827069565217610L;
		public String hello;
		
		public String toString(){
			return hello;
		}
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		Test t = new Test();
		t.hello = "check me out";
		
		System.out.println(t);
		
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		
		ObjectOutputStream oos = new ObjectOutputStream(bs);
		oos.writeObject(t);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(bs.toByteArray());
		
		ObjectInputStream ois = new ObjectInputStream(bis);
		
		Test t2 = (Test)ois.readObject();
		
		System.out.println(t2);
	}
}
