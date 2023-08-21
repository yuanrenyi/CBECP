import request from '@/utils/request'

// 查询销售方服务方案列表
export function listPlan(query) {
  return request({
    url: '/system/service/plan/list',
    method: 'get',
    params: query
  })
}

// 查询销售方服务方案详细
export function getPlan(id) {
  return request({
    url: '/system/service/plan/' + id,
    method: 'get'
  })
}

// 新增销售方服务方案
export function addPlan(data) {
  return request({
    url: '/system/service/plan',
    method: 'post',
    data: data
  })
}

// 修改销售方服务方案
export function updatePlan(data) {
  return request({
    url: '/system/service/plan',
    method: 'put',
    data: data
  })
}

// 删除销售方服务方案
export function delPlan(id) {
  return request({
    url: '/system/service/plan/' + id,
    method: 'delete'
  })
}
