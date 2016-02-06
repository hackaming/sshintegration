package com.springmvclearn.test;

import java.io.OutputStream;  
import java.util.HashMap;  
import java.util.Map;  
  
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;  
  
public class TestFastJason {  
  public static void main(String[] args) {  
    Map<String, Student> maps = new HashMap<String, Student>();  
    Student s1 = new Student("s1", 16);  
  
    maps.put("s1", s1);  
    maps.put("s2", s1);  
      
    SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect;  
  
    byte[] bytes = JSON.toJSONBytes(maps,feature);  
    
  
    System.out.println(new String(bytes));
    
    //Student s = JSON.toJavaObject(bytes, Student.class);
    
    
  }  
}  

class Student{
	public Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}