<template>
  <div>
    <span v-if="label" class="text-subtile-2 font-weight-light">{{
      label
    }}</span>
    <v-select
      :class="{ required: required }"
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
      :rules="rules"
      no-data-text="データがありません"
      solo
      dense
      @input="$emit('change', $event)"
    ></v-select>
  </div>
</template>

<script>
import validate from "@/utils/form/validate";

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
    required: {
      type: Boolean,
      default: false,
    },
  },
  data: () => ({
    rules: [],
  }),
  methods: {
    setValidate() {
      if (this.required) {
        this.rules.push(validate.getValidate("required"));
      }
    },
  },
  created() {
    this.setValidate();
  },
  computed: {},
  watch: {
    required(v) {
      const idx = this.rules.findIndex((rule) => rule.name === "required");
      if (v) {
        if (idx === -1) this.rules.push(validate.getValidate("required"));
      } else {
        if (idx !== -1) this.rules.splice(idx, 1);
      }
    },
  },
  components: {},
};
</script>

<style scoped></style>
