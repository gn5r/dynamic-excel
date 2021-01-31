<template>
  <v-footer app dark :color="color">
    <v-spacer />
    <div class="mr-2">{{ copyright }}</div>
    <slot name="footer-bar-items" />
  </v-footer>
</template>

<script>
export default {
  name: "footer-bar",
  model: {},
  mixins: [],
  props: {
    color: {
      type: String,
      default: undefined,
    },
    dark: {
      type: Boolean,
      default: true,
    },
    copyright: {
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
