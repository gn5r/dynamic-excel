<template>
  <v-container fluid class="pa-0 ma-0">
    <v-row justify="start" align-content="center" align="center" no-gutters>
      <v-col>
        <v-card>
          <header-bar title="サンプルページ">
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
                    <v-icon>fas fa-edit</v-icon>
                  </v-btn>
                </template>
                <span>マスタメンテナンス</span>
              </v-tooltip>
            </template>
          </header-bar>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "",
  mixins: [],
  props: {},
  data: () => ({}),
  methods: {
    to(target = null) {
      if (this.$route.name !== target) {
        this.$router.push({ name: target });
      }
    },
  },
  created() {},
  computed: {},
  watch: {},
  components: {
    HeaderBar: () => import("@/components/common/HeaderBar"),
  },
};
</script>

<style scoped></style>
