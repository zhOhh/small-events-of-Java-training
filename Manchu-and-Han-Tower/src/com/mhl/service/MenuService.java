package com.mhl.service;

import com.mhl.dao.MenuDAO;
import com.mhl.domain.Menu;

import java.util.List;

public class MenuService {

    private MenuDAO menuDAO = new MenuDAO();

    //返回所有菜品
    public List<Menu> list() {
        return menuDAO.queryMulti("select * from menu", Menu.class);
    }

    public Menu getMenuById(int id) {
        return menuDAO.querySingle("select * from menu where id =?", Menu.class, id);
    }

}
