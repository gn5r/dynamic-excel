<template>
  <div>
    <span v-if="label" class="text-subtile-2 font-weight-light">{{
      label
    }}</span>
    <v-select
      :value="value"
      :disabled="disabled"
      :readonly="readonly"
      :autofocus="autofocus"
      :clearable="clearable"
      :dark="dark"
      :placeholder="placeholder"
      :items="items"
      :item-text="itemText"
      :item-value="itemValue"
      solo
      dense
      @input="$emit('change', $event)"
    ></v-select>
  </div>
</template>

<script>
export default {
  name: "select-box",
  model: {
    prop: "value",
    event: "change",
  },
  mixins: [],
  props: {
    value: undefined,
    readonly: Boolean,
    disabled: Boolean,
    autofocus: Boolean,
    clearable: Boolean,
    dark: Boolean,
    placeholder: String,
    label: String,
    items: Array,
    itemText: {
      type: String,
      default: "value",
      description: "選択項目に表示されるテキスト",
    },
    itemValue: {
      type: String,
      default: "id",
      description: "選択された時に返却する値",
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
