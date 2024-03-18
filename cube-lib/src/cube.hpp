#pragma once

#include "puzzle.hpp"
#include <cstring>

static const struct PieceVertex {
    glm::vec3 pos, color;
} vertices[] = {
    // face 1
    { .pos = glm::vec3(-0.5f, -0.5f, -0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.5f, -0.5f, -0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3(-0.5f,  0.5f, -0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.5f,  0.5f, -0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    // face 2
    { .pos = glm::vec3(-0.5f, -0.5f,  0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.5f, -0.5f,  0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3(-0.5f,  0.5f,  0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.5f,  0.5f,  0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    // face 3
    { .pos = glm::vec3(-0.5f, -0.5f, -0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3(-0.5f, -0.5f,  0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3(-0.5f,  0.5f, -0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3(-0.5f,  0.5f,  0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    // face 4
    { .pos = glm::vec3( 0.5f, -0.5f, -0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.5f, -0.5f,  0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.5f,  0.5f, -0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.5f,  0.5f,  0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    // face 5
    { .pos = glm::vec3(-0.5f, -0.5f, -0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.5f, -0.5f, -0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3(-0.5f, -0.5f,  0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.5f, -0.5f,  0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    // face 6
    { .pos = glm::vec3(-0.5f,  0.5f, -0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.5f,  0.5f, -0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3(-0.5f,  0.5f,  0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.5f,  0.5f,  0.5f), .color = glm::vec3(0.0, 0.0, 0.0) },

    // stickers
    // face 1
    { .pos = glm::vec3(-0.45f, -0.45f, -0.501f), .color = glm::vec3(1.0, 0.5, 0.0) },
    { .pos = glm::vec3( 0.45f, -0.45f, -0.501f), .color = glm::vec3(1.0, 0.5, 0.0) },
    { .pos = glm::vec3(-0.45f,  0.45f, -0.501f), .color = glm::vec3(1.0, 0.5, 0.0) },
    { .pos = glm::vec3( 0.45f,  0.45f, -0.501f), .color = glm::vec3(1.0, 0.5, 0.0) },
    // face 2
    { .pos = glm::vec3(-0.45f, -0.45f,  0.501f), .color = glm::vec3(1.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.45f, -0.45f,  0.501f), .color = glm::vec3(1.0, 0.0, 0.0) },
    { .pos = glm::vec3(-0.45f,  0.45f,  0.501f), .color = glm::vec3(1.0, 0.0, 0.0) },
    { .pos = glm::vec3( 0.45f,  0.45f,  0.501f), .color = glm::vec3(1.0, 0.0, 0.0) },
    // face 3
    { .pos = glm::vec3(-0.501f, -0.45f, -0.45f), .color = glm::vec3(0.0, 1.0, 0.0) },
    { .pos = glm::vec3(-0.501f, -0.45f,  0.45f), .color = glm::vec3(0.0, 1.0, 0.0) },
    { .pos = glm::vec3(-0.501f,  0.45f, -0.45f), .color = glm::vec3(0.0, 1.0, 0.0) },
    { .pos = glm::vec3(-0.501f,  0.45f,  0.45f), .color = glm::vec3(0.0, 1.0, 0.0) },
    // face 4
    { .pos = glm::vec3( 0.501f, -0.45f, -0.45f), .color = glm::vec3(0.0, 0.0, 1.0) },
    { .pos = glm::vec3( 0.501f, -0.45f,  0.45f), .color = glm::vec3(0.0, 0.0, 1.0) },
    { .pos = glm::vec3( 0.501f,  0.45f, -0.45f), .color = glm::vec3(0.0, 0.0, 1.0) },
    { .pos = glm::vec3( 0.501f,  0.45f,  0.45f), .color = glm::vec3(0.0, 0.0, 1.0) },
    // face 5
    { .pos = glm::vec3(-0.45f, -0.501f, -0.45f), .color = glm::vec3(1.0, 1.0, 0.0) },
    { .pos = glm::vec3( 0.45f, -0.501f, -0.45f), .color = glm::vec3(1.0, 1.0, 0.0) },
    { .pos = glm::vec3(-0.45f, -0.501f,  0.45f), .color = glm::vec3(1.0, 1.0, 0.0) },
    { .pos = glm::vec3( 0.45f, -0.501f,  0.45f), .color = glm::vec3(1.0, 1.0, 0.0) },
    // face 6
    { .pos = glm::vec3(-0.45f,  0.501f, -0.45f), .color = glm::vec3(1.0, 1.0, 1.0) },
    { .pos = glm::vec3( 0.45f,  0.501f, -0.45f), .color = glm::vec3(1.0, 1.0, 1.0) },
    { .pos = glm::vec3(-0.45f,  0.501f,  0.45f), .color = glm::vec3(1.0, 1.0, 1.0) },
    { .pos = glm::vec3( 0.45f,  0.501f,  0.45f), .color = glm::vec3(1.0, 1.0, 1.0) },
};

static unsigned int indices[] = {
    // face 1
    0, 1, 2,
    1, 2, 3,
    // face 2
    4, 5, 6,
    5, 6, 7,
    // face 3
    8, 9, 10,
    9, 10, 11,
    // face 4
    12, 13, 14,
    13, 14, 15,
    // face 5
    16, 17, 18,
    17, 18, 19,
    // face 6
    20, 21, 22,
    21, 22, 23,

    // stickers
    // face 1
    24, 25, 26,
    25, 26, 27,
    // face 2
    28, 29, 30,
    29, 30, 31,
    // face 3
    32, 33, 34,
    33, 34, 35,
    // face 4
    36, 37, 38,
    37, 38, 39,
    // face 5
    40, 41, 42,
    41, 42, 43,
    // face 6
    44, 45, 46,
    45, 46, 47,
};

static Model* transform_cube_mesh(glm::mat4 transform) {
    PieceVertex* new_vertices = new PieceVertex[48];
    std::memcpy(new_vertices, vertices, sizeof(vertices));
    for (int i = 0; i < 48; i++) {
        new_vertices[i].pos = transform * glm::vec4(new_vertices[i].pos, 1.0);
    }
    auto mesh = new Model(new_vertices, indices, sizeof(vertices), sizeof(indices), 72);
    delete new_vertices;
    return mesh;
}

template<int N>
class Cube : public Puzzle<3> {
public:
    Cube() : Puzzle() {
        m_axes = std::array<std::pair<glm::vec3, int>, 3> {
            std::pair { glm::vec3(1.0, 0.0, 0.0), 4 },
            std::pair { glm::vec3(0.0, 1.0, 0.0), 4 },
            std::pair { glm::vec3(0.0, 0.0, 1.0), 4 },
        };

        fill_cubies();
        fill_moves();
    } 

protected:
    virtual void fill_cubies() override {
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
              for (int z = 0; z < N; z++) {
                    if ((x != 0 && x != N - 1)
                        && (y != 0 && y != N - 1)
                        && (z != 0 && z != N - 1))
                            continue;
                    auto transform = glm::mat4(1.0);
                    transform = glm::translate(transform, glm::vec3(x, y, z) - N/2.0f + 0.5f);
                    transform = glm::scale(glm::mat4(1.0), glm::vec3(1.0 / N)) * transform;
                    auto mesh = transform_cube_mesh(transform);
                    auto pos = glm::vec3(x, y, z) - float(N/2);
                    if (N % 2 == 0) {
                        for (int i = 0; i < 3; i++)
                            if (pos[i] >= 0)
                                pos[i]++;
                    }
                    m_cubies.push_back({ Piece(&m_program, mesh), pos });
                }
            }
        }
    }

    virtual void fill_moves() override {
        for (int i = 0; i < 3; i++) {
            for (int j = -N/2; j <= N/2; j++) {
                if (N % 2 == 0 && j == 0) continue;
                auto [axis, angle] = m_axes[i];
                if (j > 0) axis *= -1;
                auto mat = glm::rotate(glm::mat4(1.0), glm::two_pi<float>() / angle, axis);
                m_moves[{ i, j }] = mat;
            }
        }
    }
};
