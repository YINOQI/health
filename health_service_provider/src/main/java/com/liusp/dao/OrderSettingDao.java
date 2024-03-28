package com.liusp.dao;

import com.liusp.pojo.OrderSetting;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);
    public void editNumberByOrderDate(OrderSetting orderSetting);
    public long findCountByOrderDate(Date orderDate);
    List<OrderSetting> getOrderSettingByMonth(Map map);
    //更新已预约人数
    public void editReservationsByOrderDate(OrderSetting orderSetting);
    //根据预约日期查询预约设置信息
    public OrderSetting findByOrderDate(Date orderDate);
}