<template>
  <div id="app">
    <WorldMap msg="This is for map"/>
    <Controller msg="This is for controller"/>
    <Footer/>

  </div>
</template>

<script>
import WorldMap from "@/components/WorldMap";
import Footer from "@/components/Footer";
import Controller from "@/components/Controller";

export default {
  name: 'App',
  components: {
    Footer,
    WorldMap,
    Controller
  }
} 
</script>

