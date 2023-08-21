import request from '@/utils/request'

// 提现列表
export function listWithdrawSeller(query) {
  return request({
    url: '/system/seller/withdrawal/list',
    method: 'get',
    params: query
  })
}

// 申请提现
export function addWithdrawSeller(data) {
  return request({
    url: '/system/seller/withdrawal',
    method: 'post',
    data: data
  })
}

export function withdrawInfoSeller(id) {
  return request({
    url: `/system/seller/withdrawal/${id}`,
    method: 'get',
  })
}
