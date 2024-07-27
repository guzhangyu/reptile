package com.learn.reptile.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.reptile.entity.po.Item;
import com.learn.reptile.entity.po.ItemWebsite;
import com.learn.reptile.entity.vo.R;
import com.learn.reptile.mapper.ItemMapper;
import com.learn.reptile.utils.ItemCraw;

@RequestMapping("/item")
@RestController
public class ItemController {

	@Resource
	ItemMapper itemMapper;
	
	@GetMapping
	public R<IPage<Item>> items(Page<Item> page) {
		return R.ok(itemMapper.selectPage(page, new QueryWrapper<>()));
	}
	
	@PostMapping("craw")
	public R craw(ItemWebsite itemWebsite) {
		List<Item> items = ItemCraw.parseItemsFromUrl(itemWebsite.getUrl(), itemWebsite.getRegexpStr(), itemWebsite.getStartStr(), itemWebsite.getEndStr());
		for(Item item: items) {
			Item pre = itemMapper.selectOne(new QueryWrapper<Item>().eq("item_id", item.getItemId()));
			item.setSource(itemWebsite.getCode());
			if(pre==null) {
				itemMapper.insert(item);
			} else {
				item.setId(pre.getId());
				itemMapper.updateById(item);
			}
		}
		return R.ok();
	}
	
	@PostMapping("test")
	public R<List<Item>> test(@RequestBody ItemWebsite itemWebsite) {
		return R.ok(ItemCraw.parseItemsFromUrl(itemWebsite.getUrl(), itemWebsite.getRegexpStr(), itemWebsite.getStartStr(), itemWebsite.getEndStr()));
	}
}
