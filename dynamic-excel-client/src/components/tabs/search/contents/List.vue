<template>
  <v-container fluid>
    <v-row justify="center" align="center" no-gutters>
      <v-col>
        <v-data-table
          :headers="headers"
          :items="items"
          :loading.sync="loading"
          loading-text="データ取得中です..."
          hide-default-footer
          no-data-text="データがありません"
          :page.sync="page"
          :items-per-page="10"
          @page-count="pageCount = $event"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon color="error" @click="del(item)">mdi-delete</v-icon>
          </template>
        </v-data-table>
        <div class="text-center">
          <v-pagination v-model="page" :length="pageCount" />
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
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
    loading: {
      type: Boolean,
      default: false,
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
  methods: {},
  created() {},
  computed: {},
  watch: {},
  components: {},
};
</script>

<style scoped>
</style>