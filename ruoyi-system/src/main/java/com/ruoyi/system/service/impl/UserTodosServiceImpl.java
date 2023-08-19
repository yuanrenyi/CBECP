package com.ruoyi.system.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.constant.ProjectConstants;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.UserTodosMapper;
import com.ruoyi.system.domain.UserTodos;
import com.ruoyi.system.service.IUserTodosService;

/**
 * 用户待办/已办事项Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@Service
public class UserTodosServiceImpl implements IUserTodosService 
{
    @Autowired
    private UserTodosMapper userTodosMapper;

    /**
     * 查询用户待办/已办事项
     * 
     * @param id 用户待办/已办事项主键
     * @return 用户待办/已办事项
     */
    @Override
    public UserTodos selectUserTodosById(Long id)
    {
        return userTodosMapper.selectUserTodosById(id);
    }

    /**
     * 查询用户待办/已办事项列表
     * 
     * @param userTodos 用户待办/已办事项
     * @return 用户待办/已办事项
     */
    @Override
    public List<UserTodos> selectUserTodosList(UserTodos userTodos) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<UserTodos> todosList = userTodosMapper.selectUserTodosList(userTodos);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, ProjectConstants.USERTODO_RECENT_DAYS);
        Date date = calendar.getTime();

        Date featureDay = sdf.parse(sdf.format(date));

        for(UserTodos user : todosList) {
            Date todoTime = sdf.parse(user.getTodoTime());
            if(todoTime.getTime() <= featureDay.getTime()) {
                user.setRecentFlag(1);
            } else {
                user.setRecentFlag(0);
            }
        }
        return todosList;
    }

    /**
     * 新增用户待办/已办事项
     * 
     * @param userTodos 用户待办/已办事项
     * @return 结果
     */
    @Override
    public int insertUserTodos(UserTodos userTodos)
    {
        userTodos.setCreateTime(DateUtils.getNowDate());
        return userTodosMapper.insertUserTodos(userTodos);
    }

    /**
     * 修改用户待办/已办事项
     * 
     * @param userTodos 用户待办/已办事项
     * @return 结果
     */
    @Override
    public int updateUserTodos(UserTodos userTodos)
    {
        userTodos.setUpdateTime(DateUtils.getNowDate());
        return userTodosMapper.updateUserTodos(userTodos);
    }

    /**
     * 批量删除用户待办/已办事项
     * 
     * @param ids 需要删除的用户待办/已办事项主键
     * @return 结果
     */
    @Override
    public int deleteUserTodosByIds(Long[] ids)
    {
        return userTodosMapper.deleteUserTodosByIds(ids);
    }

    /**
     * 删除用户待办/已办事项信息
     * 
     * @param id 用户待办/已办事项主键
     * @return 结果
     */
    @Override
    public int deleteUserTodosById(Long id)
    {
        return userTodosMapper.deleteUserTodosById(id);
    }
}
