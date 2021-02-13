<template>
  <v-container fluid class="dynamic_excel_main">
    <v-row justify="start" align="center" no-gutters>
      <v-col>
        <!-- タブメニュー -->
        <v-card>
          <header-bar title="dynamic-excel" dense>
            <template v-slot:extension>
              <v-tabs v-model="tab" center-active dark background-color="blue">
                <v-tabs-slider color="yellow"></v-tabs-slider>

                <v-tab>一覧検索</v-tab>
                <v-tab>データ登録</v-tab>
                <v-tab>Excel取込</v-tab>
              </v-tabs>
            </template>
          </header-bar>

          <!-- タブコンテンツ -->
          <v-tabs-items v-model="tab">
            <v-tab-item>
              <v-card>
                <search class="search__frame" />
              </v-card>
            </v-tab-item>
            <v-tab-item>
              <v-card>
                <regist
                  v-if="tab === 1"
                  class="regist__frame"
                  @regist="tab = 0"
                />
              </v-card>
            </v-tab-item>
            <v-tab-item>
              <excel-import />
            </v-tab-item>
          </v-tabs-items>
        </v-card>
      </v-col>
    </v-row>

    <!-- Loading -->
    <loading :active.sync="isLoading" />
  </v-container>
</template>

<script>
import { mapActions, mapState } from "vuex";

export default {
  name: "",
  mixins: [],
  props: {},
  data: () => ({
    tab: null,
  }),
  methods: {
    ...mapActions("master", ["getOrderList", "getFamilyList", "getGenusList"]),
    ...mapActions("app", ["setLoading"]),
  },
  created() {},
  mounted() {
    this.setLoading(false);
    this.getOrderList();
    this.getFamilyList();
    this.getGenusList();
  },
  computed: {
    ...mapState({
      isLoading: (state) => state.app.loading,
    }),
  },
  watch: {},
  components: {
    HeaderBar: () => import("@/components/common/HeaderBar"),
    Search: () => import("@/components/tabs/search/Search"),
    Regist: () => import("@/components/tabs/regist/Regist"),
    ExcelImport: () => import("@/components/tabs/excel/Import"),
  },
};
</script>

<style scoped>
/* メインコンテンツ下の高さを100%にする */
.dynamic_excel_main {
  height: 100% !important;
  padding: 0px !important;
}

.dynamic_excel_main >>> .row,
.col,
.v-card {
  height: inherit;
}

.v-tabs-items {
  height: calc(100% - 48px);
}

.v-tabs-items >>> .v-window__container,
.v-window-item,
.search__frame,
.regist__frame {
  height: 100%;
}
</style>
