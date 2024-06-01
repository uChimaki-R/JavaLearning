// 三种输出方式
// 1、弹出警告框
window.alert('Hei! Man! What can I say!');
// 2、写入html页面
document.write("Wrote in document.");
// 3、在浏览器控制台输出
console.log("A log to console.");

// 行尾的分号可以不写，但是最好写上

var a = 100;
a = "js代码是弱数据类型的代码, 同个变量可以接收不同类型的变量";

// var的作用域大，产生的变量是全局变量
{
    var x = 50;
}
alert(x); // 可以访问

// var定义的变量可以重复声明
var x = 12;
var x = "hi";

// let定义的变量为局部变量且不可重复定义
{
    // let x = 10;
    // let x = "hi";
}

// const声明常量
const pi = 3.1416
