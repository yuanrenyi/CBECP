import request from '@/utils/request'

// 查询供应商提现记录列表
export function listWithdraw(query) {
  return request({
    url: '/system/supplier/withdraw/list',
    method: 'get',
    params: query
  })
}

// 查询供应商提现记录详细
export function getWithdraw(id) {
  return request({
    url: '/system/supplier/withdraw/' + id,
    method: 'get'
  })
}

// 新增供应商提现记录
export function addWithdraw(data) {
  return request({
    url: '/system/supplier/withdraw',
    method: 'post',
    data: data
  })
}

// 修改供应商提现记录
export function updateWithdraw(data) {
  return request({
    url: '/system/supplier/withdraw',
    method: 'put',
    data: data
  })
}

// 删除供应商提现记录
export function delWithdraw(id) {
  return request({
    url: '/system/supplier/withdraw/' + id,
    method: 'delete'
  })
}
