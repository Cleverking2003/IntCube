#include "cube.hpp"

static MeshData cube_data = {
    .vertices = {
         0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f,  0.5f, -0.5f,
         0.5f,  0.5f, -0.5f,
        // face 2
        -0.5f,  0.5f,  0.5f,
        -0.5f, -0.5f,  0.5f,
         0.5f, -0.5f,  0.5f,
         0.5f,  0.5f,  0.5f,
        // face 3
        -0.5f,  0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f,  0.5f,
        -0.5f,  0.5f,  0.5f,
        // face 4
         0.5f, -0.5f,  0.5f,
         0.5f, -0.5f, -0.5f,
         0.5f,  0.5f, -0.5f,
         0.5f,  0.5f,  0.5f,
        // face 5
        -0.5f, -0.5f,  0.5f,
        -0.5f, -0.5f, -0.5f,
         0.5f, -0.5f, -0.5f,
         0.5f, -0.5f,  0.5f,
        // face 6
         0.5f,  0.5f, -0.5f,
        -0.5f,  0.5f, -0.5f,
        -0.5f,  0.5f,  0.5f,
         0.5f,  0.5f,  0.5f,

        // stickers
        // face 1
         0.45f, -0.45f, -0.501f,
        -0.45f, -0.45f, -0.501f,
        -0.45f,  0.45f, -0.501f,
         0.45f,  0.45f, -0.501f,
        // face 2
        -0.45f,  0.45f,  0.501f,
        -0.45f, -0.45f,  0.501f,
         0.45f, -0.45f,  0.501f,
         0.45f,  0.45f,  0.501f,
        // face 3
        -0.501f,  0.45f, -0.45f,
        -0.501f, -0.45f, -0.45f,
        -0.501f, -0.45f,  0.45f,
        -0.501f,  0.45f,  0.45f,
        // face 4
         0.501f, -0.45f,  0.45f,
         0.501f, -0.45f, -0.45f,
         0.501f,  0.45f, -0.45f,
         0.501f,  0.45f,  0.45f,
        // face 5
        -0.45f, -0.501f,  0.45f,
        -0.45f, -0.501f, -0.45f,
         0.45f, -0.501f, -0.45f,
         0.45f, -0.501f,  0.45f,
        // face 6
         0.45f,  0.501f, -0.45f,
        -0.45f,  0.501f, -0.45f,
        -0.45f,  0.501f,  0.45f,
         0.45f,  0.501f,  0.45f,
    },
    .face_data = {
        {
            .indices = {
                // face 1
                0, 1, 2,
                0, 2, 3,
                // face 2
                4, 5, 6,
                4, 6, 7,
                // face 3
                8, 9, 10,
                8, 10, 11,
                // face 4
                12, 13, 14,
                12, 14, 15,
                // face 5
                16, 17, 18,
                16, 18, 19,
                // face 6
                20, 21, 22,
                20, 22, 23,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                // stickers
                // face 1
                24, 25, 26,
                24, 26, 27,
            },
            .color = glm::vec3(1.0, 0.5, 0.0),
        },
        {
            .indices = {
                // face 2
                28, 29, 30,
                28, 30, 31,
            },
            .color = glm::vec3(1.0, 0.0, 0.0),
        },
        {
            .indices = {
                // face 3
                32, 33, 34,
                32, 34, 35,
            },
            .color = glm::vec3(0.0, 1.0, 0.0),
        },
        {
            .indices = {
                // face 4
                36, 37, 38,
                36, 38, 39,
            },
            .color = glm::vec3(0.0, 0.0, 1.0),
        },
        {
            .indices = {
                // face 5
                40, 41, 42,
                40, 42, 43,
            },
            .color = glm::vec3(1.0, 1.0, 0.0),
        },
        {
            .indices = {
                // face 6
                44, 45, 46,
                44, 46, 47,
            },
            .color = glm::vec3(1.0),
        },
    }
};

static MeshData transform_cube_mesh(glm::mat4 transform) {
    MeshData data = cube_data;
    for (int i = 0; i < 48; i++) {
        auto ptr = reinterpret_cast<glm::vec3*>(data.vertices.data() + i * 3);
        *ptr = transform * glm::vec4(*ptr, 1.0);
    }
    return data;
}

Cube::Cube(int size) : Puzzle(), m_size(size) {
    m_axes = {
        std::pair { glm::vec3(1.0, 0.0, 0.0), 4 },
        std::pair { glm::vec3(0.0, 1.0, 0.0), 4 },
        std::pair { glm::vec3(0.0, 0.0, 1.0), 4 },
    };

    fill_cubies();
    fill_moves();
} 

void Cube::fill_cubies() {
    for (int x = 0; x < m_size; x++) {
        for (int y = 0; y < m_size; y++) {
            for (int z = 0; z < m_size; z++) {
                if ((x != 0 && x != m_size - 1)
                    && (y != 0 && y != m_size - 1)
                    && (z != 0 && z != m_size - 1))
                        continue;
                auto transform = glm::mat4(1.0);
                transform = glm::translate(transform, glm::vec3(x, y, z) - m_size/2.0f + 0.5f);
                transform = glm::scale(glm::mat4(1.0), glm::vec3(1.0 / m_size)) * transform;
                auto data = transform_cube_mesh(transform);
                auto pos = glm::vec3(x, y, z) - float(m_size/2);
                if (m_size % 2 == 0) {
                    for (int i = 0; i < 3; i++)
                        if (pos[i] >= 0)
                            pos[i]++;
                }

                if (pos.x != m_size / 2) {
                    data.face_data[4].color = glm::vec3(0.0);
                }
                if (pos.x != -m_size / 2) {
                    data.face_data[3].color = glm::vec3(0.0);
                }
                if (pos.y != m_size / 2) {
                    data.face_data[6].color = glm::vec3(0.0);
                }
                if (pos.y != -m_size / 2) {
                    data.face_data[5].color = glm::vec3(0.0);
                }
                if (pos.z != m_size / 2) {
                    data.face_data[2].color = glm::vec3(0.0);
                }
                if (pos.z != -m_size / 2) {
                    data.face_data[1].color = glm::vec3(0.0);
                }

                m_cubies.emplace_back(data, pos, pos);
            }
        }
    }
}

void Cube::fill_moves() {
    for (int i = 0; i < 3; i++) {
        for (int j = -m_size/2; j <= m_size/2; j++) {
            if (m_size % 2 == 0 && j == 0) continue;
            auto [axis, angle] = m_axes[i];
            if (j > 0) axis *= -1;
            auto mat = glm::rotate(glm::mat4(1.0), glm::two_pi<float>() / angle, axis);
            m_moves[{ i, j }] = mat;
        }
    }
}

void Cube::reset() {
    for (auto& [c, pos, orig_pos] : m_cubies) {
        c.model_mat = glm::mat4(1.0);
        pos = orig_pos;
    }
}
