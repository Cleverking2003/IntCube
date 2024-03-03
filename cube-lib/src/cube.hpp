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
    void setRotation(glm::vec3 rot) {
        m_rotation = rot;
    }

private:
    std::vector<Cubie> m_cubies;
    ShaderProgram m_program;
    glm::vec3 m_position { 0.0 };
    glm::vec3 m_rotation { 0.0 };

    std::map<CubieType, CubieType> m_state {
        { ULF, ULF },
        { UBL, UBL },
        { UFR, UFR },
        { URB, URB },
        { DFL, DFL },
        { DLB, DLB },
        { DRF, DRF },
        { DBR, DBR },
    };
};
