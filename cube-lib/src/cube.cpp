#include "cube.hpp"

#include <glm/gtc/type_ptr.hpp>
#include <glm/gtx/euler_angles.hpp>

#include <iostream>
#include <tuple>

Cube::Cube() 
    : m_program("cube_vertex.glsl", "cube_fragment.glsl") {

    for (int i = 0; i < 8; i++) {
        int x = (i & 4) ? 1 : -1;
        int y = (i & 2) ? 1 : -1;
        int z = (i & 1) ? 1 : -1;
        std::array<int, 7> colors = { 6, 6, 6, 6, 6, 6, 6 };

        if (y == 1)
            colors[5] = 5;
        else
            colors[4] = 4;
        if (x == -1)
            colors[2] = 2;
        else
            colors[3] = 3;
        if (z == 1)
            colors[1] = 1;
        else
            colors[0] = 0;

        m_cubies.push_back(Cubie(&m_program, glm::ivec3(x, y, z), colors));
    }
}

static glm::imat3x3 moves[] = {
        {
            { 0, 0, 1 },
            { 0, 1, 0 },
            { -1, 0, 0 },
        },
        {
            { 0, 0, -1 },
            { 0, 1, 0 },
            { 1, 0, 0 },
        },
        {
            { 1, 0, 0 },
            { 0, 0, 1 },
            { 0, -1, 0 },
        },
        {
            { 1, 0, 0 },
            { 0, 0, -1 },
            { 0, 1, 0 },
        },
        {
            { 0, -1, 0 },
            { 1, 0, 0 },
            { 0, 0, 1 }
        },
        {
            { 0, 1, 0 },
            { -1, 0, 0 },
            { 0, 0, 1 }
        },
};

void Cube::draw(glm::mat4& view, glm::mat4& proj) {
    auto mod = glm::mat4(1.0);
    mod = glm::translate(mod, m_position);
    auto new_view = view * mod;
    for (auto& cubie : m_cubies)
        cubie.draw(new_view, proj, *this);
}

void Cube::execute_move(int move_num) {
    for (auto& c : m_cubies) {
        if (move_num == 0) {
            if (c.m_vector_position.y != 1) continue;
        }
        else if (move_num == 1) {
            if (c.m_vector_position.y != -1) continue;
        }
        else if (move_num == 2) {
            if (c.m_vector_position.x != -1) continue;
        }
        else if (move_num == 3) {
            if (c.m_vector_position.x != 1) continue;
        }
        else if (move_num == 4) {
            if (c.m_vector_position.z != 1) continue;
        }
        else if (move_num == 5) {
            if (c.m_vector_position.z != -1) continue;
        }
        c.m_vector_position = moves[move_num] * c.m_vector_position;
        c.m_rot = glm::mat4(moves[move_num]) * c.m_rot;
    }
}
