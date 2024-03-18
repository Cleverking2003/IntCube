#pragma once

#include <map>
#include <vector>
#include "piece.hpp"

#include <glm/gtc/matrix_transform.hpp>

template<int N>
class Puzzle {
public:
    Puzzle() 
        : m_program("piece_vertex.glsl", "piece_fragment.glsl") {

        fill_cubies();
        fill_moves();
    }

    void draw(glm::mat4& view, glm::mat4& proj) {
        for (auto& [cubie, pos] : m_cubies) {
            cubie.draw(view, proj);
        }
    }

    void execute_move(int axis, int coord, bool inverse = false) {
        if (m_moves.find({ axis, coord }) == m_moves.end()) return;
        auto coord_mat = m_moves[{ axis, coord }];
        if (inverse)
            coord_mat = glm::inverse(coord_mat);

        for (auto& [c, pos] : m_cubies) {

            if (glm::round(pos[axis]) != coord) continue;

            pos = coord_mat * pos;

            auto [axis_vec, axis_part] = m_axes[axis];
            if (coord > 0) axis_vec *= -1;
            if (inverse) axis_vec *= -1;
            c.m_model_mat = glm::rotate(glm::mat4(1.0), glm::two_pi<float>() / axis_part, axis_vec) * c.m_model_mat;
        }
    }

    std::array<std::pair<glm::vec3, int>, N> m_axes;
    std::map<std::pair<int, int>, glm::mat<N, N, float>> m_moves;

protected:
    virtual void fill_cubies() {};
    virtual void fill_moves() {};

    std::vector<std::pair<Piece, glm::vec<N, float>>> m_cubies;
    ShaderProgram m_program;
};
