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
  {
    path: "/mstMnt",
    name: "MstMntTop",
    component: () => import("@/views/MstMaintenance"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
