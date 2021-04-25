/** 共通マスタAPI */
const cmnMst = {
  /** 目名一覧の取得:/cmnMst/get/orderList */
  GET_ORDER_LIST: "/cmnMst/get/orderList",

  /** 科名一覧の取得:/cmnMst/get/familyList */
  GET_FAMILY_LIST: "/cmnMst/get/familyList",

  /** 属名一覧の取得:/cmnMst/get/genusList */
  GET_GENUS_LIST: "/cmnMst/get/genusList",

  /** ファイル種別一覧の取得:/cmnMst/get/fileTypeList */
  GET_FILETYPE_LIST: "/cmnMst/get/fileTypeList",
};

/** Excel API */
const excel = {
  /** 果物一覧のExcelテンプレートファイル一覧の取得:/excel/get/templateList */
  GET_TEMPLATE_LIST: "/excel/get/templateList",
};

/** Excel Template API */
const template = {
  POST_TEMPLATE_FILES: "/template/upload/files",
  SEARCH_TEMPLATE_LIST: "/template/search",
  DELETE_TEMPLATE_FILE: "/template/delete/"
}

module.exports = {
  cmnMst: cmnMst,
  excel: excel,
  template: template,
};
