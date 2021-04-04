select
  id,
  file_type_id,
  ファイル名,
  パス,
  登録日,
  更新日,
  論理削除フラグ
from
  EXCEL_TEMPLATE_TBL
where
  id = /* id */1
