<template>
  <v-container fluid>
    <v-row justify="center" align="center" no-gutters>
      <v-col cols="8">
        <v-card>
          <v-card-title>
            <v-container fluid>
              <v-row justify="start" align="center" no-gutters>
                <v-col cols="9">
                  <text-field
                    v-model="search"
                    append-icon="mdi-magnify"
                    placeholder="検索単語を入力"
                    hide-details
                  />
                </v-col>

                <v-col cols="3">
                  <v-btn color="success" @click="fileTypeMstDialog = true"
                    >新規作成</v-btn
                  >
                </v-col>
              </v-row>
            </v-container>
          </v-card-title>
          <v-card-text>
            <v-data-table
              class="table-striped"
              :headers="headers"
              :items="items"
              :search="search"
              hide-default-footer
              fixed-header
            >
              <template v-slot:[`item.actions`]="{ item }">
                <v-tooltip bottom>
                  <template v-slot:activator="{ on }">
                    <v-btn v-on="on" icon>
                      <v-icon color="primary" @click="edit(item.id)"
                        >fas fa-edit</v-icon
                      >
                    </v-btn>
                  </template>
                  <span>編集</span>
                </v-tooltip>

                <v-tooltip v-if="!item.delFlg" bottom>
                  <template v-slot:activator="{ on }">
                    <v-btn v-on="on" icon>
                      <v-icon
                        color="error"
                        @click="del(item.id)"
                        >fas fa-trash-alt</v-icon
                      >
                    </v-btn>
                  </template>
                  <span>削除</span>
                </v-tooltip>

                <v-tooltip v-else bottom>
                  <template v-slot:activator="{ on }">
                    <v-btn v-on="on" icon>
                      <v-icon
                        color="success"
                        @click="restore(item.id)"
                        >fas fa-trash-restore-alt</v-icon
                      >
                    </v-btn>
                  </template>
                  <span>復活</span>
                </v-tooltip>
              </template>
            </v-data-table>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <file-type-mst-dialog
      v-if="fileTypeMstDialog"
      :dialog.sync="fileTypeMstDialog"
      :id="fileTypeId"
      @done="getList"
    />

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
const rest = require("@gn5r/vue-axios");
import { mapActions, mapState } from "vuex";

export default {
  name: "",
  mixins: [],
  props: {},
  data: () => ({
    search: null,
    fileTypeMstDialog: false,
    fileTypeId: null,
    headers: [
      {
        text: "ID",
        align: "center",
        sortable: true,
        value: "id",
      },
      {
        text: "種別名",
        align: "center",
        sortable: false,
        value: "typeName",
      },
      {
        text: "登録日",
        align: "center",
        sortable: true,
        value: "createDate",
      },
      {
        text: "更新日",
        align: "center",
        sortable: true,
        value: "updateDate",
      },
      {
        text: "状態",
        align: "center",
        sortable: false,
        value: "status",
      },
      {
        text: "操作",
        align: "center",
        sortable: false,
        value: "actions",
      },
    ],
    items: [],
  }),
  methods: {
    async getList() {
      const uri = "/fileTypeMst/get/list";
      const res = await rest.get(uri);

      if (res.status) {
        this.items = [];
        Array.from(res.data).forEach((data) => {
          Object.keys(data).map((key) => {
            data["status"] = data["delFlg"] ? "論理削除" : "アクティブ";
          });
          console.debug(data);
          this.items.push(data);
        });
      } else {
        console.error("ファイル種別マスタ取得エラー:", res);
      }
    },
    edit(id = null) {
      this.fileTypeId = id;
      this.fileTypeMstDialog = true;
    },
    async del(id = null) {
      const wait = await this.confirm("削除しますか？");
      if (wait) {
        const uri = `/fileTypeMst/delete/${id}`;
        const res = await rest.del(uri);

        if (res.status) {
          await this.complete("削除しました");
          this.getList();
        }
      }
    },

    async restore(id = null) {
      const wait = await this.confirm("復活させますか？");
      if (wait) {
        const uri = `/fileTypeMst/restore/${id}`;
        this.setLoading(true);
        const res = await rest
          .put(uri)
          .then((res) => {
            this.setLoading(false);
            return res;
          })
          .catch((err) => {
            this.setLoading(false);
            return err;
          });

        if (res.status) {
          await this.complete("復活させました");
          this.getList();
        }
      }
    },

    changeStatus(id = null, delflg = Boolean) {
      const item = this.items.find((row) => row.id === id);
      console.debug(item);
      const idx = this.items.findIndex((row) => row.id === id);

      if (item !== undefined && idx !== -1) {
        this.$nextTick(() => {
          this.items[idx].delFlg = delflg;
        });
      } else {
        console.error(`ID:${id}で見つかりません`);
      }
    },

    ...mapActions("app", ["setLoading"]),
  },
  created() {
    this.getList();
  },
  computed: {},
  watch: {},
  components: {
    TextField: () => import("@/components/common/form/TextField"),
    FileTypeMstDialog: () =>
      import("@/components/tabs/mstMainte/dialog/FileTypeMstDialog"),
  },
};
</script>

<style scoped></style>
