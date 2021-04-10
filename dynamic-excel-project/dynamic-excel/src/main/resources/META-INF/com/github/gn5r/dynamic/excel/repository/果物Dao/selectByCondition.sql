select
  ID,
  漢字名称,
  一般名称,
  目,
  科,
  属
from
  果物
where
  論理削除フラグ = 0
  /*%if condition.id != null */
  and ID = /* condition.id */1
  /*%end*/
  /*%if @isNotEmpty(condition.kanjiName) */
  and 漢字名称 = /* condition.kanjiName */'a'
  /*%end*/
  /*%if @isNotEmpty(condition.name) */
  and 一般名称 = /* condition.name */'a'
  /*%end*/
  /*%if @isNotEmpty(condition.order) */
  and 目 = /* condition.order */'a'
  /*%end*/
  /*%if @isNotEmpty(condition.family) */
  and 科 = /* condition.family */'a'
  /*%end*/
  /*%if @isNotEmpty(condition.genus) */
  and 属 = /* condition.genus */'a'
  /*%end*/
order by
  ID asc,
  更新日 desc,
  登録日 desc