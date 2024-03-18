#pragma once
#define GL_GLEXT_PROTOTYPES
#include "mesh.hpp"

#include <array>
#include <map>
#include <vector>

class Cube;

class Cubie {
public:
    explicit Cubie(ShaderProgram*, glm::ivec3 vector_pos, std::array<int, 7>);
    Cubie(Cubie&&);
    void draw(glm::mat4& view, glm::mat4& proj, Cube&);

    glm::ivec3 m_vector_position;
    glm::ivec3 m_orig_position;
    glm::mat4 m_rot { 1.0 };
private:
    Model m_model;
    ShaderProgram* m_shader_program;
    std::array<int, 7> m_colors { 0 };
};
