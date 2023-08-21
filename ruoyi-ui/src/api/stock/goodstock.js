import request from '@/utils/request'

// 查询商品锁库存列表
export function listLock(query) {
  return request({
    url: '/system/lock/list',
    method: 'get',
    params: query
  })
}

// 查询商品锁库存详细
export function getLock(id) {
  return request({
    url: '/system/lock/' + id,
    method: 'get'
  })
}

// 新增商品锁库存
export function addLock(data) {
  return request({
    url: '/system/lock',
    method: 'post',
    data: data
  })
}

// 修改商品锁库存
export function updateLock(data) {
  return request({
    url: '/system/lock',
    method: 'put',
    data: data
  })
}

// 删除商品锁库存
export function delLock(id) {
  return request({
    url: '/system/lock/' + id,
    method: 'delete'
  })
}
