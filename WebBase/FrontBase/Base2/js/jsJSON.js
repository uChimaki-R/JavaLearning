// 自定义对象
var user = {
    name: "Tom",
    age: 10,
    gender: "男",
    // eat: function(){
    //     document.writeln("干饭!!!");
    // }
    // 简化函数
    eat(){
        document.writeln("干饭!!!!!")
    }
}

document.writeln(user.name);
user.eat();
