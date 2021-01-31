<template>
  <v-container fluid class="regist my-auto">
    <v-row justify="center" align-content="center">
      <v-col cols="6">
        <v-card>
          <v-card-text>
            <v-form lazy-validation ref="form">
              <v-row no-gutters>
                <v-col cols="6">
                  <text-field
                    v-model="form.kanjiName"
                    label="漢字名"
                    placeholder="漢字名称を入力"
                  />
                </v-col>
                <v-col cols="6">
                  <text-field
                    v-model="form.name"
                    label="名称"
                    placeholder="ひらがな/カタカナを入力"
                  />
                </v-col>
              </v-row>
              <v-row no-gutters>
                <v-col cols="2">
                  <text-field
                    v-model="form.order"
                    label="目"
                    placeholder="目名を入力"
                  />
                </v-col>
                <v-col cols="2">
                  <text-field
                    v-model="form.family"
                    label="科"
                    placeholder="科名を入力"
                  />
                </v-col>
                <v-col cols="2">
                  <text-field
                    v-model="form.genus"
                    label="属"
                    placeholder="属名を入力"
                  />
                </v-col>
              </v-row>
              <v-row no-gutters>
                <v-col cols="1">
                  <v-btn color="success" outlined>登録</v-btn>
                </v-col>
                <v-col cols="1">
                  <v-btn color="primary" outlined @click="clear">クリア</v-btn>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "",
  model: {},
  mixins: [],
  props: {},
  data: () => ({
    form: {},
  }),
  methods: {
    // フォームクリア
    clear() {
      this.$refs.form.reset();
    },
  },
  created() {},
  computed: {},
  watch: {},
  components: {
    TextField: () => import("@/components/common/form/TextField"),
  },
};
</script>

<style scoped>
.regist {
  height: 100% !important;
}
</style>