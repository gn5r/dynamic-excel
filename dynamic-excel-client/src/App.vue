<template>
  <v-app>
    <header-bar text="dynamic-excel"></header-bar>
    <v-main>
      <router-view />
    </v-main>
    <footer-bar :copyright="copyright"></footer-bar>
  </v-app>
</template>

<script>
export default {
  name: "App",
  components: {
    HeaderBar: () => import ("@/components/common/HeaderBar"),
    FooterBar: () => import("@/components/common/FooterBar")
  },
  computed: {
    copyright() {
      const date = new Date();
      return `Copyright © ${date.getFullYear()} gn5r All Rights Reserved.`
    }
  }
};
</script>

<style>
html, body {
  overflow: hidden;
}
</style>
