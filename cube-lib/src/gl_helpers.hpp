#pragma once

#ifdef __ANDROID__
#include <GLES3/gl3.h>
#else
#include <glad/gl.h>
#endif

#include <glm/glm.hpp>

class ShaderProgram {
public:
    explicit ShaderProgram(char const* vertex, char const* fragment);
    explicit ShaderProgram(char** vertex, char** fragment);
    ShaderProgram(ShaderProgram&) = delete;

    bool loadFromFile(char const* filename, GLenum type);
    bool loadFromString(char** str, GLenum type);
    void use();
    ~ShaderProgram();
    void setUniformMatrix4fv(char const* name, glm::mat4x4& mat);
    void setInt(char const* name, int value);
    void setIntArray(char const* name, int* value, int count);
    void setVec3(char const* name, glm::vec3);

private:
    int m_programId;
};

struct Buffer {
    unsigned int index;

    Buffer() {
        glGenBuffers(1, &index);
    }

    ~Buffer() {
        glDeleteBuffers(1, &index);
    }

    Buffer(Buffer&) = delete;
    Buffer(Buffer&&) = delete;

    void bind(GLenum target) const {
        glBindBuffer(target, index);
    }

    void setData(GLenum target, int size, void* data, GLenum usage) const {
        glBindBuffer(target, index);
        glBufferData(target, size, data, usage);
    }
};

struct VertexArray {
    unsigned int index;

    VertexArray() {
        glGenVertexArrays(1, &index);
    }

    ~VertexArray() {
        glDeleteVertexArrays(1, &index);
    }

    VertexArray(VertexArray&) = delete;
    VertexArray(VertexArray&&) = delete;

    void bind() const {
        glBindVertexArray(index);
    }

    void setAttribute(int attrib, int size, GLenum type, GLboolean normalized, int stride, size_t offset) const {
        glBindVertexArray(index);
        glVertexAttribPointer(attrib, size, type, normalized, stride, (void*)offset);
        glEnableVertexAttribArray(attrib);
    }
};
