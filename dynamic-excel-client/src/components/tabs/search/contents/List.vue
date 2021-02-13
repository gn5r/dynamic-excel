<template>
  <v-container fluid class="pa-0">
    <v-row justify="center" align="center" no-gutters>
      <v-col cols="12">
        <v-btn color="primary" dark outlined @click="output">Excel出力</v-btn>
      </v-col>
      <v-col cols="12" style="overflow-y: auto">
        <v-card>
          <v-card-text class="pa-0">
            <v-data-table
              class="table-striped"
              :headers="headers"
              :items="items"
              :loading.sync="isLoading"
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
import { mapState } from "vuex";
import rest from "@/utils/api/rest"
import { saveAs } from 'file-saver';

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

    async output() {
      const wait = await this.confirm("Excel出力しますか？");
      if(wait) {
        const uri = "excel/list";
        const result = await rest.post(uri, this.items, {responseType: "blob"});
        if(result.status) {
          const blob = new Blob([result.data]);
          saveAs(blob, "一覧.xlsx");
        }
      }
    }
  },
  created() {},
  computed: {
    ...mapState({
      isLoading: (state) => state.app.loading,
    }),
  },
  watch: {},
  components: {},
};
</script>

<style scoped>
</style>