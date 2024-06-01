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

var jsonStr = '{"name": "Tom", "age": 18, "skill": ["Python", "Java", "C++"]}';

// 解析json数据为json对象
var jsonObj = JSON.parse(jsonStr);
document.writeln(jsonObj.name);

// json对象转json格式字符串
var toJson = JSON.stringify(jsonObj);
document.writeln(toJson);
