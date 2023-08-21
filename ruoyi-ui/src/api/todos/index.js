import request from '@/utils/request'

// 查询用户待办/已办事项列表
export function listTodos(query) {
  return request({
    url: '/system/todos/list',
    method: 'get',
    params: query
  })
}

// 查询用户待办/已办事项详细
export function getTodos(id) {
  return request({
    url: '/system/todos/' + id,
    method: 'get'
  })
}

// 新增用户待办/已办事项
export function addTodos(data) {
  return request({
    url: '/system/todos',
    method: 'post',
    data: data
  })
}

// 修改用户待办/已办事项
export function updateTodos(data) {
  return request({
    url: '/system/todos',
    method: 'put',
    data: data
  })
}

// 删除用户待办/已办事项
export function delTodos(id) {
  return request({
    url: '/system/todos/' + id,
    method: 'delete'
  })
}
