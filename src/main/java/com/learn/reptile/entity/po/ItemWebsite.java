package com.learn.reptile.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class ItemWebsite {
	
	@TableId(type = IdType.AUTO)
	private Long id;
	
	private String code;
	
	private String name;
	
	private String url;
	
	private String regexpStr;

	private String regexpContents;
	
	private String startStr;
	
	private String endStr;
}
