#pragma once

#include <glm/glm.hpp>
#include <memory>
#include <vector>

#include "gl_helpers.hpp"

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
    explicit Mesh(MeshData& data);

    void draw(glm::mat4, glm::mat4);
    glm::mat4 model_mat { 1.0 };

private:
    std::shared_ptr<Buffer> m_vbo;
    std::vector<std::tuple<std::shared_ptr<Buffer>, glm::vec3, int>> m_faces;
    std::shared_ptr<VertexArray> m_vao;

    MeshData m_data;
    int m_model_loc, m_view_loc, m_proj_loc, m_color_loc;
};
