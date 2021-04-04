select
  id,
  種別名,
  登録日,
  更新日,
  論理削除フラグ
from
  FILE_TYPE_MST
order by
  更新日 desc,
  登録日 desc,
  id asc