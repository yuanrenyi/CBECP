import request from '@/utils/request'

// 提现列表
export function listWithdrawSupplier(query) {
  return request({
    url: '/system/supplier/withdrawal/list',
    method: 'get',
    params: query
  })
}

// 申请提现
export function addWithdrawSupplier(data) {
  return request({
    url: '/system/supplier/withdrawal',
    method: 'post',
    data: data
  })
}

// 提现详情
export function withdrawInfoSupplier(id) {
  return request({
    url: `/system/supplier/withdrawal/${id}`,
    method: 'get',
  })
}

// 提现审核
export function auditWithdrawSupplier(data) {
  return request({
    url: '/system/supplier/withdrawal/audit',
    method: 'post',
    data: data
  })
}
