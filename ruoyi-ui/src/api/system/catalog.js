import request from '@/utils/request'

// 查询商品分类信息列表(包含递归结构)
export function listInfo(query) {
  return request({
    url: '/catalog/info/list/all',
    method: 'get',
    params: query
  })
}

// 查询商品分类信息列表(不包含递归结构，平铺所有)
export function listInfo2(query) {
  return request({
    url: '/catalog/info/list',
    method: 'get',
    params: query
  })
}

// 查询商品分类信息详细
export function getInfo(id) {
  return request({
    url: '/catalog/info/' + id,
    method: 'get'
  })
}

// 新增商品分类信息
export function addInfo(data) {
  return request({
    url: '/catalog/info',
    method: 'post',
    data: data
  })
}

// 修改商品分类信息
export function updateInfo(data) {
  return request({
    url: '/catalog/info',
    method: 'put',
    data: data
  })
}

// 删除商品分类信息
export function delInfo(id) {
  return request({
    url: '/catalog/info/' + id,
    method: 'delete'
  })
}
