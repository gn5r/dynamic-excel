import { mapActions, mapState } from "vuex";

export default {
  name: "",
  mixins: [],
  props: {},
  data: () => ({}),
  methods: {
    
    /**
     * Vue Routerで指定したnameを持つページへ遷移する
     * 
     * @param {String} target 遷移先名
     */
    to(target = null) {
      if (this.$route.name !== target) {
        this.$router.push({ name: target });
      }
    },

    ...mapActions("app", ["setLoading"]),
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
