# aCis Repository

## Overview

This repository contains the aCis project, which is a Lineage II server emulator. The purpose of this repository is to provide a stable and feature-rich server emulator for Lineage II, allowing users to set up and run their own private servers.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Ant
- MySQL or MariaDB

### Setting Up the Project

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/acis.git
   cd acis
   ```

2. Set up the database:
   - Create a new database in your MySQL or MariaDB server.
   - Import the SQL files located in the `aCis_datapack/sql` directory into your database.

3. Configure the server:
   - Edit the configuration files in the `aCis_gameserver/config` directory to match your server settings.

4. Build the project:
   ```sh
   ant
   ```

5. Run the server:
   ```sh
   java -jar aCis_gameserver/dist/gameserver.jar
   ```

## Contributing

### Guidelines

We welcome contributions to the aCis project! To contribute, please follow these guidelines:

1. Fork the repository and create a new branch for your feature or bugfix.
2. Write clear and concise commit messages.
3. Ensure your code follows the project's coding standards.
4. Submit a pull request with a detailed description of your changes.

### Code of Conduct

We expect all contributors to adhere to our code of conduct. Please be respectful and considerate of others in the community.

For more detailed information on contributing, please refer to the `CONTRIBUTING.md` file.
