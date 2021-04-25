<template>
  <v-dialog :value.sync="dialog" max-width="1200" persistent @input="close">
    <v-card>
      <v-card-title class="pa-0">
        <v-toolbar dark color="primary" dense>
          <v-toolbar-title>ファイルアップロード</v-toolbar-title>
          <v-spacer />
          <v-toolbar-items>
            <v-tooltip bottom>
              <template v-slot:activator="{ on }">
                <v-btn v-on="on" icon @click="close">
                  <v-icon>mdi-close</v-icon>
                </v-btn>
              </template>
              <span>ダイアログを閉じる</span>
            </v-tooltip>
          </v-toolbar-items>
        </v-toolbar>
      </v-card-title>

      <v-card-text class="px-0 pb-0">
        <v-container fluid>
          <v-form lazy-validation ref="form">
            <v-row
              justify="end"
              align="center"
              align-content="center"
              no-gutters
            >
              <v-col class="text-end">
                <input
                  v-if="fileView"
                  type="file"
                  ref="file"
                  accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                  style="display: none"
                  multiple
                  @change="addItem"
                />
                <v-btn color="primary" outlined @click="fileSelect"
                  >ファイル選択</v-btn
                >
              </v-col>
            </v-row>
            <v-row
              class="mt-3"
              justify="start"
              align="center"
              align-content="center"
              no-gutters
            >
              <v-col cols="12">
                <v-card>
                  <v-data-table
                    class="table-striped"
                    :headers="headers"
                    :items="items"
                    item-key="id"
                    hide-default-footer
                  >
                    <!-- ファイル種別ID -->
                    <template v-slot:[`item.fileTypeId`]="{ item }">
                      <select-box
                        v-model="item.fileTypeId"
                        :items="fileTypeList"
                        required
                      />
                    </template>

                    <!-- ファイル名 -->
                    <template v-slot:[`item.fileName`]="{ item }">
                      <text-field v-model="item.fileName" required max="256" />
                    </template>

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
                </v-card>
              </v-col>
            </v-row>
          </v-form>
        </v-container>
      </v-card-text>

      <v-divider />

      <v-card-actions>
        <v-spacer />
        <v-btn color="success" @click="doSave">登録</v-btn>
      </v-card-actions>
    </v-card>

    <!-- confirm -->
    <v-confirm
      :dialog.sync="confirmObj.dialog"
      :title="confirmObj.title"
      :title-icon="confirmObj.titleIcon"
      :title-color="confirmObj.titleColor"
      :message="confirmObj.message"
      :buttons="confirmObj.buttons"
    />
  </v-dialog>
</template>

<script>
import mixin from "@/utils/script/mixin";
import { cmnMst, template } from "@/utils/api/uriList";
import { mapState, mapActions } from "vuex";
const rest = require("@/utils/api/rest");

export default {
  name: "file-upload-dialog",
  mixins: [mixin],
  model: {
    prop: "dialog",
    event: "change",
  },
  props: {
    dialog: Boolean,
  },
  data: () => ({
    fileView: true,
    selected: [],
    headers: [
      {
        text: "ファイル種別",
        align: "center",
        sortable: false,
        value: "fileTypeId",
        width: "15%",
      },
      {
        text: "ファイル名",
        align: "center",
        sortable: false,
        value: "fileName",
        width: "30%",
      },
      {
        text: "削除",
        align: "center",
        sortable: false,
        value: "delete",
        width: "5%",
      },
    ],
    items: [],
  }),
  methods: {
    close() {
      this.$emit("update:dialog", false);
      this.$emit("change", false);
    },

    /**
     * inputタグのクリックイベントを発火させファイル選択する
     */
    fileSelect() {
      this.$refs.file.click();
    },

    addItem() {
      const files = this.$refs.file.files;
      for (let i = 0; i < Array.from(files).length; i++) {
        const file = files[i];
        console.debug("ファイル:", file);
        const item = {
          id: this.items.length + i,
          fileTypeId: null,
          fileName: file.name,
          file: file,
        };
        this.items.push(item);
      }
    },

    del(id) {
      this.items.splice(id, 1);
    },

    async doSave() {
      if (!this.$refs.form.validate()) return;

      const wait = await this.confirm("ファイルをアップロードしますか？");
      if (wait) {
        this.setLoading(true);

        const formData = new FormData();
        // リクエストボディ
        let body = {
          files: []
        };

        this.items.forEach((item) => {
          formData.append("files", item.file);
          body.files.push(item)
        });

        formData.append(
          "form",
          new Blob([JSON.stringify(body)], {
            type: "application/json",
          })
        );

        const res = await rest
          .post(template.POST_TEMPLATE_FILES, formData)
          .then((res) => res)
          .catch((err) => err);

        this.setLoading(false);

        if (res.status) {
          await this.complete("アップロードしました");
          this.getFruitsListTemplates();
          this.close();
        }
      }
    },

    ...mapActions("master", ["getFruitsListTemplates"]),
  },
  created() {},
  computed: {
    ...mapState({
      fileTypeList: (state) => state.master.fileTypeList,
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
