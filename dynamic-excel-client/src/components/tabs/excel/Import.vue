<template>
  <v-container fluid>
    <v-row justify="center" align-content="center" align="center">
      <v-col cols="3">
        <v-card>
          <v-card-text>
            <v-row no-gutters>
              <v-col class="text-center">
                <input
                  v-if="fileView"
                  type="file"
                  ref="file"
                  accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                  style="display: none"
                  @change="post"
                />
                <v-btn color="primary" outlined @click="fileImport"
                  >Excel取込</v-btn
                >
              </v-col>
              <v-col>
                <v-btn color="success" outlined @click="post">POST</v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axiosBase from "@/utils/api/axiosBase";

export default {
  name: "",
  mixins: [],
  props: {},
  data: () => ({
    fileView: true,
  }),
  methods: {
    /**
     * inputタグのクリックイベントを発火させファイル選択する
     */
    fileImport() {
      this.$refs.file.click();
    },
    async post() {
      const file = this.$refs.file.files[0];
      console.debug("file", file);

      this.fileView = false;
      this.$nextTick(() => {
        this.fileView = true;
      });

      const data = new FormData();
      if(file !== null && file !== undefined) {
        data.append("file", file);
      }
      data.append("formData", new Blob([JSON.stringify({message: "こんにちは"})], {type: "application/json"}));
      // const headers = { "content-type": "multipart/form-data" };
      await axiosBase.post("excel/import", data);
    },
  },
  created() {},
  computed: {},
  watch: {},
  components: {},
};
</script>

<style scoped></style>
