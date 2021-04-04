<template>
  <v-dialog
    :value.sync="dialog"
    max-width="1200"
    persistent
    @input="close"
  >
    <v-card>
      <v-card-title class="pa-0">
        <v-toolbar dark color="primary" dense>
          <v-toolbar-title>ファイル種別マスタ登録編集</v-toolbar-title>

          <v-spacer />

          <v-toolbar-items>
            <v-tooltip bottom>
              <template v-slot:activator="{ on }">
                <v-btn v-on="on" icon @click="close">
                  <v-icon>mdi-close</v-icon>
                </v-btn>
              </template>
              <span>ダイアログを閉じる</span>
            </v-tooltip>
          </v-toolbar-items>
        </v-toolbar>
      </v-card-title>

      <v-card-text>
        <v-container fluid>
          <v-form lazy-validation ref="form">
            <v-row justify="start" align="center" no-gutters>
              <v-col cols="4">
                <text-field
                  v-model="form.typeName"
                  label="ファイル種別名"
                  max="128"
                  required
                  clearable
                  placeholder="ファイル種別名を入力"
                />
              </v-col>
            </v-row>
          </v-form>
        </v-container>
      </v-card-text>

      <v-divider />

      <v-card-actions>
        <v-spacer />
        <v-btn color="success" @click="doSave">{{ saveText }}</v-btn>
      </v-card-actions>

      <!-- confirm -->
      <v-confirm
        :dialog.sync="confirmObj.dialog"
        :title="confirmObj.title"
        :title-icon="confirmObj.titleIcon"
        :title-color="confirmObj.titleColor"
        :message="confirmObj.message"
        :buttons="confirmObj.buttons"
      />
    </v-card>
  </v-dialog>
</template>

<script>
const rest = require("@gn5r/vue-axios");
import { mapActions, mapState } from "vuex";

export default {
  name: "",
  mixins: [],
  props: {
    dialog: Boolean,
    id: Number,
  },
  data: () => ({
    form: {},
  }),
  methods: {
    async getDetail() {
      const uri = `/fileTypeMst/get/${this.id}`;
      const res = await rest
        .get(uri)
        .then((res) => {
          this.setLoading(false);
          return res;
        })
        .catch((err) => {
          this.setLoading(false);
          return err;
        });

      if (res.status) {
        this.form = Object.assign({}, res.data);
      } else {
        console.error("ファイル種別マスタ取得エラー:", res);
        await this.err("ファイル種別マスタの取得に失敗しました");
      }
    },

    async doSave() {
      if (!this.$refs.form.validate()) return;

      const wait = await this.confirm(`${this.saveText}しますか？`);
      if (wait) {
        if (this.id === null) {
          this.regist();
        } else {
          this.update();
        }
      }
    },
    async regist() {
      const uri = "/fileTypeMst/regist";
      this.setLoading(true);
      const res = await rest
        .post(uri, this.form)
        .then((res) => {
          this.setLoading(false);
          return res;
        })
        .catch((err) => {
          this.setLoading(false);
          return err;
        });

      if (res.status) {
        await this.complete(`${this.saveText}しました`);
        this.$emit("done");
        this.close();
      } else {
        console.error("登録エラー:", res);
      }
    },

    async update() {
      const uri = `/fileTypeMst/update/${this.id}`;
      this.setLoading(true);
      const res = await rest
        .put(uri, this.form)
        .then((res) => {
          this.setLoading(false);
          return res;
        })
        .catch((err) => {
          this.setLoading(false);
          return err;
        });

      if (res.status) {
        await this.complete(`${this.saveText}しました`);
        this.$emit("done");
        this.close();
      } else {
        console.error("更新エラー:", res);
      }
    },

    close() {
        this.$emit("update:dialog", false);
    },

    ...mapActions("app", ["setLoading"]),
  },
  created() {
      if(this.id !== null) {
        this.getDetail();
      }
  },
  computed: {
    saveText() {
      return this.id === null ? "保存" : "更新";
    },
  },
  watch: {},
  components: {
    TextField: () => import("@/components/common/form/TextField"),
  },
};
</script>

<style scoped></style>
