package com.liusp.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.liusp.constant.RedisConstant;
import com.liusp.dao.SetmealDao;
import com.liusp.entity.PageResult;
import com.liusp.pojo.Setmeal;
import com.liusp.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 体检套餐服务实现类
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    StringRedisTemplate redisTemplate;  //操作k-v都是字符串的

    //新增套餐
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        if(checkgroupIds != null && checkgroupIds.length > 0){
            //绑定套餐和检查组的多对多关系
            setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        }
        //将图片名称保存到Redis
        savePic2Redis(setmeal.getImg());
    }

    //将图片名称保存到Redis
    private void savePic2Redis(String pic){
        redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }

    //绑定套餐和检查组的多对多关系
    private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            Map<String,Integer> map = new HashMap<>();
            map.put("setmeal_id",id);
            map.put("checkgroup_id",checkgroupId);
            setmealDao.setSetmealAndCheckGroup(map);
        }
    }
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    public List<Integer> findCheckGroupIdsBySetmealId(Integer id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    //编辑套餐，同时需要更新和检查组的关联关系
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        //根据套餐id删除中间表数据（清理原有关联关系）
        setmealDao.deleteAssociation(setmeal.getId());
        //向中间表(t_setmeal_checkgroup)插入数据（建立检查组和检查项关联关系）
        setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        //更新套餐基本信息
        setmealDao.edit(setmeal);
    }

    //删除
    public void delete(Integer id) throws RuntimeException {
        setmealDao.deleteAssociation(id);
        setmealDao.deleteById(id);
    }

    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }
}