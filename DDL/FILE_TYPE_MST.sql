create table `FILE_TYPE_MST` (
  `ID` int not null primary key auto_increment comment '登録順に自動で割り振られるID',
  `種別名` varchar(128) not null comment 'ファイル種別名',
  `登録日` datetime comment '登録日',
  `更新日` datetime comment '更新日',
  `論理削除フラグ` tinyint(1) comment '論理削除フラグ',
  `PREFIX_PATH` varchar(64) is not null comment 'ファイル種別ごとの保存先パスの接頭辞'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ファイル種別マスタ';
