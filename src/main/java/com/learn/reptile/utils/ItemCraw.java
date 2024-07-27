package com.learn.reptile.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.learn.reptile.entity.po.Item;

import cn.hutool.http.HttpUtil;

public class ItemCraw {

	public static List<Item> parseItemsFromUrl(String url, String regexpStr, String startStr, String endStr) {
		String html = HttpUtil.get(url);
		if(StringUtils.isNotBlank(endStr)) {
			html = html.substring(html.indexOf(startStr), html.lastIndexOf(endStr));
		} else {
			html = html.substring(html.indexOf(startStr));
		}
		List<Item> items = new ArrayList<>();
//		String html = "<div class=\"goods-item\" id=\"goods-item_z7MB472ioP5md7aDHJ5\" data-gid=\"z7MB472ioP5md7aDHJ5\"><a href=\"/item.htm?id=z7MB472ioP5md7aDHJ5\" target=\"_blank\"><img src=\"https://gw.alicdn.com/bao/uploaded/i1/2212882455386/O1CN01yjk1gr1peq9zB2RU8_!!2212882455386-0-scmitem176000.jpg_310x310.jpg\"></a><a href=\"/item.htm?id=z7MB472ioP5md7aDHJ5\" target=\"_blank\"><div class=\"quan_info\"><div><span class=\"fz20\">￥5</span> 券</div><div><span class=\"lh\">满17可用</span></div></div></a><div class=\"item-info\"><div class=\"item_title\"><a href=\"/item.htm?id=z7MB472ioP5md7aDHJ5\" target=\"_blank\">【官补到手9.9元】小猪佩奇儿童皮球6号</a></div><div class=\"info1\"><div class=\"fl flicon\"><i class=\"i_new\" data-txt=\"今日上新\">新品</i><i class=\"i_quan\" data-txt=\"优惠券\"></i><i class=\"i_tm\" data-txt=\"天猫\"></i></div><div class=\"fr\"><span>已售出2000件</span></div></div><div class=\"info2\">￥9.9<span class=\"price_before\">￥14.9</span><a href=\"/item.htm?id=z7MB472ioP5md7aDHJ5\" target=\"_blank\">去抢购&gt;</a></div></div></div>";
		Pattern pattern = Pattern.compile(regexpStr);
//		
		Matcher matcher = pattern.matcher(html);
		// 每一个匹配整体
		while(matcher.find()) {
			Item item = new Item();
			item.setItemId(matcher.group("id"));
			item.setPic(matcher.group("pic"));
			item.setTitle(matcher.group("title"));
			item.setPrice(Double.parseDouble(matcher.group("price")));
			item.setPrePrice(Double.parseDouble(matcher.group("prePrice")));
			items.add(item);
		}
		return items;
	}
	

}
