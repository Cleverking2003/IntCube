#pragma once

#include <array>
#include <map>
#include <vector>
#include "mesh.hpp"

#include <glm/gtc/matrix_transform.hpp>

template<int N>
class Puzzle {
public:
    Puzzle() {

        if (!m_shader)
            m_shader = std::make_shared<ShaderProgram>("piece_vertex.glsl", "piece_fragment.glsl");

        fill_cubies();
        fill_moves();
    }

    Puzzle(char** vertexShader, char** fragmentShader) {

        if (!m_shader)
            m_shader = std::make_shared<ShaderProgram>(vertexShader, fragmentShader);
        
        fill_cubies();
        fill_moves();
    }

    void draw(glm::mat4& view, glm::mat4& proj) {
        for (auto& [cubie, pos, orig_pos] : m_cubies) {
            cubie.draw(view, proj);
        }
    }

    void execute_move(int axis, int coord, bool inverse = false) {
        if (is_in_animation() || m_moves.find({ axis, coord }) == m_moves.end()) return;
        auto coord_mat = m_moves[{ axis, coord }];
        if (inverse)
            coord_mat = glm::inverse(coord_mat);

        for (auto& [c, pos, orig_pos] : m_cubies) {

            if (glm::round(pos[axis]) != coord) continue;

            pos = coord_mat * pos;

            auto [axis_vec, axis_part] = m_axes[axis];
            if (coord > 0) axis_vec *= -1;
            if (inverse) axis_vec *= -1;
            c.play_rotate_animation(axis_vec, glm::two_pi<float>() / axis_part, 250ms);
        }
    }

    bool is_in_animation() {
        for (auto& [c, pos, orig_pos] : m_cubies) {
            if (c.in_animation) return true;
        }
        return false;
    }

    std::array<std::pair<glm::vec3, int>, N> m_axes;
    std::map<std::pair<int, int>, glm::mat<N, N, float>> m_moves;

protected:
    virtual void fill_cubies() {};
    virtual void fill_moves() {};

    std::vector<std::tuple<Mesh, glm::vec<N, float>, glm::vec<N, float>>> m_cubies;
    std::shared_ptr<ShaderProgram> m_shader = nullptr;
};
