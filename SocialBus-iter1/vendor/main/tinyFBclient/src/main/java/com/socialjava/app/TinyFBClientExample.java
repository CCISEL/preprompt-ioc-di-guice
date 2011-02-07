package com.socialjava.app;

import java.util.TreeMap;

import com.socialjava.TinyFBClient;

public class TinyFBClientExample {
	public static void main(String [] args){
		 TinyFBClient fb = new TinyFBClient(
				 "99b4672655cadb2279d9d499017d1c1a", // apiKey
				 "23a58b1d368c4df5d6ec87b172abe32b",// SECRET KEY
				 "151238008263585"); // App Id 
	       	TreeMap<String, String> tm = new TreeMap<String, String>();
	        tm.put("uid", "smart4x4");
	        String friendList = fb.call("friends.get", tm);
	        System.out.println(friendList);
		
	}
}
