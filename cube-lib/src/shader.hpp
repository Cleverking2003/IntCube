#pragma once

#define GL_GLEXT_PROTOTYPES
#include <SFML/OpenGL.hpp>

#include <glm/glm.hpp>

class ShaderProgram {
public:
    explicit ShaderProgram(char const* vertex, char const* fragment);
    ShaderProgram(ShaderProgram&) = delete;

    bool loadFromFile(char const* filename, GLenum type);
    void use();
    ~ShaderProgram();
    void setUniformMatrix4fv(char const* name, glm::mat4x4& mat);
    void setInt(char const* name, int value);
    void setIntArray(char const* name, int* value, int count);
    void setVec3(char const* name, glm::vec3);

private:
    int m_programId;
};
