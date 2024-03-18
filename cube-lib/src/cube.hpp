#pragma once

#include <map>
#include <vector>
#include "cubie.hpp"

class Cube {
public:
    Cube();
    void draw(glm::mat4& view, glm::mat4& proj);
    void execute_move(int move);
    
    void setPosition(glm::vec3 pos) {
        m_position = pos;
    }

    glm::ivec3 m_possible_rots { 4, 4, 4 };

private:
    std::vector<Cubie> m_cubies;
    ShaderProgram m_program;
    glm::vec3 m_position { 0.0 };
};
