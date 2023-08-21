

/**
 * 通用js方法封装处理
 * Copyright (c) 2019 ruoyi
 */

// 日期格式化
export function parseTime(time, pattern) {
  if (arguments.length === 0 || !time) {
    return null
  }
  const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
      time = parseInt(time)
    } else if (typeof time === 'string') {
      time = time.replace(new RegExp(/-/gm), '/').replace('T', ' ').replace(new RegExp(/\.[\d]{3}/gm), '');
    }
    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') { return ['日', '一', '二', '三', '四', '五', '六'][value] }
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
  return time_str
}

// 表单重置
export function resetForm(refName) {
  if (this.$refs[refName]) {
    this.$refs[refName].resetFields();
  }
}

// 添加日期范围
export function addDateRange(params, dateRange, propName) {
  let search = params;
  search.params = typeof (search.params) === 'object' && search.params !== null && !Array.isArray(search.params) ? search.params : {};
  dateRange = Array.isArray(dateRange) ? dateRange : [];
  if (typeof (propName) === 'undefined') {
    search.params['beginTime'] = dateRange[0];
    search.params['endTime'] = dateRange[1];
  } else {
    search.params['begin' + propName] = dateRange[0];
    search.params['end' + propName] = dateRange[1];
  }
  return search;
}

// 回显数据字典
export function selectDictLabel(datas, value) {
  if (value === undefined) {
    return "";
  }
  var actions = [];
  Object.keys(datas).some((key) => {
    if (datas[key].value == ('' + value)) {
      actions.push(datas[key].label);
      return true;
    }
  })
  if (actions.length === 0) {
    actions.push(value);
  }
  return actions.join('');
}

// 回显数据字典（字符串、数组）
export function selectDictLabels(datas, value, separator) {
  if (value === undefined || value.length ===0) {
    return "";
  }
  if (Array.isArray(value)) {
    value = value.join(",");
  }
  var actions = [];
  var currentSeparator = undefined === separator ? "," : separator;
  var temp = value.split(currentSeparator);
  Object.keys(value.split(currentSeparator)).some((val) => {
    var match = false;
    Object.keys(datas).some((key) => {
      if (datas[key].value == ('' + temp[val])) {
        actions.push(datas[key].label + currentSeparator);
        match = true;
      }
    })
    if (!match) {
      actions.push(temp[val] + currentSeparator);
    }
  })
  return actions.join('').substring(0, actions.join('').length - 1);
}

// 字符串格式化(%s )
export function sprintf(str) {
  var args = arguments, flag = true, i = 1;
  str = str.replace(/%s/g, function () {
    var arg = args[i++];
    if (typeof arg === 'undefined') {
      flag = false;
      return '';
    }
    return arg;
  });
  return flag ? str : '';
}

// 转换字符串，undefined,null等转化为""
export function parseStrEmpty(str) {
  if (!str || str == "undefined" || str == "null") {
    return "";
  }
  return str;
}

// 数据合并
export function mergeRecursive(source, target) {
  for (var p in target) {
    try {
      if (target[p].constructor == Object) {
        source[p] = mergeRecursive(source[p], target[p]);
      } else {
        source[p] = target[p];
      }
    } catch (e) {
      source[p] = target[p];
    }
  }
  return source;
};

/**
 * 构造树型结构数据
 * @param {*} data 数据源
 * @param {*} id id字段 默认 'id'
 * @param {*} parentId 父节点字段 默认 'parentId'
 * @param {*} children 孩子节点字段 默认 'children'
 */
export function handleTree(data, id, parentId, children) {
  let config = {
    id: id || 'id',
    parentId: parentId || 'parentId',
    childrenList: children || 'children'
  };

  var childrenListMap = {};
  var nodeIds = {};
  var tree = [];

  for (let d of data) {
    let parentId = d[config.parentId];
    if (childrenListMap[parentId] == null) {
      childrenListMap[parentId] = [];
    }
    nodeIds[d[config.id]] = d;
    childrenListMap[parentId].push(d);
  }

  for (let d of data) {
    let parentId = d[config.parentId];
    if (nodeIds[parentId] == null) {
      tree.push(d);
    }
  }

  for (let t of tree) {
    adaptToChildrenList(t);
  }

  function adaptToChildrenList(o) {
    if (childrenListMap[o[config.id]] !== null) {
      o[config.childrenList] = childrenListMap[o[config.id]];
    }
    if (o[config.childrenList]) {
      for (let c of o[config.childrenList]) {
        adaptToChildrenList(c);
      }
    }
  }
  return tree;
}

/**
* 参数处理
* @param {*} params  参数
*/
export function tansParams(params) {
  let result = ''
  for (const propName of Object.keys(params)) {
    const value = params[propName];
    var part = encodeURIComponent(propName) + "=";
    if (value !== null && value !== "" && typeof (value) !== "undefined") {
      if (typeof value === 'object') {
        for (const key of Object.keys(value)) {
          if (value[key] !== null && value[key] !== "" && typeof (value[key]) !== 'undefined') {
            let params = propName + '[' + key + ']';
            var subPart = encodeURIComponent(params) + "=";
            result += subPart + encodeURIComponent(value[key]) + "&";
          }
        }
      } else {
        result += part + encodeURIComponent(value) + "&";
      }
    }
  }
  return result
}

// 验证是否为blob格式
export async function blobValidate(data) {
  try {
    const text = await data.text();
    JSON.parse(text);
    return false;
  } catch (error) {
    return true;
  }
}
// select 宽度补丁 (修补option选项 宽度无法跟input同宽)
export function focusSelect (ref) {
  // if(!vueInstance || !ref) return
  if(!ref) return
  // console.log(this.$refs[ref])
  // console.log(this.$refs[ref].inputWidth)
  // select 里面有个 resetInputWidth 方法 能获取 inputWidth并将该宽度传递给下拉框
  this.$refs[ref].resetInputWidth()
  // console.log(this.$refs[ref].inputWidth)
}

export  function isEllipsis(ref) {
  if(!ref) return
  console.log('isEllipsis...')
  console.log(this.$refs[ref])
    // let el = this.$refs[ref]
    let el = this.$refs[ref].$el ?  this.$refs[ref].$el : this.$refs[ref]
    let parentNodeStyle = getComputedStyle(el.parentNode)
    let parentWidth = parseFloat(parentNodeStyle.getPropertyValue('width')) // 父元素 宽
    let parentPaddingLeft = parseFloat(parentNodeStyle.getPropertyValue('padding-left')) // 父元素padding-left
    let parentPaddingRight = parseFloat(parentNodeStyle.getPropertyValue('padding-right')) // 父元素padding-right
    let parentRealWidth = parentWidth - parentPaddingLeft - parentPaddingRight // 父元素真正的宽度
    if(el.offsetWidth > parentRealWidth) {
      return false
    }
    return  true
}

// 父元素的 resizeObserver事件
export function checkOverflow(target) {
  // console.log('父元素的 resizeObserver')
  // 父元素
  let parentEl = target
  console.log({target})
  // 找到子元素
  let el = parentEl.querySelector('.ellipse-text')
  let parentNodeStyle = getComputedStyle(parentEl)
  let parentWidth = parseFloat(parentNodeStyle.getPropertyValue('width')) // 父元素 宽
  let parentPaddingLeft = parseFloat(parentNodeStyle.getPropertyValue('padding-left')) // 父元素padding-left
  let parentPaddingRight = parseFloat(parentNodeStyle.getPropertyValue('padding-right')) // 父元素padding-right
  let parentRealWidth = parentWidth - parentPaddingLeft - parentPaddingRight // 父元素真正的宽度
  // 未溢出 则设置为true
  this.tooltip[parentEl.__refName] = el.offsetWidth < parentRealWidth;
  // 35 的来由  el-tooltip 不知道哪来的魔法数字 默认  ArrowOffsetX 的值就是35
  let magicNum
  if(el.offsetWidth> 70  && el.offsetWidth <= 90 ) {
    magicNum = 35
  } else if ( el.offsetWidth > 90 && el.offsetWidth <= 110) {
    magicNum = 45
  } else if(el.offsetWidth > 110 && el.offsetWidth <= 130) {
    magicNum = 55
  } else if(el.offsetWidth > 130 && el.offsetWidth<= 150) {
    magicNum = 65
  }else if(el.offsetWidth > 150 && el.offsetWidth< 170) {
    magicNum = 75
  }
  if(el.offsetWidth > parentRealWidth) {
    this.$nextTick(() =>{
      let  ArrowOffsetX =  Math.floor(magicNum - ((el.offsetWidth/2 + parentPaddingLeft) -  (parentWidth/ 2 )))
      this.tooltip[parentEl.__refName + 'ArrowOffsetX'] =  ArrowOffsetX <= 0 ? 1 :  ArrowOffsetX
      console.log('本次的el.offsetWidth,parentRealWidth,ArrowOffsetX')
      console.log(el.offsetWidth,parentRealWidth,this.tooltip[parentEl.__refName + 'ArrowOffsetX'] )
      // arrow需要向左便宜的距离
      this.tooltip[parentEl.__refName + 'OffsetX'] = Math.ceil((el.offsetWidth /2  + parentPaddingLeft) - (parentWidth / 2))    // 需要向左偏移的距离
    })

  }
}
