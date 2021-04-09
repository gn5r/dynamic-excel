SELECT
  ID
  , 種別名 AS value
FROM
  FILE_TYPE_MST
WHERE
  論理削除フラグ = 0
ORDER BY
  ID