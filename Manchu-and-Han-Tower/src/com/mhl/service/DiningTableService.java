package com.mhl.service;

import com.mhl.dao.DingingTableDAO;
import com.mhl.domain.DiningTable;

import java.util.List;

public class DiningTableService {
    private DingingTableDAO dingingTableDAO = new DingingTableDAO();

    public List<DiningTable> list() {

        return dingingTableDAO.queryMulti("select id, state from diningTable", DiningTable.class);
    }

    //根据id 查询对应的餐桌DinindTable对象，如果返回null，则表示该餐桌不存在
    public DiningTable getDiningTableById(int id) {

        return dingingTableDAO.querySingle("select * from diningTable where id =?", DiningTable.class, id);
    }

    //如果餐桌可以预定 调用方法 预定
    public boolean orderDiningTable(int id,String orderName,String orderTel){
        int update = dingingTableDAO.update("update diningTable set state='已经预定', orderName=?,orderTel=?where id=?", orderName, orderTel,id);
        return update > 0;
    }

    public boolean updateDiningTableState(int id,String state){
        int update = dingingTableDAO.update("update diningTable set state=? where id=?", state,id);
        return update > 0;
    }

    public boolean updateDiningTableToFree(int id,String state){
        int update = dingingTableDAO.update("update diningTable set state=?,orderName='',orderTel='' where id=?", state,id);
        return update > 0;
    }

}
