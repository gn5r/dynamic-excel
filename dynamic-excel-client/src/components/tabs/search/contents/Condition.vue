<template>
  <v-container fluid>
    <v-row justify="start" align="center" no-gutters>
      <v-col>
        <v-form lazy-validation ref="form">
          <v-row>
            <v-col cols="12" sm="6" lg="2">
              <text-field v-model="form.id" label="ID" placeholder="IDを入力" />
            </v-col>
            <v-col cols="12" sm="6" lg="3">
              <text-field
                v-model="form.kanjiName"
                label="漢字名"
                placeholder="漢字名称を入力"
              />
            </v-col>
            <v-col cols="12" sm="6" lg="3">
              <text-field
                v-model="form.name"
                label="名称"
                placeholder="ひらがな/カタカナを入力"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12" sm="4" lg="2">
              <select-box
                v-model="form.order"
                label="目名"
                :items="master.orderList"
                item-value="value"
                placeholder="目名を選択"
                clearable
              />
            </v-col>
            <v-col cols="12" sm="6" lg="2">
              <select-box
                v-model="form.family"
                label="科名"
                :items="master.familyList"
                item-value="value"
                placeholder="科名を選択"
                clearable
              />
            </v-col>
            <v-col cols="12" sm="6" lg="2">
              <select-box
                v-model="form.genus"
                label="属名"
                :items="master.genusList"
                item-value="value"
                placeholder="属名を選択"
                clearable
              />
            </v-col>
          </v-row>
          <v-row justify="start" no-gutters>
            <v-col cols="3" sm="1" lg="1">
              <v-btn color="primary" outlined @click="search">検索</v-btn>
            </v-col>
            <v-col cols="3" sm="1" lg="1">
              <v-btn color="primary" outlined @click="clear">クリア</v-btn>
            </v-col>
          </v-row>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
const rest = require("@/utils/api/rest");

export default {
  name: "condition",
  model: {
    prop: "result",
    event: "search",
  },
  mixins: [],
  props: {
    result: {
      type: Array,
      default: () => [],
    },
  },
  data: () => ({
    form: {},
  }),
  methods: {
    // フォームクリア
    clear() {
      this.$refs.form.reset();
    },
    // 検索
    async search() {
      const uri = "/search";
      this.$emit("search", []);
      this.setLoading(true);
      const res = await rest
        .post(uri, this.form)
        .then((res) => res)
        .catch((err) => err)
        .finally(() => {
          this.setLoading(false);
        });

      if (res.status) {
        console.debug(res.data.result);
        this.$emit("search", res.data.result);
      } else {
        this.$emit("search", []);
      }
    },

    ...mapActions("app", ["setLoading"]),
  },
  created() {},
  computed: {
    ...mapState({
      master: (state) => state.master,
    }),
  },
  watch: {},
  components: {
    TextField: () => import("@/components/common/form/TextField"),
    SelectBox: () => import("@/components/common/form/SelectBox"),
  },
};
</script>

<style scoped></style>
