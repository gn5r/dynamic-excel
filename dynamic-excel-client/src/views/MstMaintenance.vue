<template>
  <v-container fluid class="dynamic_excel_main">
    <v-row justify="start" align="center" align-content="center" no-gutters>
      <v-col>
        <!-- タブメニュー -->
        <v-card>
          <header-bar title="マスタメンテナンス" dense>
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
                <v-tab>ファイル種別</v-tab>
              </v-tabs>
            </template>
          </header-bar>

          <!-- タブコンテンツ -->
          <v-tabs-items v-model="tab">
            <v-tab-item>
              <v-card>
                <file-type-mst class="v__tab__contents__frame" />
              </v-card>
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
import mixin from "@/utils/script/mixin";
import { mapActions, mapState } from "vuex";

export default {
  name: "",
  mixins: [mixin],
  props: {},
  data: () => ({
      tab: null
  }),
  methods: {},
  created() {},
  computed: {},
  watch: {},
  components: {
    HeaderBar: () => import("@/components/common/HeaderBar"),
    FileTypeMst: () =>
      import("@/components/tabs/mstMainte/contents/FileTypeMst"),
  },
};
</script>

<style scoped></style>
