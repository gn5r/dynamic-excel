<template>
  <v-container fluid>
    <v-form lazy-validation ref="form">
      <v-row justify="start" align="center">
        <v-col cols="2">
          <select-box
            v-model="form.templateId"
            label="テンプレート"
            :items="templates"
            placeholder="テンプレートを選択"
            clearable
            required
          />
        </v-col>
        <v-col cols="2">
          <v-btn color="primary" dark outlined @click="output">Excel出力</v-btn>
        </v-col>
      </v-row>
    </v-form>

    <v-row justify="start" align="center" no-gutters>
      <v-col cols="12" style="overflow-y: auto">
        <v-card>
          <v-card-text class="pa-0">
            <v-data-table
              class="table-striped"
              :headers="headers"
              :items="items"
              loading-text="データ取得中です..."
              hide-default-footer
              no-data-text="データがありません"
              :page.sync="page"
              :items-per-page="10"
              @page-count="pageCount = $event"
              fixed-header
              style="max-height: 300px"
            >
              <template v-slot:[`item.actions`]="{ item }">
                <v-icon color="error" @click="del(item.id)">mdi-delete</v-icon>
              </template>
            </v-data-table>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="12">
        <div class="text-center">
          <v-pagination v-model="page" :length="pageCount" />
        </div>
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
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
import rest from "@/utils/api/axiosBase";
import { get } from "@/utils/api/rest";
import { saveAs } from "file-saver";

export default {
  name: "list",
  model: {
    prop: "items",
    event: "edit",
  },
  mixins: [],
  props: {
    items: {
      type: Array,
      default: () => [],
    },
  },
  data: () => ({
    headers: [
      {
        text: "ID",
        align: "center",
        sortable: true,
        value: "id",
      },
      {
        text: "漢名",
        align: "center",
        sortable: false,
        value: "kanjiName",
      },
      {
        text: "かな/カナ名",
        align: "center",
        sortable: false,
        value: "name",
      },
      {
        text: "目",
        align: "center",
        sortable: true,
        value: "order",
      },
      {
        text: "科",
        align: "center",
        sortable: true,
        value: "family",
      },
      {
        text: "属",
        align: "center",
        sortable: true,
        value: "genus",
      },
      {
        text: "操作",
        align: "center",
        sortable: false,
        value: "actions",
      },
    ],
    page: 0,
    pageCount: 0,
    form: {
      templateId: null,
      items: [],
    },
  }),
  methods: {
    async del(id) {
      const wait = await this.confirm("削除しますか？");
      if (wait) {
        console.debug("疑似削除処理");
        let result = Array.from(this.items);
        const idx = result.findIndex((item) => item.id === id);
        if (idx !== -1) {
          result.splice(idx, 1);
          this.$emit("edit", result);
        }
      }
    },

    /**
     * 果物一覧の出力
     */
    async output() {
      if (!this.$refs.form.validate()) {
        return;
      }

      const wait = await this.confirm("Excel出力しますか？");
      if (wait) {
        const uri = "excel/list";
        this.form.items = this.items;
        
        this.setLoading(true);
        const result = await rest.post(uri, this.form, {
          responseType: "blob",
        });

        this.setLoading(false);
        if (result.status) {
          const blob = new Blob([result.data]);
          console.debug(blob)
          saveAs(blob, "一覧.xlsx");
        }
      }
    },

    ...mapActions("app", ["setLoading"]),
  },
  created() {},
  computed: {
    ...mapState({
      isLoading: (state) => state.app.loading,
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
