<!-- 定义html代码 -->
<template>
  <div>
    <!-- <element-view></element-view> -->
    <!-- <emp-view></emp-view> -->
    <user-view></user-view>
  </div>
</template>

<!-- 定义js代码，定义vue的数据模型和方法 -->
<script>
// import ElementView from './views/element/ElementView.vue';
import UserView from './views/myTestElement/UserView.vue';
// export后才能在其他js文件中导入
export default {
  components: { /* ElementView */ /* EmpView, */ UserView },
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
