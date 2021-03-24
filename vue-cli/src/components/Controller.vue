<template>
  <div id="controller">
     {{msg}}
  </div>
</template>

<script>
export default {
  name: "Controller",
  props:{
    msg : String
  }
}
</script>