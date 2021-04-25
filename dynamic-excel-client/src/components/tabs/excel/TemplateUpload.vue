<template>
  <v-container fluid>
    <v-row justify="start" align="center" align-content="center">
      <v-col cols="12">
        <v-card>
          <v-card-text>
            <v-form ref="form" lazy-validation>
              <v-container fluid>
                <v-row
                  justify="start"
                  align="center"
                  align-content="center"
                  no-gutters
                >
                  <v-col cols="12" sm="6" lg="6">
                    <text-field
                      v-model="form.fileName"
                      label="ファイル名"
                      placeholder="ファイル名を入力"
                      clearable
                    />
                  </v-col>
                  <v-col cols="12" sm="6" lg="6">
                    <text-field
                      v-model="form.path"
                      label="ファイルパス"
                      placeholder="ファイルパスを入力"
                      clearable
                    />
                  </v-col>
                  <v-col cols="12" sm="6" lg="3">
                    <select-box
                      v-model="form.fileTypeId"
                      :items="fileTypeList"
                      label="ファイル種別"
                      placeholder="ファイル種別を選択"
                      clearable
                    />
                  </v-col>
                </v-row>
                <v-row justify="start" no-gutters>
                  <v-col cols="3" sm="3" lg="3">
                    <v-item-group>
                      <v-btn color="primary" outlined @click="search"
                        >検索</v-btn
                      >
                      <v-btn color="primary" outlined @click="clear"
                        >クリア</v-btn
                      >
                      <v-btn color="success" outlined @click="dialog = true"
                        >新規作成</v-btn
                      >
                    </v-item-group>
                  </v-col>
                </v-row>
              </v-container>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" class="pt-0">
        <v-card>
          <v-card-text class="py-0">
            <v-container fluid>
              <v-row justify="center" align="center" align-content="center">
                <v-col cols="12">
                  <v-data-table
                    class="table-striped"
                    :headers="headers"
                    :items="items"
                    :items-per-page="10"
                    hide-default-footer
                    fixed-header
                    @page-count="pageCount = $event"
                  >
                    <!-- 削除操作 -->
                    <template v-slot:[`item.delete`]="{ item }">
                      <v-tooltip bottom>
                        <template v-slot:activator="{ on }">
                          <v-btn v-on="on" icon>
                            <v-icon color="error" @click="del(item.id)"
                              >fas fa-trash-alt</v-icon
                            >
                          </v-btn>
                        </template>
                        <span>削除</span>
                      </v-tooltip>
                    </template>
                  </v-data-table>
                </v-col>
                <v-col cols="12" class="text-center">
                  <v-pagination v-model="page" :length="pageCount" />
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <file-upload-dialog v-if="dialog" v-model="dialog" />
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
import mixin from "@/utils/script/mixin";
import { cmnMst, template } from "@/utils/api/uriList";
import { mapActions, mapState } from "vuex";
const rest = require("@/utils/api/rest");

export default {
  name: "",
  mixins: [mixin],
  props: {},
  data: () => ({
    // 検索条件フォーム
    form: {},
    // ヘッダー
    headers: [
      {
        text: "ID",
        align: "center",
        sortable: true,
        value: "id",
      },
      {
        text: "ファイル名",
        align: "center",
        sortable: true,
        value: "fileName",
      },
      {
        text: "ファイルパス",
        align: "center",
        sortable: false,
        value: "path",
      },
      {
        text: "ファイル種別",
        align: "center",
        sortable: true,
        value: "fileTypeName",
      },
      {
        text: "操作",
        align: "center",
        sortable: false,
        value: "delete",
      },
    ],
    // ファイル一覧
    items: [],
    // ページ
    page: 0,
    // ページ総数
    pageCount: 0,
    // ダイアログフラグ
    dialog: false,
  }),
  methods: {
    /** ファイル種別リストを取得する */
    async getFileTypeList() {
      const uri = cmnMst.GET_FILETYPE_LIST;
      const res = await rest.get(uri);
      console.debug("ファイル一覧一覧:", res);

      if (res.status) {
        this.setFileTypeList(res.data);
      }
    },
    // フォームクリア
    clear() {
      this.$refs.form.reset();
    },
    // 検索
    async search() {
      this.setLoading(true);
      const res = await rest
        .post(template.SEARCH_TEMPLATE_LIST, this.form)
        .then((res) => res)
        .catch((err) => err);

      this.setLoading(false);
      if (res.status) {
        this.items = res.data;
      }
    },

    // 削除
    async del(id) {
      const wait = await this.confirm("削除しますか？")
      if(wait) {
        const res = await rest.put(template.DELETE_TEMPLATE_FILE + id).then(res => res).catch(err => err);
        if(res.status) {
          await this.complete("削除しました")
          this.getFruitsListTemplates();
          this.search();
        }
      }
    },

    ...mapActions("master", ["setFileTypeList", "getFruitsListTemplates"]),
  },
  created() {
    this.$nextTick(() => {
      this.getFileTypeList();
    });
  },
  computed: {
    ...mapState({
      fileTypeList: (state) => state.master.fileTypeList,
    }),
  },
  watch: {},
  components: {
    TextField: () => import("@/components/common/form/TextField"),
    SelectBox: () => import("@/components/common/form/SelectBox"),
    FileUploadDialog: () =>
      import("@/components/tabs/excel/dialog/FileUploadDIalog"),
  },
};
</script>

<style scoped></style>
