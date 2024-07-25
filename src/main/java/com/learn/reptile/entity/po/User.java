package com.learn.reptile.entity.po;

import lombok.Data;

@Data
public class User {

	private Long id;
	
	private String username;
	
	private String password;
	
	private String token;
}
