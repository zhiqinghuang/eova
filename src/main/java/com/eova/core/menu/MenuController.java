/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.menu;

import com.eova.common.Easy;
import com.eova.config.EovaConst;
import com.eova.model.Button;
import com.eova.model.Menu;
import com.eova.model.MenuObject;
import com.jfinal.core.Controller;
import com.eova.common.utils.xx;

/**
 * 菜单管理
 * 
 * @author Jieven
 * @date 2014-9-11
 */
public class MenuController extends Controller {

	public void toAdd() {
		render("/eova/menu/form.html");
	}
	
	public void toUpdate() {
		int pkValue = getParaToInt(1);
		Menu menu = Menu.dao.findById(pkValue);
		
		setAttr("menu", menu);

		render("/eova/menu/form.html");
	}

	/**
	 * 新增菜单
	 */
	public void add() {
		
		String menuCode = getPara("code");
		String type = getPara("type");
		
		Menu menu = new Menu();
		menu.set("parentId", getPara("parentId"));
		menu.set("icon", getPara("icon",""));
		menu.set("name", getPara("name"));
		menu.set("code", menuCode);
		menu.set("indexNum", getPara("indexNum"));
		menu.set("type", type);
		menu.set("bizIntercept", getPara("bizIntercept", ""));
		menu.set("url", getPara("url", ""));
		menu.save();
		
		// 如果是父级目录菜单没有按钮也无需关联对象
		if (type.equals(Menu.TYPE_DIR)) {
			renderJson(new Easy());
			return;
		}
		
		// 初始化查询按钮
		Button btn = new Button();
		btn.set("menuCode", menuCode);
		btn.set("name", EovaConst.FUN_QUERY);
		btn.save();
		
		// 单表-菜单关联对象
		if (type.equals(Menu.TYPE_SINGLEGRID)) {
			// 单表只有一个对象
			MenuObject mo = new MenuObject();
			mo.set("menuCode", menuCode);
			mo.set("objectCode", getPara("objectCode"));
			mo.save();
		}
		
		// TODO 其它业务模版
		
		renderJson(new Easy());
	}

	/**
	 * 菜单功能管理
	 */
	public void menuFun() {
		String menuCode = getPara(0);

		String isAdd = getPara("isAdd");
		String isUpdate = getPara("isUpdate");
		String isDelete = getPara("isDelete");

		// 删除当前菜单的功能按钮然后重新添加
		Button.dao.deleteFunByMenuCode(menuCode);

		// 添加查询(有功能点子节点时，Tree无法单选父节点,所以添加空的查询节点 让菜单能保持选中状态)
		// 取消Tree级联选择不存在该问题
		// if (!xx.isEmpty(isAdd) || !xx.isEmpty(isUpdate) ||
		// !xx.isEmpty(isDelete)) {
		// Button btn = new Button();
		// btn.set("menuCode", menuCode);
		// btn.set("name", "查询");
		// btn.set("ui", "");
		// btn.set("bs", "");
		// btn.save();
		// }

		// 添加
		if (!xx.isEmpty(isAdd)) {
			Button btn = new Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_ADD);
			btn.set("ui", EovaConst.FUN_ADD_UI);
			btn.set("bs", EovaConst.FUN_ADD_BS);
			btn.save();
		}
		// 修改
		if (!xx.isEmpty(isUpdate)) {
			Button btn = new Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_UPDATE);
			btn.set("ui", EovaConst.FUN_UPDATE_UI);
			btn.set("bs", EovaConst.FUN_UPDATE_BS);
			btn.save();
		}
		// 删除
		if (!xx.isEmpty(isDelete)) {
			Button btn = new Button();
			btn.set("menuCode", menuCode);
			btn.set("name", EovaConst.FUN_DELETE);
			btn.set("ui", EovaConst.FUN_DELETE_UI);
			btn.set("bs", EovaConst.FUN_DELETE_BS);
			btn.save();
		}

		renderJson(new Easy());
	}

}