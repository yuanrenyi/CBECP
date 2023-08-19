package com.ruoyi.system.service;

import java.text.ParseException;
import java.util.List;
import com.ruoyi.system.domain.UserTodos;

/**
 * 用户待办/已办事项Service接口
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
public interface IUserTodosService 
{
    /**
     * 查询用户待办/已办事项
     * 
     * @param id 用户待办/已办事项主键
     * @return 用户待办/已办事项
     */
    public UserTodos selectUserTodosById(Long id);

    /**
     * 查询用户待办/已办事项列表
     * 
     * @param userTodos 用户待办/已办事项
     * @return 用户待办/已办事项集合
     */
    public List<UserTodos> selectUserTodosList(UserTodos userTodos) throws ParseException;

    /**
     * 新增用户待办/已办事项
     * 
     * @param userTodos 用户待办/已办事项
     * @return 结果
     */
    public int insertUserTodos(UserTodos userTodos);

    /**
     * 修改用户待办/已办事项
     * 
     * @param userTodos 用户待办/已办事项
     * @return 结果
     */
    public int updateUserTodos(UserTodos userTodos);

    /**
     * 批量删除用户待办/已办事项
     * 
     * @param ids 需要删除的用户待办/已办事项主键集合
     * @return 结果
     */
    public int deleteUserTodosByIds(Long[] ids);

    /**
     * 删除用户待办/已办事项信息
     * 
     * @param id 用户待办/已办事项主键
     * @return 结果
     */
    public int deleteUserTodosById(Long id);
}
