create table if not exists `果物` (
  id int not null primary key auto_increment comment '登録順に自動で割り振られるID',
  `漢字名称` varchar(32) not null comment '果物の漢字名称',
  `一般名称` varchar(64) not null comment '果物の一般的に使用される名称(ひらがな/カタカナ)',
  `目` varchar(16) comment '属している目名',
  `科` varchar(16) comment '属している科名',
  `属` varchar(16) comment '属している属名',
  `登録日` datetime comment '登録日',
  `更新日` datetime comment '更新日',
  `論理削除フラグ` tinyint(1) comment '論理削除フラグ'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='果物';

