select
  ID,
  FILE_TYPE_ID,
  ファイル名,
  パス,
  登録日,
  更新日,
  論理削除フラグ
from
  EXCEL_TEMPLATE_TBL
where
  ID = /* id */1
