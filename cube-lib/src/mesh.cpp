#include "mesh.hpp"

#ifdef __ANDROID__
#include <android/log.h>
#endif

#include <glm/gtc/matrix_transform.hpp>
#define GLM_ENABLE_EXPERIMENTAL
#include <glm/gtx/intersect.hpp>

Mesh::Mesh(MeshData& data, std::shared_ptr<ShaderProgram> program) 
    : m_vbo(std::make_shared<Buffer>()),
    m_vao(std::make_shared<VertexArray>()),
    m_data(data),
    m_shader(program) {

    m_vbo->setData(GL_ARRAY_BUFFER, data.vertices.size() * sizeof(float), data.vertices.data(), GL_STATIC_DRAW);
    m_vao->setAttribute(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), 0);

    for (auto& face : data.face_data) {
        auto ebo = std::make_shared<Buffer>();
        ebo->setData(GL_ELEMENT_ARRAY_BUFFER, face.indices.size() * sizeof(int), face.indices.data(), GL_STATIC_DRAW);
        m_faces.emplace_back(ebo, face.color, face.indices.size());
    }
}

void Mesh::draw(glm::mat4 view, glm::mat4 proj) {
    if (in_animation) {
        auto elapsed_time = std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::high_resolution_clock::now() - m_start_time);
        if (elapsed_time >= 10ms) {
            m_start_time += 10ms;
            m_time -= 10ms;
            model_mat = m_rot_mat * model_mat;
        }
        if (m_time <= 0ms) in_animation = false;
    }

    GLenum err;
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

void Mesh::play_rotate_animation(glm::vec3 axis, float angle, std::chrono::milliseconds time) {
    in_animation = true;
    m_time = time;
    m_elapsed_time = 0ms;
    m_start_time = std::chrono::high_resolution_clock::now();
    m_rot_mat = glm::rotate(glm::mat4(1.0), angle / (time / 10ms), axis);
}

bool Mesh::ray_intersect(glm::vec3 origin, glm::vec3 dir, glm::vec3& crossing, float& dist) {
    dist = INFINITY;
    glm::vec3* vertices = reinterpret_cast<glm::vec3*>(m_data.vertices.data());
    bool res = false;

    origin = glm::inverse(model_mat) * glm::vec4(origin, 1.0);
    dir = glm::inverse(model_mat) * glm::vec4(dir, 1.0);

    for (auto face : m_data.face_data) {
        for (int i = 0; i < face.indices.size(); i += 3) {
            glm::vec3 v1 = vertices[face.indices[i + 0]];
            glm::vec3 v2 = vertices[face.indices[i + 1]];
            glm::vec3 v3 = vertices[face.indices[i + 2]];

            glm::vec2 bary_pos; 
            float new_dist;
            if (!glm::intersectRayTriangle<float>(origin, dir, v1, v2, v3, bary_pos, new_dist)) continue;
            res = true;
            if (new_dist < dist) {
                dist = new_dist;
                crossing = v1 * bary_pos.x + v2 * bary_pos.y + v3 * (1 - bary_pos.x - bary_pos.y);
                crossing = model_mat * glm::vec4(crossing, 1.0);
            }
        }
    }

    return res;
}
