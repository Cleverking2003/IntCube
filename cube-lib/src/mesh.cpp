#include "mesh.hpp"

static std::shared_ptr<ShaderProgram> m_shader = nullptr;

Mesh::Mesh(MeshData& data) 
    : m_vbo(std::make_shared<Buffer>()),
    m_vao(std::make_shared<VertexArray>()),
    m_data(data) {

    if (!m_shader)
        m_shader = std::make_shared<ShaderProgram>("piece_vertex.glsl", "piece_fragment.glsl");

    m_vbo->setData(GL_ARRAY_BUFFER, data.vertices.size() * sizeof(float), data.vertices.data(), GL_STATIC_DRAW);
    m_vao->setAttribute(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), 0);

    for (auto& face : data.face_data) {
        auto ebo = std::make_shared<Buffer>();
        ebo->setData(GL_ELEMENT_ARRAY_BUFFER, face.indices.size() * sizeof(int), face.indices.data(), GL_STATIC_DRAW);
        m_faces.emplace_back(ebo, face.color, face.indices.size());
    }
}

void Mesh::draw(glm::mat4 view, glm::mat4 proj) {
    m_shader->use();
    m_shader->setUniformMatrix4fv("model", model_mat);
    m_shader->setUniformMatrix4fv("view", view);
    m_shader->setUniformMatrix4fv("projection", proj);
    m_vbo->bind(GL_ARRAY_BUFFER);
    m_vao->bind();
    for (auto& [ebo, color, count] : m_faces) {
        m_shader->setVec3("color", color);
        ebo->bind(GL_ELEMENT_ARRAY_BUFFER);
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, nullptr);
    }
}
