# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.9

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /opt/clion-2017.3.2/bin/cmake/bin/cmake

# The command to remove a file.
RM = /opt/clion-2017.3.2/bin/cmake/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/m/CLionProjects/tcp_sockets_example

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/m/CLionProjects/tcp_sockets_example/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/hello_world.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/hello_world.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/hello_world.dir/flags.make

CMakeFiles/hello_world.dir/main.c.o: CMakeFiles/hello_world.dir/flags.make
CMakeFiles/hello_world.dir/main.c.o: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/m/CLionProjects/tcp_sockets_example/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/hello_world.dir/main.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/hello_world.dir/main.c.o   -c /home/m/CLionProjects/tcp_sockets_example/main.c

CMakeFiles/hello_world.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/hello_world.dir/main.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/m/CLionProjects/tcp_sockets_example/main.c > CMakeFiles/hello_world.dir/main.c.i

CMakeFiles/hello_world.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/hello_world.dir/main.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/m/CLionProjects/tcp_sockets_example/main.c -o CMakeFiles/hello_world.dir/main.c.s

CMakeFiles/hello_world.dir/main.c.o.requires:

.PHONY : CMakeFiles/hello_world.dir/main.c.o.requires

CMakeFiles/hello_world.dir/main.c.o.provides: CMakeFiles/hello_world.dir/main.c.o.requires
	$(MAKE) -f CMakeFiles/hello_world.dir/build.make CMakeFiles/hello_world.dir/main.c.o.provides.build
.PHONY : CMakeFiles/hello_world.dir/main.c.o.provides

CMakeFiles/hello_world.dir/main.c.o.provides.build: CMakeFiles/hello_world.dir/main.c.o


# Object files for target hello_world
hello_world_OBJECTS = \
"CMakeFiles/hello_world.dir/main.c.o"

# External object files for target hello_world
hello_world_EXTERNAL_OBJECTS =

hello_world: CMakeFiles/hello_world.dir/main.c.o
hello_world: CMakeFiles/hello_world.dir/build.make
hello_world: CMakeFiles/hello_world.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/m/CLionProjects/tcp_sockets_example/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable hello_world"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/hello_world.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/hello_world.dir/build: hello_world

.PHONY : CMakeFiles/hello_world.dir/build

CMakeFiles/hello_world.dir/requires: CMakeFiles/hello_world.dir/main.c.o.requires

.PHONY : CMakeFiles/hello_world.dir/requires

CMakeFiles/hello_world.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/hello_world.dir/cmake_clean.cmake
.PHONY : CMakeFiles/hello_world.dir/clean

CMakeFiles/hello_world.dir/depend:
	cd /home/m/CLionProjects/tcp_sockets_example/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/m/CLionProjects/tcp_sockets_example /home/m/CLionProjects/tcp_sockets_example /home/m/CLionProjects/tcp_sockets_example/cmake-build-debug /home/m/CLionProjects/tcp_sockets_example/cmake-build-debug /home/m/CLionProjects/tcp_sockets_example/cmake-build-debug/CMakeFiles/hello_world.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/hello_world.dir/depend

