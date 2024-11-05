# Geodata Configuration Documentation

This document explains the purpose of the `geoengine.properties` file in the `aCis_gameserver/config` directory and provides examples and explanations for each setting in the file.

## Purpose of `geoengine.properties`

The `geoengine.properties` file is used to configure the geodata settings for the aCis server. Geodata is essential for accurate collision detection, pathfinding, and other spatial calculations in the game world.

## Settings

### GeoDataPath

- **Description**: Specifies the path to the geodata files.
- **Default**: `./data/geodata/`
- **Example**: `GeoDataPath = ./data/geodata/`

### CoordSynchronize

- **Description**: Player coordinates synchronization.
- **Default**: `2`
- **Options**:
  - `1`: Partial synchronization Client --> Server; don't use it with geodata.
  - `2`: Partial synchronization Server --> Client; use this setting with geodata.
  - `-1`: Old system: will synchronize Z only.
- **Example**: `CoordSynchronize = 2`

### PartOfCharacterHeight

- **Description**: Line of sight start at X percent of the character height.
- **Default**: `75`
- **Example**: `PartOfCharacterHeight = 75`

### MaxObstacleHeight

- **Description**: Maximum height of an obstacle, which can exceed the line of sight.
- **Default**: `32`
- **Example**: `MaxObstacleHeight = 32`

### PathFinding

- **Description**: When line of movement check fails, the pathfinding algorithm is performed to look for an alternative path.
- **Default**: `true`
- **Example**: `PathFinding = true`

### PathFindBuffers

- **Description**: Pathfinding array buffers configuration.
- **Default**: `100x6;128x6;192x6;256x4;320x4;384x4;500x2`
- **Example**: `PathFindBuffers = 100x6;128x6;192x6;256x4;320x4;384x4;500x2`

### BaseWeight

- **Description**: Base path weight, when moving from one node to another on axis direction.
- **Default**: `10`
- **Example**: `BaseWeight = 10`

### DiagonalWeight

- **Description**: Path weight, when moving from one node to another on diagonal direction.
- **Default**: `14`
- **Example**: `DiagonalWeight = 14`

### ObstacleMultiplier

- **Description**: When movement flags of target node is blocked to any direction, multiply movement weight by this multiplier.
- **Default**: `10`
- **Example**: `ObstacleMultiplier = 10`

### HeuristicWeight

- **Description**: Weight of the heuristic algorithm, which is giving estimated cost from node to target.
- **Default**: `20`
- **Example**: `HeuristicWeight = 20`

### MaxIterations

- **Description**: Maximum number of generated nodes per one path-finding process.
- **Default**: `3500`
- **Example**: `MaxIterations = 3500`

### DebugPath

- **Description**: Path debug function, FOR DEBUG PURPOSES ONLY!
- **Default**: `False`
- **Example**: `DebugPath = False`

### DebugGeoNode

- **Description**: Write invalid nodes into geo_bugs.txt, FOR DEBUG PURPOSES ONLY!
- **Default**: `False`
- **Example**: `DebugGeoNode = False`

### Geodata Files

- **Description**: The world contains 176 regions (11 x 16), each region has its own geodata file. Geodata files are loaded according to the list below.
  - `16_10`: Load region (geodata options are enabled). Server will not start until all enabled regions are loaded properly.
  - `#16_10`: Skip region (geodata options are disabled). Disabled regions will be considered as in "everything-allowed" mode. Monster aggression/attacks/spellcast/movement will pass through walls and other obstacles, no pathfinding will be used.
- **Example**:
  ```
  16_10
  16_11
  16_12
  #16_13 - not supported by L2 client
  #16_14 - not supported by L2 client
  #16_15 - not supported by L2 client
  #16_16 - not supported by L2 client
  #16_17 - not supported by L2 client
  #16_18 - not supported by L2 client
  16_19
  16_20
  16_21
  16_22
  16_23
  16_24
  16_25
  17_10
  17_11
  17_12
  #17_13 - not supported by L2 client
  #17_14 - not supported by L2 client
  #17_15 - not supported by L2 client
  #17_16 - not supported by L2 client
  #17_17 - not supported by L2 client
  17_18
  17_19
  17_20
  17_21
  17_22
  17_23
  17_24
  17_25
  18_10
  18_11
  18_12
  18_13
  18_14
  #18_15 - not supported by L2 client
  #18_16 - not supported by L2 client
  18_17
  18_18
  18_19
  18_20
  18_21
  18_22
  18_23
  18_24
  18_25
  19_10
  19_11
  #19_12 - not supported by L2 client
  19_13
  19_14
  19_15
  19_16
  19_17
  19_18
  19_19
  19_20
  19_21
  19_22
  19_23
  19_24
  19_25
  20_10
  20_11
  #20_12 - not supported by L2 client
  20_13
  20_14
  20_15
  20_16
  20_17
  20_18
  20_19
  20_20
  20_21
  20_22
  20_23
  20_24
  20_25
  #21_10 - not supported by L2 client
  #21_11 - not supported by L2 client
  #21_12 - not supported by L2 client
  21_13
  21_14
  21_15
  21_16
  21_17
  21_18
  21_19
  21_20
  21_21
  21_22
  21_23
  21_24
  21_25
  #22_10 - not supported by L2 client
  #22_11 - not supported by L2 client
  #22_12 - not supported by L2 client
  22_13
  22_14
  22_15
  22_16
  22_17
  22_18
  22_19
  22_20
  22_21
  22_22
  22_23
  22_24
  22_25
  23_10
  23_11
  23_12
  23_13
  23_14
  23_15
  23_16
  23_17
  23_18
  23_19
  23_20
  23_21
  23_22
  23_23
  23_24
  23_25
  24_10
  24_11
  24_12
  24_13
  24_14
  24_15
  24_16
  24_17
  24_18
  24_19
  24_20
  24_21
  24_22
  24_23
  24_24
  24_25
  25_10
  25_11
  25_12
  #25_13 - not supported by L2 client
  25_14
  25_15
  25_16
  25_17
  25_18
  25_19
  25_20
  25_21
  #25_22 - not supported by L2 client
  #25_23 - not supported by L2 client
  #25_24 - not supported by L2 client
  #25_25 - not supported by L2 client
  #26_10 - not supported by L2 client
  26_11
  26_12
  #26_13 - not supported by L2 client
  26_14
  26_15
  26_16
  #26_17 - not supported by L2 client
  #26_18 - not supported by L2 client
  #26_19 - not supported by L2 client
  #26_20 - not supported by L2 client
  #26_21 - not supported by L2 client
  #26_22 - not supported by L2 client
  #26_23 - not supported by L2 client
  #26_24 - not supported by L2 client
  #26_25 - not supported by L2 client
  ```
