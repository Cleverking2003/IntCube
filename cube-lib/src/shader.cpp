#include "shader.hpp"

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
        return false;
    }

    glAttachShader(m_programId, shaderId);
    glDeleteShader(shaderId);
    return true;
}

void ShaderProgram::use() {
    glUseProgram(m_programId);
}

ShaderProgram::~ShaderProgram() {
    glDeleteProgram(m_programId);
}

void ShaderProgram::setUniformMatrix4fv(char const* name, glm::mat4x4& mat) {
    glUseProgram(m_programId);
    auto loc = glGetUniformLocation(m_programId, name);
    glUniformMatrix4fv(loc, 1, GL_FALSE, &mat[0][0]);
}

void ShaderProgram::setInt(char const* name, int value) {
    glUseProgram(m_programId);
    auto loc = glGetUniformLocation(m_programId, name);
    glUniform1i(loc, value);
}

void ShaderProgram::setIntArray(char const* name, int* value, int count) {
    glUseProgram(m_programId);
    auto loc = glGetUniformLocation(m_programId, name);
    glUniform1iv(loc, count, value);
}
