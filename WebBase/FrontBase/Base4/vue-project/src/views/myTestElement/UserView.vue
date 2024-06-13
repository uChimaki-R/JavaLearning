<template>
  <div>
    <el-button type="primary" @click="sendRequest">update</el-button>
    <br /><br />
    <span>Message: {{ UserData }}</span>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      UserData: [],
    };
  },
  methods: {
    sendRequest() {
      axios
        .get("/getUserData")
        .then((result) => (this.UserData = result.data.data));
    },
  },
  updated() {
    const h = this.$createElement;
    this.$notify({
      title: "获取到响应信息",
      message: h("i", { style: "color: teal" }, "快去查看吧~"),
    });
  },
};
</script>

<style>
</style>