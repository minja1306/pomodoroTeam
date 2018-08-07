CREATE TABLE `team_entity` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `pomodoro_entity` (
  `id` bigint(20) NOT NULL,
  `date_time_start` datetime NOT NULL,
  `date_time_stop` datetime NOT NULL,
  `user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK37m8xl0vjh343qf1c9a9y1e6t` (`user`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `user_entity` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `user_team` (
  `user_id` bigint(20) NOT NULL,
  `team_id` bigint(20) NOT NULL,
  KEY `FK86l97au0wt791a04rc2n0jer2` (`team_id`),
  KEY `FK7kvqdhsydefmqwrb4qrat57wi` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
