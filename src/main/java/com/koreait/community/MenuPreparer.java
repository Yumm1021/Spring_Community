package com.koreait.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.koreait.community.model.BoardManageEntity;

@Component("MenuPreparer")
public class MenuPreparer implements ViewPreparer{
	
	@Autowired
	private CommonMapper mapper;

	@Override
	public void execute(Request tilesContext, AttributeContext attributeContext) {
		
		if(Const.menus == null) {
			Const.menus = mapper.selMenuList();
		}
		
		attributeContext.putAttribute(Const.KEY_MENULIST, new Attribute(Const.menus), true);
	}

}
