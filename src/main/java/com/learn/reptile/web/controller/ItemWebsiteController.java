package com.learn.reptile.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.reptile.entity.po.ItemWebsite;
import com.learn.reptile.entity.vo.R;
import com.learn.reptile.mapper.ItemWebsiteMapper;

@RequestMapping("/itemWebsite")
@RestController
public class ItemWebsiteController {

	@Resource
	ItemWebsiteMapper itemWebsiteMapper;
	
	@GetMapping
	public R<List<ItemWebsite>> list() {
		return R.ok(itemWebsiteMapper.selectList(new QueryWrapper<>()));
	}
	
	@GetMapping("{id}")
	public R get(@PathVariable("id") Long id) {
		return R.ok(itemWebsiteMapper.selectById(id));
	}
	
	@PostMapping
	public R add(@RequestBody ItemWebsite itemWebsite) {
		itemWebsiteMapper.insert(itemWebsite);
		return R.ok();
	}
	
	@PutMapping("{id}")
	public R update(@PathVariable("id") Long id, @RequestBody ItemWebsite itemWebsite) {
		itemWebsiteMapper.updateById(itemWebsite);
		return R.ok();
	}
	
	@DeleteMapping("{id}")
	public R deleteById(@PathVariable("id") Long id) {
		itemWebsiteMapper.deleteById(id);
		return R.ok();
	}
}
