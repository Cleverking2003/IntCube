#include "cube.hpp"

#include <glm/gtc/type_ptr.hpp>

#include <tuple>

Cube::Cube() 
    : m_program("cube_vertex.glsl", "cube_fragment.glsl") {

    for (int i = 0; i < 8; i++) {
        m_cubies.push_back(Cubie(&m_program, (CubieType)i));
    }
}

static std::map<CubieType, std::tuple<CubieType, int>> move_defs[] {
    {
        { UBL, { ULF, 0 } },
        { URB, { UBL, 0 } },
        { UFR, { URB, 0 } },
        { ULF, { UFR, 0 } },
    },
    {
        { DFL, { DLB, 0 } },
        { DLB, { DBR, 0 } },
        { DBR, { DRF, 0 } },
        { DRF, { DFL, 0 } },
    },
    {
        { DFL, { ULF, 1 } },
        { ULF, { UBL, 2 } },
        { UBL, { DLB, 1 } },
        { DLB, { DFL, 2 } },
    },
    {
        { URB, { UFR, 2 } },
        { DBR, { URB, 1 } },
        { DRF, { DBR, 2 } },
        { UFR, { DRF, 1 } },
    },
    {
        { UFR, { DRF, 2 } },
        { DRF, { DFL, 1 } },
        { DFL, { ULF, 2 } },
        { ULF, { UFR, 1 } },
    },
    {
        { URB, { UBL, 1 } },
        { UBL, { DLB, 2 } },
        { DLB, { DBR, 1 } },
        { DBR, { URB, 2 } },
    }
};

void Cube::draw(glm::mat4& view, glm::mat4& proj) {
    auto mod = glm::mat4(1.0);
    mod = glm::rotate(mod, glm::radians(m_rotation.x), glm::vec3(0.0, 1.0, 0.0));
    mod = glm::rotate(mod, glm::radians(m_rotation.y), glm::vec3(1.0, 0.0, 0.0));
    mod = glm::translate(mod, m_position);
    auto new_view = view * mod;
    for (auto& cubie : m_cubies)
        cubie.draw(new_view, proj);
}

void Cube::execute_move(int move_num) {
    auto& move = move_defs[move_num];
    for (auto& cubie : m_cubies) {
        if (move.find(cubie.m_cube_position) != move.end()) {
            auto [pos, orient] = move[cubie.m_cube_position];
            cubie.m_cube_position = pos;
            cubie.m_orientation = (cubie.m_orientation + orient) % 3;
        }
    }
}
