import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "",
    name: "DynamicExcelMain",
    component: () => import("@/views/DynamicExcel"),
  },
  {
    path: "/sample",
    name: "SampleTop",
    component: () => import("@/views/SampleTopFrame"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
