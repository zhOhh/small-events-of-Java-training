package com.mhl.view;

import com.mhl.domain.*;
import com.mhl.service.BillService;
import com.mhl.service.DiningTableService;
import com.mhl.service.EmployeeService;
import com.mhl.service.MenuService;
import com.mhl.utils.Utility;

import java.util.List;

/**
 * 主界面
 */
public class MHLView {

    //控制是否退出菜单
    private boolean loop = true;
    private String key = "";//接收用户选择
    //定义EmployeeService属性
    private EmployeeService employeeService = new EmployeeService();
    //定义DiningTable属性
    private DiningTableService diningTableService = new DiningTableService();
    //定义MenuService属性
    private MenuService menuService = new MenuService();
    //定义BillService属性
    private BillService billService = new BillService();

    public static void main(String[] args) {
        new MHLView().mainMenu();
    }

    //结账
    public void payBill() {
        System.out.println("===============结账==================");
        System.out.println("请选择要结账的桌号（-1退出）：");
        int diningTableId = Utility.readInt();
        if (diningTableId == -1) {
            System.out.println("==================取消结账==================");
            return;
        }
        //验证餐桌是否存在
        DiningTable diningTable = diningTableService.getDiningTableById(diningTableId);
        if (diningTable == null) {
            System.out.println("=================桌号不存在==================");
            return;
        }
        //验证是否已经结账
        if(!billService.hasPayBillByDiningTableId(diningTableId)) {
            System.out.println("=================该餐位没有未结账的账单==================");
            return;
        }
        System.out.println("请选择结账方式（现金/微信/支付宝）");
        String payMode = Utility.readString(20, "");
        if("".equals(payMode)){
            System.out.println("==================取消结账==================");
            return;
        }
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {
            if (billService.payBill(diningTableId, payMode)) {
                System.out.println("==================结账成功==================");
            }else {
                System.out.println("==================结账失败==================");
            }
        }else {
            System.out.println("==================取消结账==================");

        }


    }

    //显示账单信息
    public void listBill() {

//        System.out.println("===============查看账单==================");
//        List<Bill> bills = billService.list();
//        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态");
//        for (Bill bill : bills) {
//            System.out.println(bill);
//        }
//        System.out.println("==================显示完毕==================");

        System.out.println("===============查看账单==================");
        List<MultiTableBean> multiTableBeans = billService.list2();
        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态\t\t菜品名\t\t价格");
        for (MultiTableBean bill : multiTableBeans) {
            System.out.println(bill);
        }
        System.out.println("==================显示完毕==================");
    }

    //点餐服务
    public void orderMenu() {
        System.out.println("===============点餐服务==================");
        System.out.println("请输入点餐的桌号（-1退出）：");
        int orderDiningTableId = Utility.readInt();
        if (orderDiningTableId == -1) {
            System.out.println("==================取消点餐服务==================");
            return;
        }
        System.out.println("请输入点餐的菜品号（-1退出）：");
        int orderMenuId = Utility.readInt();
        if (orderMenuId == -1) {
            System.out.println("==================取消点餐服务==================");
            return;
        }
        System.out.println("请输入点餐的菜品量号（-1退出）：");
        int orderNums = Utility.readInt();
        if (orderNums == -1) {
            System.out.println("==================取消点餐服务==================");
            return;
        }

        //验证餐桌号是否存在
        DiningTable diningTable = diningTableService.getDiningTableById(orderDiningTableId);
        if (diningTable == null) {
            System.out.println("==================餐桌号不存在==================");
            return;
        }

        //验证菜品号是否存在
        Menu menu = menuService.getMenuById(orderMenuId);
        if (menu == null) {
            System.out.println("==================菜品编号不存在==================");
            return;
        }

        //点餐
        if(billService.orderMenu(orderMenuId,orderNums,orderDiningTableId)){
            System.out.println("==================点餐成功==================");
        }else {
            System.out.println("==================点餐失败==================");
        }

    }

    //显示所有菜品
    public void listMenu() {
        System.out.println("===============显示所有菜品==================");
        List<Menu> list = menuService.list();
        System.out.println("\n菜品编号\t\t菜品名称\t\t类别\t\t价格");
        for (Menu menu : list) {
            System.out.println(menu);
        }
        System.out.println("==================显示完毕==================");
    }


    //预定餐桌
    public void orderDiningTable() {
        System.out.println("===============预定餐桌==================");
        System.out.println("请选择要预定的餐桌编号（-1退出）：");
        int orderId = Utility.readInt();
        if (orderId == -1) {
            System.out.println("==================取消预订餐桌==================");
            return;
        }
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {

            DiningTable diningTable = diningTableService.getDiningTableById(orderId);
            if (diningTable== null) {
                System.out.println("==================预定餐桌不存在==================");
                return;
            }
            if(!("空".equals(diningTable.getState()))) {
                System.out.println("==================预定餐桌已被占用==================");
                return;
            }

            //接收预定信息
            System.out.println("请输入预定人姓名：");
            String orderName = Utility.readString(50);
            System.out.println("请输入预定人电话：");
            String orderTel = Utility.readString(50);

            //更新餐桌状态
            if(diningTableService.orderDiningTable(orderId,orderName,orderTel)){
                System.out.println("==================预定成功==================");
            }else {
                System.out.println("==================预定失败==================");
            }

        }else {
            System.out.println("==================取消预订餐桌==================");
        }

    }

    //显示所有餐桌状态
    public void listDiningTable() {
        System.out.println("===============餐桌状态==================");
        List<DiningTable> list = diningTableService.list();
        System.out.println("\n餐桌编号\t\t餐桌状态");
        for (DiningTable diningTable : list) {
            System.out.println(diningTable);
        }
        System.out.println("===============显示完毕==================");
    }

    //显示主菜单
    public void mainMenu() {

        while (loop) {

            System.out.println("===============满汉楼==================");
            System.out.println("\t\t 1.登录满汉楼");
            System.out.println("\t\t 2.退出满汉楼");
            System.out.println("请输入你的选择：");
            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.println("输入工号：");
                    String empid = Utility.readString(50);
                    System.out.println("输入密码：");
                    String pwd = Utility.readString(50);
                    Employee employee = employeeService.getEmployeeByIdAndPwd(empid, pwd);
                    if (employee != null) {
                        System.out.println("===============登录成功[" + employee.getName() + "]==================\n");
                        //显示二级菜单
                        while (loop) {
                            System.out.println("\n===============满汉楼(二级菜单)==================");
                            System.out.println("\t\t 1.显示餐桌状态");
                            System.out.println("\t\t 2.预定餐桌");
                            System.out.println("\t\t 3.显示所有菜品");
                            System.out.println("\t\t 4.点餐服务");
                            System.out.println("\t\t 5.查看账单");
                            System.out.println("\t\t 6.结账");
                            System.out.println("\t\t 9.退出满汉楼");
                            System.out.println("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                   listDiningTable();//显示所有餐桌状态
                                    break;
                                case "2":
                                    orderDiningTable();//预定餐桌
                                    break;
                                case "3":
                                    listMenu();//显示所有菜品
                                    break;
                                case "4":
                                    orderMenu();//点餐服务
                                    break;
                                case "5":
                                    listBill();//查看账单
                                    break;
                                case "6":
                                    payBill();//结账
                                    break;
                                case "9":
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("输入错误！");
                                    break;
                            }
                        }

                    } else {
                        System.out.println("===============登录失败==================");
                    }
                    break;
                case "2":
                    loop = false;
                    break;
                default:
                    System.out.println("输入错误！");
                    break;
            }

        }
        System.out.println("退出成功！");
    }

}
