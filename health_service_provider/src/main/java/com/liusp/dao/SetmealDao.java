package com.liusp.dao;
import com.github.pagehelper.Page;
import com.liusp.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    public void add(Setmeal setmeal);
    public void setSetmealAndCheckGroup(Map<String, Integer> map);
    public Page<Setmeal> selectByCondition(String queryString);
    Setmeal findById(Integer id);
    List<Integer> findCheckGroupIdsBySetmealId(Integer id);
    public void edit(Setmeal setmeal);
    void deleteAssociation(Integer id);
    public void deleteById(Integer id);

    List<Setmeal> findAll();

    public Setmeal findById(int id);
}