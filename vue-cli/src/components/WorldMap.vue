<template>
   <div id="map"></div>
</template>

<script>
import * as echarts from 'echarts'
import '/public/js/china.js'
import axios from 'axios'

export default {
  name: "WorldMap",
  methods: {
    getWorld() {
      axios.get('/js/json/world.json').then(function (worldJson) {
        echarts.registerMap('world', worldJson.data);
        let myChart = echarts.init(document.getElementById("map"));
        // 监听屏幕变化自动缩放图表
        window.addEventListener('resize', function () {
          myChart.resize()
        })
        myChart.setOption({
              title: [{
                text: '世界地图'
              }],
              tooltip:{
                trigger: 'item',
                formatter: '{b}<br/>{c} (人)'
              },
              //设置echarts图随滚轮缩放
              dataZoom: [
                {
                  type: "inside"
                }
              ],
              roam: true,
              series: [{
                type: 'map',
                map: 'world',
                data: [
                  {name: 'China',value:23123123},
                  {name: 'Russia' ,value: 23234}
                ]
              }],
              visualMap: {
                type: 'continuous',
                min: 0,
                max: 1000000,
                text: ['High','Low'],
                realtime: true,
                calculable: true,
                inRange:{
                  color: ['lightskyblue','yellow','orangered']
                }
              }
          })
      })
    },
  },
  mounted() {
    this.getWorld();
  }
}

</script>

