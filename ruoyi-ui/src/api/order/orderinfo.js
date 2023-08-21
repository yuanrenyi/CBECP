import request from '@/utils/request'

// 查询订单信息列表
export function listInfo(query) {
  return request({
    url: '/system/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单信息列表
export function listAftermarketInfo(query) {
  return request({
    url: '/system/order/aftermarket/list',
    method: 'get',
    params: query
  })
}

// 查询订单信息详细
export function getInfo(id) {
  return request({
    url: '/system/order/' + id,
    method: 'get'
  })
}

// 新增订单信息
export function addInfo(data) {
  return request({
    url: '/system/order',
    method: 'post',
    data: data
  })
}

// 修改订单信息
export function updateInfo(data) {
  return request({
    url: '/system/order',
    method: 'put',
    data: data
  })
}

// 删除订单信息
export function delInfo(id) {
  return request({
    url: '/system/order/' + id,
    method: 'delete'
  })
}

export function pushInfo(data) {
  return request({
    url: '/system/order/push',
    method: 'post',
    data
  })
}

// 标记异常订单
export function addAbnormal(data) {
  return request({
    url: 'system/order/abnormal/add',
    method: 'post',
    data
  })
}
// 异常订单添加
export function addAbnormalFollowup(data) {
  return request({
    url: 'system/order/abnormal/followup/add',
    method: 'post',
    data
  })
}


// 异常订单添加
export function addAftermarketFollowup(data) {
  return request({
    url: 'system/order/aftermarket/followup/add',
    method: 'post',
    data
  })
}

// 异常订单列表

export function abnormalFollowupList(data) {
  return request({
    url: 'system/order/abnormal/followup/list',
    method: 'post',
    data
  })
}

export function aftermarketFollowupList(data) {
  return request({
    url: 'system/order/aftermarket/followup/list',
    method: 'post',
    data
  })
}

export function rollbackAbnorma(data) {
  return request({
    url: 'system/order/abnormal/rollback',
    method: 'post',
    data
  })
}

export function getUserType() {
  return request({
    url: '/system/user/getUserType',
    method: 'get'
  })
}

export function getOrderGoodsList(id) {
  return request({
    url: '/system/order/goods/list/' + id,
    method: 'get'
  })
}
