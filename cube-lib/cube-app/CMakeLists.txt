cmake_minimum_required(VERSION 3.16)
project(cube LANGUAGES CXX C)

include_directories(../src/ ../src/include)
add_executable(cube ../src/gl.c main.cpp)
target_link_libraries(cube PRIVATE sfml-graphics glm::glm libcube)
target_compile_features(cube PRIVATE cxx_std_20)

if(WIN32)
    add_custom_command(
        TARGET cube
        COMMENT "Copy OpenAL DLL"
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${SFML_SOURCE_DIR}/extlibs/bin/$<IF:$<EQUAL:${CMAKE_SIZEOF_VOID_P},8>,x64,x86>/openal32.dll $<TARGET_FILE_DIR:cube>
        VERBATIM)

    add_custom_command(
        TARGET cube
        COMMENT "Copy libcube DLL"
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_BINARY_DIR}/libcube.dll $<TARGET_FILE_DIR:cube>
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/src/piece_vertex.glsl $<TARGET_FILE_DIR:cube>
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/src/piece_fragment.glsl $<TARGET_FILE_DIR:cube>
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/src/simple_vertex.glsl $<TARGET_FILE_DIR:cube>
        PRE_BUILD COMMAND ${CMAKE_COMMAND} -E copy ${CMAKE_SOURCE_DIR}/src/simple_fragment.glsl $<TARGET_FILE_DIR:cube>
        VERBATIM)
endif()

install(TARGETS cube)
