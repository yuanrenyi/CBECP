import request from '@/utils/request'

// 查询供应商仓库信息列表
export function listStorehouse(query) {
  return request({
    url: '/system/supplier/storehouse/list',
    method: 'get',
    params: query
  })
}



// 查询供应商仓库信息列表(全部)
export function listStorehouseAll(query) {
  return request({
    url: '/system/supplier/storehouse/list/all',
    method: 'get',
    params: query
  })
}

// 查询供应商仓库信息详细
export function getStorehouse(id) {
  return request({
    url: '/system/supplier/storehouse/' + id,
    method: 'get'
  })
}

// 新增供应商仓库信息
export function addStorehouse(data) {
  return request({
    url: '/system/supplier/storehouse',
    method: 'post',
    data: data
  })
}

// 修改供应商仓库信息
export function updateStorehouse(data) {
  return request({
    url: '/system/supplier/storehouse',
    method: 'put',
    data: data
  })
}

// 删除供应商仓库信息
export function delStorehouse(id) {
  return request({
    url: '/system/supplier/storehouse/' + id,
    method: 'delete'
  })
}
