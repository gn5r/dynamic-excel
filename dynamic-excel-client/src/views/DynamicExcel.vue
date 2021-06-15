<template>
  <v-container fluid class="dynamic_excel_main">
    <v-row justify="start" align="center" no-gutters>
      <v-col>
        <!-- タブメニュー -->
        <v-card>
          <header-bar title="dynamic-excel" dense>
            <template v-slot:app-bar-items>
              <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <v-btn icon v-on="on">
                    <v-icon @click="to('DynamicExcelMain')">fas fa-home</v-icon>
                  </v-btn>
                </template>
                <span>ホーム</span>
              </v-tooltip>

              <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <v-btn icon v-on="on">
                    <v-icon @click="to('SampleTop')">fas fa-table</v-icon>
                  </v-btn>
                </template>
                <span>サンプルページ</span>
              </v-tooltip>

              <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <v-btn icon v-on="on">
                    <v-icon @click="to('MstMntTop')">fas fa-edit</v-icon>
                  </v-btn>
                </template>
                <span>マスタメンテナンス</span>
              </v-tooltip>
            </template>

            <template v-slot:extension>
              <v-tabs v-model="tab" center-active dark background-color="blue">
                <v-tabs-slider color="yellow"></v-tabs-slider>

                <v-tab>一覧検索</v-tab>
                <v-tab>データ登録</v-tab>
                <v-tab>Excel取込</v-tab>
                <v-tab v-if="false">Excelテンプレート編集</v-tab>
                <v-tab>テンプレートアップロード</v-tab>
              </v-tabs>
            </template>
          </header-bar>

          <!-- タブコンテンツ -->
          <v-tabs-items v-model="tab" ref="tabs">
            <v-tab-item>
              <search class="v__tab__contents__frame" />
            </v-tab-item>
            <v-tab-item>
              <regist v-if="tab === 1" @regist="tab = 0" />
            </v-tab-item>
            <v-tab-item>
              <excel-import />
            </v-tab-item>
            <v-tab-item v-if="false">
              <template-edit />
            </v-tab-item>
            <v-tab-item>
              <template-upload />
            </v-tab-item>
          </v-tabs-items>

          <v-btn fixed fab icon width="24" style="top: 300px;left: 5px">
            <v-icon @click="prevTab">fas fa-chevron-left</v-icon>
          </v-btn>

          <v-btn fixed fab icon width="24" style="top: 300px;right: 5px">
            <v-icon @click="nextTab">fas fa-chevron-right</v-icon>
          </v-btn>
        </v-card>
      </v-col>
    </v-row>

    <!-- confirm -->
    <v-confirm
      :dialog.sync="confirmObj.dialog"
      :title="confirmObj.title"
      :title-icon="confirmObj.titleIcon"
      :title-color="confirmObj.titleColor"
      :message="confirmObj.message"
      :buttons="confirmObj.buttons"
    />

    <!-- Loading -->
    <loading :active.sync="isLoading" />
  </v-container>
</template>

<script>
import mixin from "@/utils/script/mixin";
import { mapActions, mapState } from "vuex";

export default {
  name: "",
  mixins: [mixin],
  props: {},
  data: () => ({
    tab: null,
  }),
  methods: {
    prevTab() {
      if(this.$refs.tabs.hasPrev) this.$refs.tabs.prev()
    },
    nextTab() {
      if(this.$refs.tabs.hasNext) this.$refs.tabs.next()
    },
    ...mapActions("master", [
      "getOrderList",
      "getFamilyList",
      "getGenusList",
      "getFruitsListTemplates",
    ]),
    ...mapActions("app", ["setOfflineMode"]),
  },
  created() {},
  mounted() {
    this.setLoading(false);
    this.getOrderList();
    this.getFamilyList();
    this.getGenusList();
    this.getFruitsListTemplates();
  },
  computed: {
    ...mapState({
      offlineMode: (state) => state.app.offlineMode,
    }),
  },
  watch: {
    // async offlineMode(mode) {
    //   if (mode) {
    //     const wait = await this.confirm(
    //       "ネットワークエラーが発生しました。\nオフラインモードを有効にしますか？"
    //     );
    //     this.setOfflineMode(wait);
    //   }
    // }
  },
  components: {
    HeaderBar: () => import("@/components/common/HeaderBar"),
    Search: () => import("@/components/tabs/search/Search"),
    Regist: () => import("@/components/tabs/regist/Regist"),
    ExcelImport: () => import("@/components/tabs/excel/Import"),
    TemplateEdit: () => import("@/components/tabs/excel/TemplateEdit"),
    TemplateUpload: () => import("@/components/tabs/excel/TemplateUpload"),
  },
};
</script>

<style scoped></style>
