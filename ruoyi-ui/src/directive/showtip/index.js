const compareWidth = (el,vm) => {
  // 如果没有超出宽度，即子<父 则不显示tooltip
  console.log("vm.disabled")
  console.log(vm.disabled)
  if (el.querySelector('.ellipse-text') && el.querySelector('.ellipse-text').offsetWidth < el.parentNode.offsetWidth) {
    vm.disabled = true
  }else {
    vm.disabled = false
  }
};
const hoverCompareWidth = el =>{
  console.log('hoverCompareWidth')
  el.addEventListener('mouseenter',hoverFn)
}
function hoverFn (e) {
  console.log('e.target',e.target)
  compareWidth(e.target)
}
export default  {
    // 只调用一次，指令第一次绑定到元素时调用。在这里可以进行一次性的初始化设置。
    bind: (el, binding,vNode) => {
      // bind的时候无法获取到已经带有ajax数据的DOM元素，宽度为0
      console.log({el,binding,vNode})
      console.log(binding)
      const {vm} = binding.value
      console.log({vm})
      compareWidth(el,vm)
    },
    // 被绑定元素插入父节点时调用 (仅保证父节点存在，但不一定已被插入文档中)。
    inserted: (el) => {
      console.log('inserted')

    },
    // 所在组件的 VNode 更新时调用，但是可能发生在其子 VNode 更新之前。指令的值可能发生了改变，也可能没有。但是你可以通过比较更新前后的值来忽略不必要的模板更新
    update: (el, binding,vNode, oldNode) => {
      compareWidth(el); // 可以获取到ajax数据的DOM元素，即真实的宽度
    },
    unbind: (el) => {
      el.removeEventListener('mouseenter',hoverFn())
    }
}

