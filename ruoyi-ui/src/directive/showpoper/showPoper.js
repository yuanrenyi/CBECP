import { createPopper } from '@popperjs/core'


function show(e) {
  const el = e.target
  // const arrow = document.querySelector('#arrow')
  let parentNodeStyle = getComputedStyle(el.parentNode)
  let parentWidth = parseFloat(parentNodeStyle.getPropertyValue('width')) // 父元素 宽
  let parentPaddingLeft = parseFloat(parentNodeStyle.getPropertyValue('padding-left')) // 父元素padding-left
  let parentPaddingRight = parseFloat(parentNodeStyle.getPropertyValue('padding-right')) // 父元素padding-right
  let parentRealWidth = parentWidth - parentPaddingLeft - parentPaddingRight // 父元素真正的宽度

  let elWidth =  el.offsetWidth // 元素宽

  if(elWidth < parentRealWidth) return /* arrow.removeAttribute('data-show') */
  let offsetX =  (elWidth - parentWidth) / 2  // 需要向左便宜的距离
  // console.log({offsetX})
  const tooltip = document.querySelector('#tooltip')
  let contentBox = tooltip.querySelector('.content')
  contentBox.innerText =  el.__tooltipContent
  let popperInstance = createPopper(el, tooltip, {
    placement: 'top-start',
    modifiers: [
      {
        name: 'offset',
        options: {
          offset: [-(offsetX + parentPaddingLeft) ,  parseFloat(el.__tooltipY) || 20],
        },
      },
      // {
      //   name: 'arrow',
      //   options: {
      //     padding: ({ popper, reference, placement }) =>
      //       popper.width / reference.width,
      //   },
      // },
      {
        name: 'applyArrowChange',
        enabled: true,
        phase: 'write',
        fn({ state }) {
          const { arrow } = state.elements;
          if (arrow) {
            let arrowStyle = getComputedStyle(arrow)
            let elTransform =  arrowStyle.getPropertyValue('transform') //
            if(elTransform === 'none') return
            const reg = /(?<=\()(.+?)(?=\))/g;
            let oldX = elTransform.match(reg)[0].split(',')[4] // 获取元素的translateX
            // console.log({oldX})
            arrow.style.transform = `translate(${oldX - offsetX - parentPaddingLeft}px,0px)`
          }
        },
      }
    ],
  })

  // 浏览器的bug?  style.getPropertyValue('widht') 获取不到真实的宽度 这里处理一下
  if (el.offsetWidth > parentRealWidth ) {
    tooltip.setAttribute('data-show','');
    // arrow.setAttribute('data-show','');
    popperInstance.update();
  }
}

function hide(e) {
  const tooltip = document.querySelector('#tooltip')
  // const arrow = document.querySelector('#arrow')
  tooltip.removeAttribute('data-show');
  // arrow.removeAttribute('data-show');
}

const showEvents = ['mouseenter', 'focus'];
const hideEvents = ['mouseleave', 'blur'];

// showEvents.forEach((event) => {
//   button.addEventListener(event, show);
// });
//
// hideEvents.forEach((event) => {
//   button.addEventListener(event, hide);
// });


export default {
  // 只调用一次，指令第一次绑定到元素时调用。在这里可以进行一次性的初始化设置。
  bind: (el, binding, vnode, oldVnode) => {
    // bind的时候无法获取到已经带有ajax数据的DOM元素，宽度为0
    // console.log({el,binding})

  },
  // 被绑定元素插入父节点时调用 (仅保证父节点存在，但不一定已被插入文档中)。
  inserted: (el, binding, vnode, oldVnode) => {
    console.log({binding})
    showEvents.forEach((event) => {
      const { content, x, y} = binding.value
      el.__tooltipContent = content
      el.__tooltipX = x
      el.__tooltipY = parseFloat(y)
      el.addEventListener(event, show);
    });

    hideEvents.forEach((event) => {
      el.addEventListener(event, hide);
    });
  },
  // 所在组件的 VNode 更新时调用，但是可能发生在其子 VNode 更新之前。指令的值可能发生了改变，也可能没有。但是你可以通过比较更新前后的值来忽略不必要的模板更新
  update: (el,binding) => {
    // showEvents.forEach(event =>{
    //   el.removeEventListener(event,show)
    // })
    // hideEvents.forEach((event) => {
    //   el.removeEventListener(event, hide);
    // });
    // showEvents.forEach((event) => {
    //   const { content, x, y} = binding.value
    //   console.log('content')
    //   el.__tooltipContent = content
    //   el.__tooltipX = x
    //   el.__tooltipY = parseFloat(y)
    //   console.log(el)
    //   el.addEventListener(event, show);
    // });
    //
    // hideEvents.forEach((event) => {
    //   el.addEventListener(event, hide);
    // });
    // compareWidth(el) // 可以获取到ajax数据的DOM元素，即真实的宽度

  },
  unbind: (el) => {
    showEvents.forEach(event =>{
      el.removeEventListener(event,show)
    })
    hideEvents.forEach((event) => {
      el.removeEventListener(event, hide);
    });
  }
}

