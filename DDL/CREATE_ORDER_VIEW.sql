﻿CREATE OR REPLACE VIEW `目_VIEW` (`登録順`, `目名`) AS SELECT MIN(id), 目 FROM 果物 WHERE 論理削除フラグ = 0 GROUP BY 目
