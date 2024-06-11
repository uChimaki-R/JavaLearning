<template>
  <div>
    <el-container style="border: 1px solid #eee">
      <el-header style="font-size: 40px; background-color: rgb(238, 241, 246)">
        豆瓣电影分类排行榜 - 剧情片
      </el-header>
      <el-main>
        <!-- Form表单 -->
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item label="电影名称: ">
            <el-input
              v-model="formInline.name"
              placeholder="电影名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="地区: ">
            <el-select v-model="formInline.regions" placeholder="地区">
              <el-option label="中国" value="China"></el-option>
              <el-option label="美国" value="America"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
        <!-- Table -->
        <el-table :data="tableData" border>
          <el-table-column prop="rank" label="排名"> </el-table-column>
          <el-table-column prop="title" label="名称"> </el-table-column>
          <el-table-column label="图片">
            <template slot-scope="scope">
              <!-- 获取图片有跨域问题，就不搞了 -->
              src = {{ scope.row.cover_url }}
            </template>
          </el-table-column>
          <el-table-column prop="score" label="评分"> </el-table-column>
          <el-table-column prop="release_date" label="上映时间">
          </el-table-column>
          <el-table-column prop="regions" label="地区"> </el-table-column>
        </el-table>

        <br /><br />

        <!-- 分页条 -->
        <el-pagination
          background
          layout="prev, pager, next, sizes, jumper, total"
          :total="1000"
          @size-change="handlerSizeChange"
          @current-change="handlerCurrentChange"
        >
        </el-pagination>
      </el-main>
    </el-container>
  </div>
</template>


<script>
// 导入axios
import axios from "axios";
export default {
  data() {
    return {
      tableData: [],
      formInline: {
        name: "",
        regions: "",
      },
    };
  },
  methods: {
    onSubmit() {
      alert("查询数据:" + JSON.stringify(this.formInline));
    },
    handlerSizeChange() {},
    handlerCurrentChange() {},
  },
  // 钩子函数发送异步请求
  mounted() {
    axios
      // 跨域问题，只能自己去yapi搞个请求地址了
      .get("https://yapi.pro/mock/419253/movie_rank")
      .then((result) => {
        this.tableData = result.data;
      })
      .catch((error) => console.log(error));
  },
};
</script>

<style>
</style>
