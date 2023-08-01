package com.mhl.service;

import com.mhl.dao.BillDAO;
import com.mhl.dao.MultiTableDAO;
import com.mhl.domain.Bill;
import com.mhl.domain.MultiTableBean;
import sun.net.www.content.audio.basic;

import java.util.List;
import java.util.UUID;

public class BillService {
    private BillDAO billDAO = new BillDAO();
    private MenuService menuService = new MenuService();
    private DiningTableService diningTableService = new DiningTableService();
    private MultiTableDAO multiTableDAO = new MultiTableDAO();

    public boolean orderMenu(int menuId, int nums, int diningTableId) {
        String billID = UUID.randomUUID().toString();

        int update = billDAO.update("insert into bill values(null,?,?,?,?,?,now(),'未支付')",
                billID, menuId, nums, menuService.getMenuById(menuId).getPrice() * nums, diningTableId);

        if (update <= 0) {
            return false;
        }

        return diningTableService.updateDiningTableState(diningTableId, "就餐中");

    }

    //返回所有账单
    public List<Bill> list() {
        return billDAO.queryMulti("select * from bill", Bill.class);
    }
    public List<MultiTableBean> list2() {
        return multiTableDAO.queryMulti("SELECT bill.*,NAME,price " +
                "FROM bill,menu " +
                "WHERE bill.menuId = menu.id", MultiTableBean.class);
    }

    //查看某个餐桌结账状态
    public boolean hasPayBillByDiningTableId(int diningTableId) {
        Bill bill = billDAO.querySingle("SELECT * FROM bill WHERE diningTableId=? AND state='未支付'LIMIT 0,1", Bill.class, diningTableId);
        return bill != null;
    }

    //完成结账
    public boolean payBill(int diningTableId, String payMode) {

        int update = billDAO.update("update bill set state=? where state='未支付' and diningTableId=?", payMode, diningTableId);
        if (update <= 0) {
            return false;
        }

        if (!diningTableService.updateDiningTableToFree(diningTableId, "空")) {
            return false;
        }

        return true;

    }

}
