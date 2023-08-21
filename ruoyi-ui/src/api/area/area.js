import request from '@/utils/request'

// 查询商品锁库存列表
export function allProvince() {
  return request({
    url: '/cninfo/allProvince',
    method: 'get',
  })
}

export function cityByProvinceID(provinceID) {
  return request({
    url: `/cninfo/${provinceID}/city`,
    method: 'get',
  })
}

export function areaByCityID(cityID) {
  return request({
    url: `/cninfo/${cityID}/area`,
    method: 'get',
  })
}
