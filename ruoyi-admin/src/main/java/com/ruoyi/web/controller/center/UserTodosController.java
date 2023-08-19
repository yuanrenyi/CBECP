package com.ruoyi.web.controller.center;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.UserTodos;
import com.ruoyi.system.service.IUserTodosService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户待办/已办事项Controller
 * 
 * @author ruoyi
 * @date 2023-01-02
 */
@RestController
@RequestMapping("/system/todos")
public class UserTodosController extends BaseController
{
    @Autowired
    private IUserTodosService userTodosService;

    /**
     * 查询用户待办/已办事项列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserTodos userTodos) throws ParseException {
        userTodos.setCreateBy(String.valueOf(getUserId()));
        startPage();
        List<UserTodos> list = userTodosService.selectUserTodosList(userTodos);
        return getDataTable(list);
    }

    /**
     * 导出用户待办/已办事项列表
     */
    @Log(title = "用户待办/已办事项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserTodos userTodos) throws ParseException {
        userTodos.setCreateBy(String.valueOf(getUserId()));
        List<UserTodos> list = userTodosService.selectUserTodosList(userTodos);
        ExcelUtil<UserTodos> util = new ExcelUtil<UserTodos>(UserTodos.class);
        util.exportExcel(response, list, "用户待办/已办事项数据");
    }

    /**
     * 获取用户待办/已办事项详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userTodosService.selectUserTodosById(id));
    }

    /**
     * 新增用户待办/已办事项
     */
    @Log(title = "用户待办/已办事项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserTodos userTodos)
    {
        userTodos.setCreateBy(String.valueOf(getUserId()));
        return toAjax(userTodosService.insertUserTodos(userTodos));
    }

    /**
     * 修改用户待办/已办事项
     */
    @Log(title = "用户待办/已办事项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserTodos userTodos)
    {
        userTodos.setUpdateBy(String.valueOf(getUserId()));
        if(userTodos.getTodoStatus() == 1) {
            userTodos.setCompleteTime(new Date());
        }
        return toAjax(userTodosService.updateUserTodos(userTodos));
    }

    /**
     * 删除用户待办/已办事项
     */
    @Log(title = "用户待办/已办事项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userTodosService.deleteUserTodosByIds(ids));
    }
}
