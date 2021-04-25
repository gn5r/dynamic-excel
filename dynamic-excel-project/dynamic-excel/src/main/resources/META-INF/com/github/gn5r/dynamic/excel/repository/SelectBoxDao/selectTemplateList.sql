select
  ID,
  ファイル名 as value
from
  EXCEL_TEMPLATE_TBL
where
  論理削除フラグ = 0
order by
  ID