<template>
   <div id="map">
      {{msg}}
   </div>
</template>

<script>
import echarts from 'echarts'

export default {

  name: "WorldMap",
  props: {
     msg: String
  }
}

</script>

