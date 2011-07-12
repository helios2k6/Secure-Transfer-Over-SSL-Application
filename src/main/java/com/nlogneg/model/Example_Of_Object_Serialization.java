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
