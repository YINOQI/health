package com.liusp.service;
import com.liusp.pojo.OrderSetting;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    public void add(List<OrderSetting> list);
    public List<Map> getOrderSettingByMonth(String date);//参数格式为：2019-03

    void editNumberByDate(OrderSetting orderSetting);
}
