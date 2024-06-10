<template>
  <div>
    <el-container style="height: 500px; border: 1px solid #eee">
      <el-header style="font-size: 40px; background-color: rgb(238, 241, 246)">
        豆瓣电影分类排行榜 - 剧情片
      </el-header>
      <el-main>
        <el-table :data="tableData">
          <el-table-column prop="rank" label="排名"> </el-table-column>
          <el-table-column prop="title" label="名称"> </el-table-column>
          <el-table-column prop="cover" label="图片"> </el-table-column>
          <el-table-column prop="score" label="评分"> </el-table-column>
          <el-table-column prop="release_date" label="上映时间">
          </el-table-column>
          <el-table-column prop="regions" label="地区"> </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>


<script>
export default {
  data() {
    return {
      tableData: [],
    };
  },
};
</script>

<style>
</style>
