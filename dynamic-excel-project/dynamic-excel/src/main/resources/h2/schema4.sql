CREATE OR REPLACE VIEW `属_VIEW` (`登録順`, `属名`) AS SELECT MIN(id), 属 FROM 果物 WHERE 論理削除フラグ = 0 GROUP BY 属
