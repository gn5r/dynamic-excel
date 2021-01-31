<template>
  <v-container fluid>
    <v-row justify="start" align="center" no-gutters>
      <v-col>
        <!-- アコーディオン -->
        <v-expansion-panels v-model="panel" accordion>
          <!-- 検索条件 -->
          <v-expansion-panel>
            <v-expansion-panel-header>検索条件</v-expansion-panel-header>
            <v-expansion-panel-content>
              <condition
                v-model="result"
                :loading.sync="loading"
                @search="panel = 1"
              />
            </v-expansion-panel-content>
          </v-expansion-panel>
          <!-- 検索結果 -->
          <v-expansion-panel>
            <v-expansion-panel-header>一覧</v-expansion-panel-header>
            <v-expansion-panel-content>
              <list v-model="result" :loading="loading" />
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Condition from "@/components/tabs/search/contents/Condition";
import List from "@/components/tabs/search/contents/List";

export default {
  name: "",
  mixins: [],
  props: {},
  data: () => ({
    panel: 0,
    result: [],
    loading: false,
  }),
  methods: {},
  created() {},
  computed: {},
  watch: {},
  components: {
    Condition,
    List,
  },
};
</script>

<style scoped></style>
