#pragma once

#define GL_GLEXT_PROTOTYPES
#include <SFML/OpenGL.hpp>
#include <glm/glm.hpp>

#include "shader.hpp"

struct VertexAttrib {
    GLint size;
    GLenum type;
    GLboolean normalized = GL_FALSE;
    GLsizei stride;
    void* pointer = nullptr;
};

class Model {
public:
    explicit Model(void* vertexData, void* indexData, int vertexSize, int indexSize, int count);
    Model(Model&&);

    void setVertexData(void* vertexData, int vertexSize);
    void addAttribute(int index, VertexAttrib attrib);
    void addIntAttribute(int index, VertexAttrib attrib);
    void draw();
    ~Model();

private:
    GLuint m_vbo;
    GLuint m_vao;
    GLuint m_ebo;

    int m_vertexSize;
    int m_indexSize;
    int m_count;
};
