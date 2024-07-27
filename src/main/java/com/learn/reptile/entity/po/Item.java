package com.learn.reptile.entity.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class Item {
	
	@TableId(type = IdType.AUTO)
	private Long id;
	
	private String itemId;
	
	private String source;

	private String title;
	
	private String pic;
	
	private double price;
	
	private double prePrice;
	
	private Date createTime;
}
