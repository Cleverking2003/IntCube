#include "gl_helpers.hpp"

#ifdef __ANDROID__
#include <android/log.h>
#endif

#include <fstream>
#include <iostream>
#include <sstream>

ShaderProgram::ShaderProgram(char const* vertex, char const* fragment) {
    m_programId = glCreateProgram();

    if (!loadFromFile(vertex, GL_VERTEX_SHADER)) {
        std::cout << "Couldn't load vertex shader\n";
    }

    if (!loadFromFile(fragment, GL_FRAGMENT_SHADER)) {
        std::cout << "Couldn't load fragment shader\n";
    }

    glLinkProgram(m_programId);
}

ShaderProgram::ShaderProgram(char ** vertex, char ** fragment) {
    m_programId = glCreateProgram();

    if (!loadFromString(vertex, GL_VERTEX_SHADER)) {
        std::cout << "Couldn't load vertex shader\n";
    }

    if (!loadFromString(fragment, GL_FRAGMENT_SHADER)) {
        std::cout << "Couldn't load fragment shader\n";
    }

    glLinkProgram(m_programId);
    int success;
    char log[512];
    glGetProgramiv(m_programId, GL_LINK_STATUS, &success);
    if (success == GL_FALSE) {
        glGetProgramInfoLog(m_programId, 512, NULL, log);
        std::cout << "Shader link: " << log << '\n';
#ifdef __ANDROID__
        __android_log_print(ANDROID_LOG_ERROR, "glsl", "Shader link: %s\n", log);
#endif
    }
}

bool ShaderProgram::loadFromFile(char const* filename, GLenum type) {
    std::ifstream file(filename);

    if (!file.is_open()) {
        std::cout << "Couldn't open the file!\n";
        return false;
    }

    int shaderId = glCreateShader(type);

    std::stringstream buffer;
    buffer << file.rdbuf();
    auto str = buffer.str();
    auto c_str = str.c_str();
    file.close();
    glShaderSource(shaderId, 1, &c_str, NULL);
    glCompileShader(shaderId);

    int success;
    char log[512];
    glGetShaderiv(shaderId, GL_COMPILE_STATUS, &success);
    if (!success) {
        glGetShaderInfoLog(shaderId, 512, NULL, log);
        std::cout << "Shader compilation error: " << log << '\n';
#ifdef __ANDROID__
        __android_log_print(ANDROID_LOG_ERROR, "glsl", "Shader compile error: %s\n", log);
#endif
        return false;
    }

    glAttachShader(m_programId, shaderId);
//    glDeleteShader(shaderId);
    return true;
}

bool ShaderProgram::loadFromString(char** str, GLenum type) {
    int shaderId = glCreateShader(type);
    glShaderSource(shaderId, 1, str, NULL);
    glCompileShader(shaderId);

    int success;
    char log[512];
    glGetShaderiv(shaderId, GL_COMPILE_STATUS, &success);
    if (!success) {
        glGetShaderInfoLog(shaderId, 512, NULL, log);
        std::cout << "Shader compilation error: " << log << '\n';
#ifdef __ANDROID__
        __android_log_print(ANDROID_LOG_ERROR, "glsl", "Shader compile error: %s\n", log);
#endif
        return false;
    }

    glAttachShader(m_programId, shaderId);
//    glDeleteShader(shaderId);
    // glLinkProgram(m_programId);
    return true;
}

void ShaderProgram::use() {
    glUseProgram(m_programId);
}

ShaderProgram::~ShaderProgram() {
    glDeleteProgram(m_programId);
}

void ShaderProgram::setUniformMatrix4fv(char const* name, glm::mat4x4& mat) {
#ifdef __ANDROID__
    __android_log_print(ANDROID_LOG_ERROR, "program", "PROGRAM ID: %d\n", m_programId);
#endif
    GLenum err;
#ifdef __ANDROID__
    while ((err = glGetError()) != GL_NO_ERROR)
        __android_log_print(ANDROID_LOG_ERROR, "gl", "OPENGL ERROR %x\n", err);
#endif
     glUseProgram(m_programId);
#ifdef __ANDROID__
    while ((err = glGetError()) != GL_NO_ERROR)
        __android_log_print(ANDROID_LOG_ERROR, "gl_draw", "OPENGL ERROR %x\n", err);
#endif
    auto loc = glGetUniformLocation(m_programId, name);
#ifdef __ANDROID__
    __android_log_print(ANDROID_LOG_ERROR, "mat4_uniform", "UNIFORM LOCATION: %d\n", loc);
#endif
    glUniformMatrix4fv(loc, 1, GL_FALSE, &mat[0][0]);
}

void ShaderProgram::setInt(char const* name, int value) {
#ifdef __ANDROID__
    __android_log_print(ANDROID_LOG_ERROR, "program", "PROGRAM ID: %d\n", m_programId);
#endif
     glUseProgram(m_programId);
    auto loc = glGetUniformLocation(m_programId, name);
    glUniform1i(loc, value);
}

void ShaderProgram::setIntArray(char const* name, int* value, int count) {
     glUseProgram(m_programId);
    auto loc = glGetUniformLocation(m_programId, name);
    glUniform1iv(loc, count, value);
}

void ShaderProgram::setVec3(char const* name, glm::vec3 vec) {
     glUseProgram(m_programId);
    auto loc = glGetUniformLocation(m_programId, name);
#ifdef __ANDROID__
    __android_log_print(ANDROID_LOG_ERROR, "vec3_uniform", "UNIFORM LOCATION: %d\n", loc);
#endif
    glUniform3fv(loc, 1, &vec[0]);
}
