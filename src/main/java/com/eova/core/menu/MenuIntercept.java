/**
 * Copyright (c) 2013-2015, Jieven. All rights reserved.
 *
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.core.menu;

import com.eova.model.Button;
import com.eova.model.Menu;
import com.eova.model.MenuObject;
import com.eova.model.RoleBtn;
import com.eova.template.crud.CrudIntercept;
import com.jfinal.plugin.activerecord.Record;

public class MenuIntercept implements CrudIntercept {

	public void addBefore(Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void addAfter(Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void addSucceed(Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void deleteBefore(String pkValues) throws Exception {
		Menu menu = Menu.dao.findById(pkValues); 
		
		String code = menu.getStr("code");
		
		// 删除菜单按钮关联权限
		RoleBtn.dao.deleteByMenuCode(code);
		
		// 删除菜单关联按钮
		Button.dao.deleteByMenuCode(code);
		
		// 删除菜单关联对象
		MenuObject.dao.deleteByMenuCode(code);
	}

	public void deleteAfter(String pkValues) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void deleteSucceed(String pkValues) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void updateBefore(Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void updateAfter(Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void updateSucceed(Record record) throws Exception {
		// TODO Auto-generated method stub
		
	}
}