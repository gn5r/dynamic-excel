<template>
  <v-app-bar app dark :color="color" :dense="dense">
    <v-toolbar-title>{{ title }}</v-toolbar-title>
    <v-spacer v-if="spacer" />
    <slot name="app-bar-items"></slot>
    <template v-for="(value, name) in $slots" v-slot:[name]>
      <slot :name="name" />
    </template>
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
    title: {
      type: String,
      default: null,
    },
    spacer: {
      type: Boolean,
      default: true
    },
    dense: {
      type: Boolean,
      default: false
    }
  },
  data: () => ({}),
  methods: {},
  created() {},
  computed: {},
  watch: {},
  components: {},
};
</script>

<style scoped>
</style>
