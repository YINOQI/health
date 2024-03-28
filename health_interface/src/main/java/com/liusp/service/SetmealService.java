package com.liusp.service;
import com.liusp.entity.PageResult;
import com.liusp.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * 体检套餐服务接口
 */
public interface SetmealService {
    public void add(Setmeal setmeal, Integer[] checkgroupIds);
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);
    Setmeal findById(Integer id);
    List<Integer> findCheckGroupIdsBySetmealId(Integer id);
    public void edit(Setmeal setmeal,Integer[] checkgroupIds);
    public void delete(Integer id);

    List<Setmeal> findAll();

    Setmeal findById(int id);

    List<Map<String, Object>> findSetmealCount();

}
