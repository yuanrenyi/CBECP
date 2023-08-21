import request from '@/utils/request'

// 查询销售方提现记录列表
export function listWithdraw(query) {
  return request({
    url: '/system/seller/withdraw/list',
    method: 'get',
    params: query
  })
}

// 查询销售方提现记录详细
export function getWithdraw(id) {
  return request({
    url: '/system/seller/withdraw/' + id,
    method: 'get'
  })
}

// 新增销售方提现记录
export function addWithdraw(data) {
  return request({
    url: '/system/seller/withdraw',
    method: 'post',
    data: data
  })
}

// 修改销售方提现记录
export function updateWithdraw(data) {
  return request({
    url: '/system/seller/withdraw',
    method: 'put',
    data: data
  })
}

// 删除销售方提现记录
export function delWithdraw(id) {
  return request({
    url: '/system/seller/withdraw/' + id,
    method: 'delete'
  })
}
