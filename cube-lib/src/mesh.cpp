#include <glad/gl.h>
#include "mesh.hpp"

#include <iostream>

Model::Model(void* vertexData, void* indexData, int vertexSize, int indexSize, int count) 
    : m_vertexSize(vertexSize),
    m_indexSize(indexSize),
    m_count(count) {
    glGenBuffers(1, &m_vbo);
    glGenVertexArrays(1, &m_vao);
    glGenBuffers(1, &m_ebo);

    glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
    glBufferData(GL_ARRAY_BUFFER, vertexSize, vertexData, GL_STATIC_DRAW);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ebo);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexSize, indexData, GL_STATIC_DRAW);
}

Model::Model(Model&& other) 
    : m_vertexSize(other.m_vertexSize),
    m_indexSize(other.m_indexSize),
    m_count(other.m_count) {
    glGenBuffers(1, &m_vbo);
    glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
    glBufferData(GL_ARRAY_BUFFER, m_vertexSize, nullptr, GL_STATIC_DRAW);

    glGenVertexArrays(1, &m_vao);

    glGenBuffers(1, &m_ebo);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ebo);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, m_indexSize, nullptr, GL_STATIC_DRAW);

    glBindBuffer(GL_COPY_READ_BUFFER, other.m_vbo);
    glBindBuffer(GL_COPY_WRITE_BUFFER, m_vbo);
    glCopyBufferSubData(GL_COPY_READ_BUFFER, GL_COPY_WRITE_BUFFER, 0, 0, m_vertexSize);
    glBindBuffer(GL_COPY_READ_BUFFER, other.m_ebo);
    glBindBuffer(GL_COPY_WRITE_BUFFER, m_ebo);
    glCopyBufferSubData(GL_COPY_READ_BUFFER, GL_COPY_WRITE_BUFFER, 0, 0, m_indexSize);
}

void Model::setVertexData(void* vertexData, int vertexSize) {
    glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
    glBufferData(GL_ARRAY_BUFFER, vertexSize, vertexData, GL_STATIC_DRAW);
}

void Model::addAttribute(int index, VertexAttrib attrib) {
    glBindVertexArray(m_vao);
    glVertexAttribPointer(index, attrib.size, attrib.type, attrib.normalized, attrib.stride, attrib.pointer);
    glEnableVertexAttribArray(index);
}

void Model::addIntAttribute(int index, VertexAttrib attrib) {
    glBindVertexArray(m_vao);
    glVertexAttribIPointer(index, attrib.size, attrib.type, attrib.stride, attrib.pointer);
    glEnableVertexAttribArray(index);
}

void Model::draw() {
    glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
    glBindVertexArray(m_vao);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ebo);
    glDrawElements(GL_TRIANGLES, m_count, GL_UNSIGNED_INT, nullptr);
}

Model::~Model() {
    glDeleteBuffers(1, &m_vbo);
    glDeleteBuffers(1, &m_ebo);
    glDeleteVertexArrays(1, &m_vao);
}
