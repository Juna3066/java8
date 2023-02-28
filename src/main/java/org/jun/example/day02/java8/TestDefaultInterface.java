package org.jun.example.day02.java8;

import org.jun.example.day02.java8.in.MyInterface;
import org.jun.example.day02.java8.in.SubClass;

public class TestDefaultInterface {
	
	public static void main(String[] args) {
		SubClass sc = new SubClass();
		System.out.println(sc.getName());
		
		MyInterface.show();
	}

}
