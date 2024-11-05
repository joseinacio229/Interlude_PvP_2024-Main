# Build Process Documentation

This document explains the purpose of the `build.xml` file in the `aCis_datapack` directory and describes the build process and any dependencies required.

## Purpose of `build.xml`

The `build.xml` file is an Apache Ant build script used to automate the build process for the `aCis_datapack` project. It defines various targets and tasks to compile, package, and distribute the project.

## Build Process

The build process consists of the following main targets:

1. `init`: Initializes the build directories.
2. `clean`: Cleans up the build directories.
3. `build`: Synchronizes the build space contents with the project.
4. `dist`: Distributes the build artifacts.

### Dependencies

The build process requires the following dependencies:

- Apache Ant: A Java-based build tool used to execute the build script.
- Java Development Kit (JDK): Required to compile the Java source code.

### Target Descriptions

#### `init`

The `init` target creates the necessary output directories for the build process. It ensures that the required directories exist before proceeding with the build.

#### `clean`

The `clean` target removes the output directories created during the build process. It depends on the `init` target to ensure that the directories are initialized before attempting to delete them.

#### `build`

The `build` target synchronizes the build space contents with the project. It performs the following tasks:
- Synchronizes the `data` directory with the `build.game` directory, excluding certain files and directories.
- Synchronizes the `sql` directory with the `build.sql` directory.
- Synchronizes the `tools` directory with the `build.tools` directory, excluding certain files.
- Synchronizes the `servername.xml` file with the `build.login` directory.

#### `dist`

The `dist` target distributes the build artifacts to the appropriate directories. It performs the following tasks:
- Copies the compiled JAR file to the `build.dist.login` and `build.dist.game` directories.
- Copies the necessary libraries to the `build.dist.login` and `build.dist.game` directories.
- Copies the configuration files to the `build.dist.login` and `build.dist.game` directories.
- Creates the necessary directories for logs and configuration files.

### Example Usage

To execute the build process, run the following command in the project root directory:

```sh
ant
```

This will execute the default target specified in the `build.xml` file, which is the `dist` target. The build artifacts will be generated and distributed to the appropriate directories.
