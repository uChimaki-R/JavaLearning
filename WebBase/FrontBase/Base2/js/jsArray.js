// 数组
var arr = new Array(1, 2, 3);
// 简化写法
var arr = [3, 4, 5];

// 长度可变
arr[10] = 1000;
// 中间的元素是undefined
console.log(arr[8]);

// 类型可变
arr[0] = "Hello";
console.log(arr);

// 遍历数组
// for遍历所有元素
for (let i = 0; i < arr.length; i++) {
    console.log(arr[i]);
}
console.log("---------------------");
// forEach遍历有值的元素
arr.forEach(function(e){
    console.log(e);
})

console.log("---------------------");
// 使用箭头函数简化
// 注意是 =>
arr.forEach((e) => console.log(e));

// 添加元素push
// 删除元素splice 从index下标删除count个元素
