package com.liusp.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liusp.dao.OrderSettingDao;
import com.liusp.pojo.OrderSetting;
import com.liusp.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * 预约设置服务
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    //批量添加
    public void add(List<OrderSetting> list) {
        if(list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                //检查此数据（日期）是否存在
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if(count > 0){
                    //已经存在，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else{
                    //不存在，执行添加操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

//    //根据日期查询预约设置数据
//    public List<Map> getOrderSettingByMonth(String date) {//2019-3
//        String dateBegin = date + "-1";//2019-3-1
//        String dateEnd ="";
//        String temp = date.substring(date.length() - 1);
//        if (temp.equals("6") || temp.equals("9")|| temp.equals("11")){
//            dateEnd = date + "-30";//2019-3-31
//        }else{
//            dateEnd = date + "-31";//2019-3-31
//        }
//
//        Map map = new HashMap();
//        map.put("dateBegin",dateBegin);
//        map.put("dateEnd",dateEnd);
//        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
//        List<Map> data = new ArrayList<>();
//        for (OrderSetting orderSetting : list) {
//            Map orderSettingMap = new HashMap();
//            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//获得日期（几号）
//            orderSettingMap.put("number",orderSetting.getNumber());//可预约人数
//            orderSettingMap.put("reservations",orderSetting.getReservations());//已预约人数
//            data.add(orderSettingMap);
//        }
//        return data;
//    }

    //根据日期查询预约设置数据
    public List<Map> getOrderSettingByMonth(String date) {//2019-3
        String dateBegin = date + "-1";//2019-3-1
        String dateEnd ="";
        String temp = date.split("-")[1];
        if (temp.equals("4") || temp.equals("6") || temp.equals("9")|| temp.equals("11")){
            dateEnd = date + "-30";//2019-4-30
        }else if (temp.equals("2")) {
            int year = Integer.parseInt(date.split("-")[0]);
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                dateEnd = date + "-29";//2024-2-29
            } else {
                dateEnd = date + "-28";//2019-2-28
            }
        }else {
            dateEnd = date + "-31";//2019-3-31
        }
        Map map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number",orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations",orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }

    //根据日期修改可预约人数
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(count > 0){
            //当前日期已经进行了预约设置，需要进行修改操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //当前日期没有进行预约设置，进行添加操作
            orderSettingDao.add(orderSetting);
        }
    }
}