# SQL Files Documentation

This document provides an overview of the SQL files in the `aCis_datapack/sql` directory, along with examples and explanations for each file.

## auction_bid.sql

**Purpose:**  
Defines the structure of the `auction_bid` table, which stores information about auction bids.

**Example:**
```sql
CREATE TABLE IF NOT EXISTS auction_bid (
  id INT NOT NULL default 0,
  auctionId INT NOT NULL default 0,
  bidderId INT NOT NULL default 0,
  bidderName varchar(50) NOT NULL,
  clan_name varchar(50) NOT NULL,
  maxBid int(11) NOT NULL default 0,
  time_bid decimal(20,0) NOT NULL default '0',
  PRIMARY KEY  (auctionId, bidderId),
  KEY id (id)
);
```

## augmentations.sql

**Purpose:**  
Defines the structure of the `augmentations` table, which stores information about item augmentations.

**Example:**
```sql
CREATE TABLE IF NOT EXISTS `augmentations` (
  `item_id` int(11) NOT NULL default 0,
  `attributes` int(11) NOT NULL default -1,
  `skill_id` int(11) NOT NULL default -1,
  `skill_level` int(11) NOT NULL default -1,
  PRIMARY KEY (`item_id`)
);
```

## buffer_schemes.sql

**Purpose:**  
Defines the structure of the `buffer_schemes` table, which stores information about buffer schemes.

**Example:**
```sql
CREATE TABLE IF NOT EXISTS `buffer_schemes` (
  `object_id` INT UNSIGNED NOT NULL DEFAULT '0',
  `scheme_name` VARCHAR(16) NOT NULL DEFAULT 'default',
  `skills` VARCHAR(200) NOT NULL
);
```

## character_autofarm.sql

**Purpose:**  
Defines the structure of the `character_autofarm` table, which stores information about character autofarm settings.

**Example:**
```sql
DROP TABLE IF EXISTS `character_autofarm`;
CREATE TABLE `character_autofarm` (
  `char_id` int(10) unsigned NOT NULL,
  `char_name` varchar(35) COLLATE utf8_unicode_ci NOT NULL,
  `auto_farm` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `radius` int(10) unsigned NOT NULL DEFAULT '1200',
  `short_cut` int(10) unsigned NOT NULL DEFAULT '9',
  `heal_percent` int(10) unsigned NOT NULL DEFAULT '30',
  `buff_protection` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `anti_ks_protection` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `summon_attack` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `summon_skill_percent` int(10) unsigned NOT NULL DEFAULT '0',
  `hp_potion_percent` int(10) unsigned NOT NULL DEFAULT '60',
  `mp_potion_percent` int(10) unsigned NOT NULL DEFAULT '60',
  PRIMARY KEY (`char_id`),
  KEY `char_name` (`char_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
```

## character_friends.sql

**Purpose:**  
Defines the structure of the `character_friends` table, which stores information about character friendships.

**Example:**
```sql
CREATE TABLE IF NOT EXISTS `character_friends` (
  `char_id` INT UNSIGNED NOT NULL default 0,
  `friend_id` INT UNSIGNED NOT NULL DEFAULT 0,
  `relation` INT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`char_id`,`friend_id`)
);
```

## character_recommends.sql

**Purpose:**  
Defines the structure of the `character_recommends` table, which stores information about character recommendations.

**Example:**
```sql
CREATE TABLE IF NOT EXISTS character_recommends ( 
 char_id INT NOT NULL default 0, 
 target_id INT(11) NOT NULL DEFAULT 0, 
 PRIMARY KEY (char_id,target_id) 
); 
```

## character_subclasses.sql

**Purpose:**  
Defines the structure of the `character_subclasses` table, which stores information about character subclasses.

**Example:**
```sql
CREATE TABLE IF NOT EXISTS `character_subclasses` (
`char_obj_id` decimal(11,0) NOT NULL default '0',
`class_id` int(2) NOT NULL default '0',
`exp` decimal(20,0) NOT NULL default '0',
`sp` decimal(11,0) NOT NULL default '0',
`level` int(2) NOT NULL default '40',
`class_index` int(1) NOT NULL default '0',
PRIMARY KEY  (`char_obj_id`,`class_id`)
);
```
