/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.widget.grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.eova.common.Easy;
import com.eova.config.PageConst;
import com.eova.model.MetaItem;
import com.eova.model.MetaObject;
import com.eova.widget.WidgetManager;
import com.eova.widget.WidgetUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * Grid组件
 * 
 * @author Jieven
 * 
 */
public class GridController extends Controller {

	/**
	 * 分页查询
	 */
	public void query() {

		// Get MetaObject Code
		String code = getPara(0);

		// Get MetaObject and MetaItem List
		MetaObject eo = MetaObject.dao.getByCode(code);
		List<MetaItem> eis = MetaItem.dao.queryByObjectCode(code);

		// 获取分页参数
		int pageNumber = getParaToInt(PageConst.PAGENUM, 1);
		int pageSize = getParaToInt(PageConst.PAGESIZE, 95);

		// 获取条件
		List<String> parmList = new ArrayList<String>();
		String where = WidgetManager.getWhere(this, eis, parmList, "");
		// 转换SQL参数为Obj[]
		Object[] parm = new Object[parmList.size()];
		parmList.toArray(parm);
		// 获取排序
		String sort = WidgetManager.getSort(this, eo);

		// 分页查询Grid数据
		String view = eo.getView();
		String sql = "from " + view + where + sort;
		Page<Record> page = Db.use(eo.getStr("dataSource")).paginate(pageNumber, pageSize, "select *", sql, parm);

		// 创建主键列副本
		WidgetUtil.copyPkValue(page.getList(), eo.getStr("pkName"));
		// 根据表达式将数据中的值翻译成汉字
		WidgetManager.buildExpValue(eis, page.getList());

		// 将分页数据转换成JSON
		String json = JsonKit.toJson(page.getList());
		json = "{\"total\":" + page.getTotalRow() + ",\"rows\":" + json + "}";
		System.out.println(json);

		renderJson(json);
	}

	/**
	 * 新增
	 */
	public void add() {
		String json = getPara("rows");

		System.out.println(json);

		List<Record> records = getRecordsByJson(json);
		for (Record re : records) {
			Db.save("users", re);
		}

		renderJson(new Easy());
	}

	/**
	 * 删除
	 */
	public void delete() {
		String json = getPara("rows");

		System.out.println(json);

		List<Record> records = getRecordsByJson(json);
		for (Record re : records) {
			Db.delete("users", re);
		}

		renderJson(new Easy());
	}

	/**
	 * 更新 Json:[{"id":1,"loginId":"111"},{"id":2,"loginId":"222"}]
	 * 
	 * @throws IOException
	 */
	public void update() throws IOException {
		String json = getPara("rows");

		System.out.println(json);

		List<Record> records = getRecordsByJson(json);
		for (Record re : records) {
			Db.update("users", re);
		}

		renderJson(new Easy());
	}
	
	/**
	 * json转List
	 * @param json
	 * @return
	 */
	private static List<Record> getRecordsByJson(String json) {
		List<Record> records = new ArrayList<Record>();

		List<JSONObject> list = JSON.parseArray(json, JSONObject.class);
		for (JSONObject o : list) {
			Map<String, Object> map = JSON.parseObject(o + "", new TypeReference<Map<String, Object>>() {
			});
			Record re = new Record();
			re.setColumns(map);
			// 删除非持久化列
			re.remove("grid_pk");
			records.add(re);
		}

		return records;
	}

	public static void main(String[] args) {

		String sl = "[{'id':1,'loginId':'111'},{'id':2,'loginId':'222'}]";
		List<JSONObject> list = JSON.parseArray(sl, JSONObject.class);
		for (JSONObject o : list) {
			Map<String, Object> map = JSON.parseObject(o + "", new TypeReference<Map<String, Object>>() {
			});
			Record re = new Record();
			re.setColumns(map);
			System.out.println(re.toJson());
		}
	}

}