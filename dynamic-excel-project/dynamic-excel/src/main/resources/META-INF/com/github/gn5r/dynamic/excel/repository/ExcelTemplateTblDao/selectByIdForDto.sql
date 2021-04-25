select
  a.ID,
  a.FILE_TYPE_ID,
  a.ファイル名,
  a.パス,
  b.種別名,
  b.PREFIX_PATH
from
  excel_template_tbl a
  inner join file_type_mst b
    on a.FILE_TYPE_ID = b.ID
    and b.論理削除フラグ = 0
where
  a.ID = /* id */1
  and a.論理削除フラグ = 0 