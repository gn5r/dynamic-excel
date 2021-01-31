<template>
  <div>
    <span v-if="label" class="text-subtitle-2 font-weight-light">
      {{ label }}
    </span>
    <v-text-field
      :class="{ disabled: disabled || readonly }"
      :value="value"
      :disabled="disabled"
      :readonly="readonly"
      :autofocus="autofocus"
      :clearable="clearable"
      :dark="dark"
      :placeholder="placeholder"
      solo
      dense
      @input="$emit('input', $event)"
    >
      <template v-for="(value, name) in $slots" v-slot:[name]>
        <slot :name="name" />
      </template>
    </v-text-field>
  </div>
</template>

<script>
export default {
  name: "text-field",
  description: "テキストフィールド",
  model: {
    prop: "value",
    event: "input",
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
