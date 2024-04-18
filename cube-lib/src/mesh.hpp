#pragma once

#include <chrono>
#include <glm/glm.hpp>
#include <memory>
#include <vector>

#include "gl_helpers.hpp"

using namespace std::literals::chrono_literals;

struct FaceData {
    std::vector<int> indices;
    glm::vec3 color;
};

struct MeshData {
    std::vector<float> vertices;
    std::vector<FaceData> face_data;
};

class Mesh {
public:
    explicit Mesh(MeshData& data, std::shared_ptr<ShaderProgram> program);

    void play_rotate_animation(glm::vec3 axis, float angle, std::chrono::milliseconds time);

    void draw(glm::mat4, glm::mat4);
    glm::mat4 model_mat { 1.0 };
    bool in_animation { false };

private:
    std::shared_ptr<Buffer> m_vbo;
    std::vector<std::tuple<std::shared_ptr<Buffer>, glm::vec3, int>> m_faces;
    std::shared_ptr<VertexArray> m_vao;

    MeshData m_data;
    glm::mat4 m_rot_mat;
    std::chrono::milliseconds m_elapsed_time, m_time;
    std::chrono::time_point<std::chrono::high_resolution_clock> m_start_time;
    std::shared_ptr<ShaderProgram> m_shader;
};
