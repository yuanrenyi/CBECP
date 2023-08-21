import request from '@/utils/request'

// 查询保税仓列表(包含递归子集)
export function listInfo(query) {
  return request({
    url: '/bw/info/list/all',
    method: 'get',
    params: query
  })
}

// 查询保税仓列表
export function listInfo2(query) {
  return request({
    url: '/bw/info/list',
    method: 'get',
    params: query
  })
}

// 查询保税仓详细
export function getInfo(id) {
  return request({
    url: '/bw/info/' + id,
    method: 'get'
  })
}

// 新增保税仓
export function addInfo(data) {
  return request({
    url: '/bw/info',
    method: 'post',
    data: data
  })
}

// 修改保税仓
export function updateInfo(data) {
  return request({
    url: '/bw/info',
    method: 'put',
    data: data
  })
}

// 删除保税仓
export function delInfo(id) {
  return request({
    url: '/bw/info/' + id,
    method: 'delete'
  })
}
