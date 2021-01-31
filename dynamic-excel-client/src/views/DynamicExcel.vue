<template>
  <v-container fluid class="dynamic_excel_main">
    <v-row justify="start" align="center" no-gutters>
      <v-col>
        <v-card>
          <!-- タブメニュー -->
          <v-tabs v-model="tab" center-active>
            <v-tab>一覧検索</v-tab>
            <v-tab>データ登録</v-tab>
          </v-tabs>

          <!-- タブコンテンツ -->
          <v-tabs-items v-model="tab">
            <v-tab-item>
              <v-card>
                <search />
              </v-card>
            </v-tab-item>
            <v-tab-item>
              <v-card>
                <regist />
              </v-card>
            </v-tab-item>
          </v-tabs-items>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "",
  mixins: [],
  props: {},
  data: () => ({
    tab: null,
  }),
  methods: {
    ...mapActions("master", ["getOrderList", "getFamilyList", "getGenusList"]),
  },
  created() {},
  mounted() {
    this.getOrderList();
    this.getFamilyList();
    this.getGenusList();
  },
  computed: {},
  watch: {},
  components: {
    Search: () => import("@/components/tabs/search/Search"),
    Regist: () => import("@/components/tabs/regist/Regist"),
  },
};
</script>

<style scoped>
/* メインコンテンツ下の高さを100%にする */
.dynamic_excel_main {
  height: 100% !important;
  overflow-y: scroll;
}

.dynamic_excel_main >>> .row,
.col,
.v-card {
  height: inherit;
}

.v-card >>> .v-tabs-items {
  height: calc(100% - 48px);
}

.v-tabs-items >>> .v-window__container, .v-window-item {
  height: 100%;
}
</style>
