cmake_minimum_required(VERSION 3.16)
project(cube LANGUAGES CXX C)

#set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/bin)
#option(BUILD_SHARED_LIBS "Build shared libraries" OFF)

include(FetchContent)

if(NOT ANDROID)
FetchContent_Declare(SFML
    GIT_REPOSITORY https://github.com/SFML/SFML.git
    GIT_TAG 2.6.x)
FetchContent_MakeAvailable(SFML)
endif()

FetchContent_Declare(
	glm
	GIT_REPOSITORY	https://github.com/g-truc/glm.git
	GIT_TAG 	refs/tags/1.0.1
)
FetchContent_MakeAvailable(glm)

if(WIN32)
    set(CMAKE_WINDOWS_EXPORT_ALL_SYMBOLS ON)
endif()

include_directories(src/include)
add_library(libcube SHARED src/axis_cube.cpp src/cube.cpp src/mesh.cpp src/scene.cpp src/gl_helpers.cpp src/gl.c)
target_link_libraries(libcube PRIVATE glm::glm)
if(ANDROID)
	find_library(GLES-lib
			GLESv3)
	find_library(android-lib
			android)
	find_library(log-lib
			log)
	target_link_libraries(libcube PRIVATE ${GLES-lib} ${android-lib} ${log-lib})
else()
    target_link_libraries(libcube PRIVATE sfml-window)
endif()
target_compile_features(libcube PRIVATE cxx_std_20)

if(BUILD_CUBE_APP)
    add_subdirectory(cube-app)
endif()

if(NOT ANDROID)
add_custom_command(
        TARGET libcube
        COMMENT "Copy shader files"
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/src/piece_vertex.glsl $<TARGET_FILE_DIR:libcube>
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/src/piece_fragment.glsl $<TARGET_FILE_DIR:libcube>
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/src/simple_vertex.glsl $<TARGET_FILE_DIR:libcube>
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/src/simple_fragment.glsl $<TARGET_FILE_DIR:libcube>
        VERBATIM)
endif()
