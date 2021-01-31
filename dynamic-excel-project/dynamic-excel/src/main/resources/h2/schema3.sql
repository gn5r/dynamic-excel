CREATE OR REPLACE VIEW `科_VIEW` (`登録順`, `科名`) AS SELECT MIN(id), 科 FROM 果物 WHERE 論理削除フラグ = 0 GROUP BY 科
