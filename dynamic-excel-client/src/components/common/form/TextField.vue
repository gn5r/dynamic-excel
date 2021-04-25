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
      :append-icon="appendIcon"
      :counter="max"
      :hide-details="hideDetails"
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
      hide-details
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
    appendIcon: String,
    max: String,
    hideDetails: Boolean,
    validateType: {
      type: [String, Array],
      default: () => undefined,
    },
  },
  data: () => ({
    visivle: false,
    rules: [],
  }),
  methods: {
    setValidate() {
      if (this.required) {
        if (validate.availableValidate("required")) {
          this.rules.push(validate.getValidate("required"));
        }
      }

      if (this.validateType !== undefined) {
        if (Array.isArray(this.validateType)) {
          Array.from(this.validateType).forEach((i, v) => {
            if (validate.availableValidate(v)) {
              this.rules.push(validate.getValidate(v));
            }
          });
        } else {
          if (validate.availableValidate(this.validateType)) {
            this.rules.push(validate.getValidate(this.validateType));
          }
        }
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
