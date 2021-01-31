select
  id,
  漢字名称,
  一般名称,
  目,
  科,
  属,
  登録日,
  更新日,
  論理削除フラグ
from
  果物
where
  id = /* id */1
  and 論理削除フラグ = 0