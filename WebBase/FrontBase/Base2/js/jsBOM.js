/* 
    BOM
    - Window: 浏览器窗口对象  <-----
    - Navigator: 浏览器对象
    - Screen: 屏幕对象
    - History: 历史记录对象
    - Location: 地址栏对象
*/

// window.可以省略

// 警告框
alert("Hello BOM!");

// 对话框
if (confirm("确定吗？")) {
    alert("你居然确定了？！");
}
else {
    alert("你居然不确定？！");
}

// 定时器 时间单位ms
// 重复执行
setInterval(() => {
    document.writeln("huh?");
}, 500);

// 只执行一次
setTimeout(() => {
    alert("Only Once!");
}, 1000);

// 获取地址栏内容
document.writeln(location.href);

// 设置地址栏内容，会自动跳转
location.href = "https://www.bilibili.com";
