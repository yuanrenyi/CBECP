import request from '@/utils/request'

// 查询销售方信息列表
export function listSeller(query) {
  return request({
    url: '/system/seller/list',
    method: 'get',
    params: query
  })
}

export function listAgent(query) {
  return request({
    url: '/system/seller/agent/list',
    method: 'get',
    params: query
  })
}

export function showAddButtonFlag() {
  return request({
    url: '/system/seller/showAddButton/flag',
    method: 'get'
  })
}

// 查询销售方信息详细
export function getSeller(id) {
  return request({
    url: '/system/seller/' + id,
    method: 'get'
  })
}

// 新增销售方信息
export function addSeller(data) {
  return request({
    url: '/system/seller',
    method: 'post',
    data: data
  })
}

// 修改销售方信息
export function updateSeller(data) {
  return request({
    url: '/system/seller',
    method: 'put',
    data
  })
}

// 删除销售方信息
export function delSeller(id) {
  return request({
    url: '/system/seller/' + id,
    method: 'delete'
  })
}

// 跟进记录列表
export function followupList(data) {
  return request({
    url: '/system/seller/followup/list',
    method: 'post',
    data
  })
}

// 添加跟进记录
export function addFollowup(data) {
  return request({
    url: '/system/seller/addFollowup',
    method: 'post',
    data
  })
}

// 跟进记录
export function followupNotice() {
  return request({
    url: '/system/seller/followup/notice',
    method: 'get',
  })
}


// 跟进记录 所有
export function followupListAll(params) {
  return request({
    url: '/system/seller/followup/all',
    method: 'get',
    params
  })
}

export function getUserType() {
  return request({
    url: '/system/user/getUserType',
    method: 'get'
  })
}
