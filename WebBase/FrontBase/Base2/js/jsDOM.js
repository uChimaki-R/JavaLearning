// 获取元素
// 通过id class name tagName等
var title = document.getElementsByTagName("title")[0];

setTimeout(() => {
    // 修改元素
    title.innerHTML = "Ha!You changed!";
}, 2000);
