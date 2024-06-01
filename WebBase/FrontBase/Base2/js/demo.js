// 三种输出方式
// 1、弹出警告框
window.alert('Hei! Man! What can I say!');
// 2、写入html页面
document.writeln("Wrote in document.");
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

// js的原始数据类型
// number
document.writeln(typeof 3);
document.writeln(typeof 3.14);
// string
document.writeln(typeof "A");
document.writeln(typeof 'hi');
// boolean
document.writeln(typeof true);
document.writeln(typeof false);
// object
document.writeln(typeof null);
// undefined
var aaaa;
document.writeln(typeof aaaa);

// 比较运算符基本一致
// 特殊的 == 和 ===
// == 比较前会进行类型转换 而 === 不会
var a = 10;
document.writeln(a == "10");  // true
document.writeln(a === "10");  // false
document.writeln(a === 10);  // true


// 类型转换

// 字符串转数字
document.writeln(parseInt("101"));
// 从头开始转换直到不是数字
document.writeln(parseInt("101hi"));  // 101
document.writeln(parseInt("a110"));  // NaN

// 数字转boolean
// 除了0和NaN 其他都是true

// 字符串转boolean
// 除了空串 其他都是true

// null和undefined都是false

// 流程控制语句基本一致

// 函数 形参和返回值类型都不需要声明
function functionName(a, b){
    return a + b;
}

var result = functionName(12, 13);
document.writeln(result);

// 可以使用变量接收
var testFunc = function functionName(a, b){
    return a + b;
}
// 可以传递超过参数列表个数的形参，多余的不会被接收
document.writeln(testFunc(10, 20, 10, 10));
