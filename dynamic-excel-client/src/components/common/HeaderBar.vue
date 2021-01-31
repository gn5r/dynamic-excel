<template>
  <v-app-bar app dark :color="color">
    <v-toolbar-title>{{ text }}</v-toolbar-title>
    <v-spacer />
    <slot name="app-bar-items"></slot>
  </v-app-bar>
</template>

<script>
export default {
  name: "header-bar",
  model: {},
  mixins: [],
  props: {
    color: {
      type: String,
      default: undefined,
    },
    text: {
      type: String,
      default: null,
    },
  },
  data: () => ({}),
  methods: {},
  created() {},
  computed: {},
  watch: {},
  components: {},
};
</script>

<style scoped></style>
