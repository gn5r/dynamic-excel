module.exports = {
  transpileDependencies: ["vuetify"],
  // ベースパス
  publicPath: "/dynamic-excel",

  // 出力ディレクトリ
  outputDir: process.env.VUE_APP_OUTPUT_DIR,

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
