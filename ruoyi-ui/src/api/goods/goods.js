import request from '@/utils/request'

// 查询商品信息列表
export function listGoods(query) {
  return request({
    url: '/system/goods/list',
    method: 'get',
    params: query
  })
}

// 查询商品信息详细
export function getGoods(id) {
  return request({
    url: '/system/goods/' + id,
    method: 'get'
  })
}

// 新增商品信息
export function addGoods(data) {
  return request({
    url: '/system/goods',
    method: 'post',
    data: data
  })
}

// 修改商品信息
export function updateGoods(data) {
  return request({
    url: '/system/goods',
    method: 'put',
    data: data
  })
}

// 删除商品信息
export function delGoods(id) {
  return request({
    url: '/system/goods/' + id,
    method: 'delete'
  })
}


// 添加商品图片
export function addGoodPic(data) {
  return request({
    url: '/system/goods/pic',
    method: 'post',
    data
  })
}

// 删除商品图片
export function delGoodPic(ids) {
  return request({
    url: `/system/goods/pic/${ids}`,
    method: 'delete',
  })
}

// 删除商品图片
export function delGoodAllPic(id) {
  return request({
    url: `/system/goods/pic/delete/${id}`,
    method: 'delete',
  })
}

export function rollbackGoods(data) {
  return request({
    url: '/system/goods',
    method: 'put',
    data: data
  })
}
//  goods:info:onshelf 平台上架商品
export function platOnshelf(data) {
  return request({
    url: '/system/goods/onshelf/plat',
    method: 'post',
    data: data
  })
}


export function goodAudit(data) {
  return request({
    url: '/system/goods/audit',
    method: 'post',
    data
  })
}
// 销售方商品列表
export function goodSellerList(params) {
  return request({
    url: '/system/goods/seller/list',
    method: 'get',
    params
  })
}

export function goodSellerDetail(data) {
  return request({
    url: '/system/goods/seller/detail',
    method: 'post',
    data
  })
}

export function goodSellerPic(data) {
  return request({
    url: '/system/goods/getPicById',
    method: 'post',
    data
  })
}


export function sellerPutshelf(data) {
  return request({
    url: '/system/goods/seller/putshelf',
    method: 'post',
    data
  })
}

export function sellerOffshelf(data) {
  return request({
    url: '/system/goods/seller/offshelf',
    method: 'post',
    data
  })
}

export function goodspicDownLoad(goodsId) {
  return request({
    url: '/system/goods/goodspic/batch/download?goodsId=' + goodsId ,
    method: 'post',
    timeout: 60 * 1000
  })
}
/*商品动销*/
export function goodsSale(params) {
  return request({
    url: '/system/data/table/goods/sale',
    method: 'get',
    params
  })
}


export function goodsSalePrice7d(params) {
  return request({
    url: `/system/data/table/goods/price/7`,
    method: 'get',
    params
  })
}

export function uploadImg({goodsBarcode, picType, file, picVersion}) {
  let data =  new FormData()
  data.append('goodsBarcode',goodsBarcode)
  data.append('picType',picType)
  data.append('file',file)
  data.append('picVersion',picVersion)
  return request({
    url: `/common/upload/cbecp/image`,
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data;' },
    data
  })
}
