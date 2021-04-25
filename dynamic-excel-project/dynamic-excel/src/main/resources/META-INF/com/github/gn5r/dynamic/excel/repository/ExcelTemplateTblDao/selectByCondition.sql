select
  A.ID,
  A.FILE_TYPE_ID,
  A.ファイル名,
  A.パス,
  A.登録日,
  A.更新日,
  B.種別名,
  B.PREFIX_PATH
from
  EXCEL_TEMPLATE_TBL A
  inner join FILE_TYPE_MST B
    on A.FILE_TYPE_ID = B.ID
    and B.論理削除フラグ = 0
where
  A.論理削除フラグ = 0
/*%if condition.id != null */
  and A.id = /* condition.id */'a'
/*%end*/
/*%if condition.fileTypeId != null */
  and A.FILE_TYPE_ID = /* condition.fileTypeId */'a'
/*%end*/
order by
  A.更新日 desc,
  A.登録日 desc