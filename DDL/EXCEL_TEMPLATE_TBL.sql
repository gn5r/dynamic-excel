﻿create table `EXCEL_TEMPLATE_TBL` (
  ID int not null primary key auto_increment comment '登録順に自動で割り振られるID',
  `FILE_TYPE_ID` int not null comment 'ファイル種別ID',
  `ファイル名` varchar(256) not null comment 'ファイル名',
  `パス` varchar(1024) not null comment 'ファイルディレクトリ',
  `登録日` datetime comment '登録日',
  `更新日` datetime comment '更新日',
  `論理削除フラグ` tinyint(1) comment '論理削除フラグ',
  CONSTRAINT `ファイル種別紐づけ` FOREIGN KEY (`FILE_TYPE_ID`) REFERENCES `FILE_TYPE_MST` (`ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Excelテンプレートテーブル'
