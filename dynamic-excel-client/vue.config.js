module.exports = {
  transpileDependencies: ["vuetify"],
  // ベースパス
  publicPath: "/dynamic-excel",

  // タイトルを動的に変更する
  pages: {
    index: {
      entry: "src/main.js",
      title: "dynamic-excel",
    },
  },
  devServer: {
    port: 80,
  },
};
