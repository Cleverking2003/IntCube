#pragma once

#include <array>
#include <iostream>
#include <map>
#include <vector>
#include "mesh.hpp"

#include <glm/gtc/matrix_transform.hpp>
#define GLM_ENABLE_EXPERIMENTAL
#include <glm/gtx/vector_angle.hpp>

template<int N>
class Puzzle {
public:
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
        if (is_in_animation()) return;
        auto coord_mat = m_moves[axis];
        if (inverse)
            coord_mat = glm::inverse(coord_mat);

        auto [axis_vec, axis_part] = m_axes[axis];
        if (inverse) axis_vec *= -1;

        for (auto& [c, pos, orig_pos] : m_cubies) {

            if (pos[axis] != coord) continue;

            pos = coord_mat * pos;
            pos = glm::vec3(glm::round(pos.x), glm::round(pos.y), glm::round(pos.z));

            c.play_rotate_animation(axis_vec, glm::two_pi<float>() / axis_part, 100ms);
        }
    }

    bool is_in_animation() {
        for (auto& [c, pos, orig_pos] : m_cubies) {
            if (c.in_animation) return true;
        }
        return false;
    }

    bool intersect(glm::vec3 origin, glm::vec3 dir, glm::vec<N, float>& closest_piece_coord, glm::vec3& closest_cross) {
        float dist = INFINITY;
        bool res = false;

        for (auto& [piece, pos, orig] : m_cubies) {
            glm::vec3 cross;
            float new_dist;
            if (piece.ray_intersect(origin, dir, cross, new_dist)) {
                res = true;
                if (new_dist < dist) {
                    dist = new_dist;
                    closest_piece_coord = pos;
                    closest_cross = cross;
                }
            }
        }
        return res;
    }

    void execute_move_between_points(glm::vec3 o1, glm::vec3 d1, glm::vec3 o2, glm::vec3 d2) {
        glm::vec3 p1, p2;
        glm::vec<N, float> c1, c2;
        if (!intersect(o1, d1, c1, p1) || !intersect(o2, d2, c2, p2)) return;

        if (c1 == c2) return;

        auto delta_c = c2 - c1;
        int index = -1;
        float max_dist = 0;
        for (int i = 0; i < N; i++) {
            if (delta_c[i] != 0) continue;
            auto r1 = glm::vec3(0.0);
            auto r2 = p1;
            auto n = glm::cross(std::get<0>(m_axes[i]), p2 - p1);
            float dist = glm::abs(glm::dot(n, (r2 - r1))) / n.length();
            if (dist > max_dist) {
                max_dist = dist;
                index = i;
            }
        }
        if (index == -1) return;
        auto angle = glm::orientedAngle(glm::normalize(p1), glm::normalize(p2), std::get<0>(m_axes[index]));
        execute_move(index, c1[index], angle < 0);
    }

    void rotate_around_axis(int axis, bool inverse) {
        if (is_in_animation()) return;
        auto mat = m_moves[axis];
        auto [axis_vec, axis_part] = m_axes[axis];
        if (inverse) axis_vec *= -1;
        if (inverse)
            mat = glm::inverse(mat);
        for (auto& [c, pos, orig_pos] : m_cubies) {
            pos = mat * pos;
            pos = glm::vec3(glm::round(pos.x), glm::round(pos.y), glm::round(pos.z));
            c.play_rotate_animation(axis_vec, glm::half_pi<float>(), 200ms);
        }
    }

    void apply_move(int axis, int coord, bool inverse) {
//        if (is_in_animation()) return;
        auto coord_mat = m_moves[axis];
        if (inverse)
            coord_mat = glm::inverse(coord_mat);

        auto [axis_vec, axis_part] = m_axes[axis];
        if (inverse) axis_vec *= -1;

        for (auto& [c, pos, orig_pos] : m_cubies) {

            if (pos[axis] != coord) continue;

            pos = coord_mat * pos;
            pos = glm::vec3(glm::round(pos.x), glm::round(pos.y), glm::round(pos.z));

            c.model_mat = glm::rotate(glm::mat4(1.0), glm::half_pi<float>(), axis_vec) * c.model_mat;
        }
    }

    void apply_rot(int axis, bool inverse) {
//        if (is_in_animation()) return;
        auto mat = m_moves[axis];
        auto [axis_vec, axis_part] = m_axes[axis];
        if (inverse) axis_vec *= -1;
        if (inverse)
            mat = glm::inverse(mat);
        for (auto& [c, pos, orig_pos] : m_cubies) {
            pos = mat * pos;
            pos = glm::vec3(glm::round(pos.x), glm::round(pos.y), glm::round(pos.z));
            c.model_mat = glm::rotate(glm::mat4(1.0), glm::half_pi<float>(), axis_vec) * c.model_mat;
        }
    }

    std::array<std::pair<glm::vec3, int>, N> m_axes;
    std::array<glm::mat<N, N, float>, N> m_moves;

protected:
    virtual void fill_cubies() {};
    virtual void fill_moves() {};

    std::vector<std::tuple<Mesh, glm::vec<N, float>, glm::vec<N, float>>> m_cubies;
    std::shared_ptr<ShaderProgram> m_shader = nullptr;
};
