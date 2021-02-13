<template>
  <div>
    <span v-if="label" class="text-subtitle-2 font-weight-bold">
      {{ label }}
    </span>
    <v-text-field
      v-if="!password"
      :class="{
        disabled: disabled || readonly,
        required: required,
        'field-error': error,
      }"
      :value="value"
      :disabled="disabled"
      :readonly="readonly"
      :autofocus="autofocus"
      :clearable="clearable"
      :dark="dark"
      :placeholder="placeholder"
      :rules="rules"
      ref="text"
      solo
      dense
      @input="$emit('input', $event)"
    >
      <template v-for="(value, name) in $slots" v-slot:[name]>
        <slot :name="name" />
      </template>
    </v-text-field>
    <v-text-field
      v-else
      :class="{
        disabled: disabled || readonly,
        required: required,
        'field-error': error,
      }"
      :value="value"
      :placeholder="placeholder"
      :clearable="clearable"
      :disabled="disabled"
      :readonly="readonly"
      :append-icon="visible ? 'mdi-eye' : 'mdi-eye-off'"
      :type="visible ? 'text' : 'password'"
      :rules="rules"
      solo
      dense
      @click:append="visible = !visible"
      @input="$emit('input', $event)"
    >
      <template v-for="(value, name) in $slots" v-slot:[name]>
        <slot :name="name" />
      </template>
    </v-text-field>
  </div>
</template>

<script>
import validate from "@/utils/form/validate";

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
    required: Boolean,
    error: Boolean,
    password: Boolean,
  },
  data: () => ({
    visivle: false,
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
