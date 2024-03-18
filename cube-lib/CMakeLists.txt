cmake_minimum_required(VERSION 3.16)
project(cube LANGUAGES CXX)

#set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/bin)
option(BUILD_SHARED_LIBS "Build shared libraries" OFF)

include(FetchContent)
FetchContent_Declare(SFML
    GIT_REPOSITORY https://github.com/SFML/SFML.git
    GIT_TAG 2.6.x)
FetchContent_MakeAvailable(SFML)

FetchContent_Declare(
	glm
	GIT_REPOSITORY	https://github.com/g-truc/glm.git
	GIT_TAG 	bf71a834948186f4097caa076cd2663c69a10e1e #refs/tags/1.0.1
)

FetchContent_MakeAvailable(glm)

add_executable(cube src/cube.cpp src/cubie.cpp src/main.cpp src/mesh.cpp src/shader.cpp)
target_link_libraries(cube PRIVATE sfml-graphics glm::glm)
target_compile_features(cube PRIVATE cxx_std_17)

if(WIN32)
    add_custom_command(
        TARGET cube
        COMMENT "Copy OpenAL DLL"
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${SFML_SOURCE_DIR}/extlibs/bin/$<IF:$<EQUAL:${CMAKE_SIZEOF_VOID_P},8>,x64,x86>/openal32.dll $<TARGET_FILE_DIR:cube>
        VERBATIM)
endif()

add_custom_command(
        TARGET cube
        COMMENT "Copy shader files"
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/src/cube_vertex.glsl ${CMAKE_BINARY_DIR}
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/src/cube_fragment.glsl ${CMAKE_BINARY_DIR}
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/src/simple_vertex.glsl ${CMAKE_BINARY_DIR}
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/src/simple_fragment.glsl ${CMAKE_BINARY_DIR}
        VERBATIM)

install(TARGETS cube)
