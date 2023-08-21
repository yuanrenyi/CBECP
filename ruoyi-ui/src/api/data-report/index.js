import request from '@/utils/request'

// 查询商品信息列表
export function dataTable() {
  return request({
    url: '/system/data/table/index',
    method: 'get',
    // params: query
  })
}

export function salePercent() {
  return request({
    url: '/system/data/table/seller/sale/percent',
    method: 'get',
    // params: query
  })
}

export function countRank() {
  return request({
    url: '/system/data/table/order/count/rank',
    method: 'get',
    // params: query
  })
}

export function amountRank() {
  return request({
    url: '/system/data/table/amount/count/rank',
    method: 'get',
    // params: query
  })
}

