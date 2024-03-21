#pragma once
#define GL_GLEXT_PROTOTYPES
#include "mesh.hpp"

#include <array>
#include <map>
#include <vector>

class Piece {
public:
    explicit Piece(ShaderProgram*, Model*);
    Piece(Piece&&) = default;
    void draw(glm::mat4& view, glm::mat4& proj);

    glm::mat4 m_model_mat { 1.0 };
private:
    Model* m_model;
    ShaderProgram* m_shader_program;
};
