#include "mesh.hpp"

#ifdef __ANDROID__
#include <android/log.h>
#endif

#include <glm/gtc/matrix_transform.hpp>

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
//#ifdef __ANDROID__
//    while ((err = glGetError()) != GL_NO_ERROR)
//        __android_log_print(ANDROID_LOG_ERROR, "gl", "OPENGL ERROR %x\n", err);
//#endif
    m_vbo->bind(GL_ARRAY_BUFFER);
    m_vao->bind();
//#ifdef __ANDROID__
//    while ((err = glGetError()) != GL_NO_ERROR)
//        __android_log_print(ANDROID_LOG_ERROR, "gl_draw", "OPENGL ERROR %x\n", err);
//#endif
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
