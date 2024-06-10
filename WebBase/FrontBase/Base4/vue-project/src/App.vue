<!-- 定义html代码 -->
<template>
  <div>
    <h1>{{ message }}</h1>
  </div>
</template>

<!-- 定义js代码，定义vue的数据模型和方法 -->
<script>
// export后才能在其他js文件中导入
export default {
  // 数据模型定义的不是对象，而是返回对象的函数
  data() {
    return {
      message: "Hello Vue",
    };
  },
  // 定义方法
  methods: {},
};
</script>

<!-- 定义css样式 -->
<style>
</style>
