#include "cube.hpp"
#include "piece.hpp"
#include <glm/glm.hpp>
#include <glm/gtc/type_ptr.hpp>
#include <glm/gtx/euler_angles.hpp>
#include <iostream>

Piece::Piece(ShaderProgram* program, Model* model) 
        : m_shader_program(program), 
        m_model(model) {
    // vertex attribs
    VertexAttrib coord = { .size = 3, .type = GL_FLOAT, .stride = 6 * sizeof(float)};
    VertexAttrib col = { .size = 3, .type = GL_FLOAT, .stride = 6 * sizeof(float), .pointer = (void*)(3*sizeof(float))};
    m_model->addAttribute(0, coord);
    m_model->addAttribute(1, col);
}

void Piece::draw(glm::mat4& view, glm::mat4& proj) {
    m_shader_program->setUniformMatrix4fv("model", m_model_mat);
    m_shader_program->setUniformMatrix4fv("view", view);
    m_shader_program->setUniformMatrix4fv("projection", proj);
    m_shader_program->use();
    m_model->draw();
}
