<template>
  <v-container class="ma-0 pa-0" fluid style="height: 100%!important">
    <v-row justify="start" align="center" align-content="center">
      <v-col cols="12" style="height:100%!important">
        <v-card height="100%">
          <v-card-title class="px-0 pt-0">
            <v-toolbar dense>
              <v-toolbar-title>Excelテンプレート編集</v-toolbar-title>
            </v-toolbar>
          </v-card-title>
          <v-card-text>
            <v-container fluid class="ma-0 pa-0">
              <v-row
                justify="start"
                align="center"
                align-content="center"
                no-gutters
              >
                <v-col cols="2">
                  <select-box
                    v-model="form.templateId"
                    label="テンプレート"
                    :items="templates"
                    placeholder="テンプレートを選択"
                    clearable
                    @change="selectTemplate"
                  />
                </v-col>

                <v-col cols="2">
                  <v-btn color="primary" outlined @click="download"
                    >ダウンロード</v-btn
                  >
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { get } from "@gn5r/vue-axios";
import { saveAs } from "file-saver";
import { mapState } from "vuex";

const xlsx = require("xlsx");

export default {
  name: "template-edit",
  mixins: [],
  props: {},
  data: () => ({
    form: {},
    fileData: null,
  }),
  methods: {
    async selectTemplate(id) {
      const template = this.templates.find((item) => item.id === id);

      if (template !== undefined) {
        const uri = `excel/get/templateFile/${template.id}`;
        const res = await get(uri, { responseType: "blob" });

        if (res.status) {
          const blob = new Blob([res.data], {
            type:
              "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          });
          this.fileData = { file: blob, name: template.value };
          const wb = xlsx.read(await blob.arrayBuffer(), { type: "array" });
          console.debug(wb);
          // 先頭のシート名を取得する
          const sheetName = wb.SheetNames[0];
          // シート名からワークシートを取得する
          const sheet = wb.Sheets[sheetName];
          // 取得したシートをJSONオブジェクトへ変換
          const json = xlsx.utils.sheet_to_json(sheet);
          console.debug("Excelシート:", json);
        }
      }
    },
    download() {
      if (this.fileData !== null) {
        saveAs(this.fileData.file, this.fileData.name);
      }
    },
  },
  created() {},
  computed: {
    ...mapState({
      templates: (state) => state.master.fruitsTemplates,
    }),
  },
  watch: {},
  components: {
    SelectBox: () => import("@/components/common/form/SelectBox"),
  },
};
</script>

<style scoped></style>
