#include <glad/gl.h>
#include <glm/gtx/euler_angles.hpp>
#include "scene.hpp"

#include <iostream>

static const float quadVertices[] = {
    -1.0f,  1.0f,  0.0f, 1.0f,
    -1.0f, -1.0f,  0.0f, 0.0f,
    1.0f, -1.0f,  1.0f, 0.0f,
    1.0f,  1.0f,  1.0f, 1.0f
};

static GLuint quadIndices[] = {
    0, 1, 2,
    0, 2, 3,
};

Scene::Scene(int width, int height)
    : m_fb_shader("simple_vertex.glsl", "simple_fragment.glsl"),
    m_fb_mesh((void*)quadVertices, quadIndices, sizeof(quadVertices), sizeof(quadIndices), 6),
    m_view(glm::translate(glm::mat4(1.0), glm::vec3(0.0, 0.0, -5.0))),
    m_proj(glm::perspective(glm::radians(45.0f), (float)width/(float)height, 0.1f, 100.0f)),
    m_width(width), m_height(height) {
    glEnable(GL_DEPTH_TEST);

    glGenFramebuffers(1, &m_fbo);
    glBindFramebuffer(GL_FRAMEBUFFER, m_fbo);  

    glGenTextures(1, &m_texture);
    glBindTexture(GL_TEXTURE_2D, m_texture);
    
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, m_width, m_height, 0, GL_RGBA, GL_UNSIGNED_BYTE, NULL);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);  

    glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, m_texture, 0); 

    glGenRenderbuffers(1, &m_rbo);
    glBindRenderbuffer(GL_RENDERBUFFER, m_rbo); 
    glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, m_width, m_height);  
    glBindRenderbuffer(GL_RENDERBUFFER, 0);
    glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, m_rbo);

    if(glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE)
        std::cout << "yay\n";
    glBindFramebuffer(GL_FRAMEBUFFER, 0);

    std::cout << quadVertices[1] << '\n';

    m_fb_mesh.setVertexData((void*)quadVertices, sizeof(quadVertices));

    m_fb_shader.setInt("screenTexture", 0);
    m_fb_mesh.addAttribute(0, { .size = 2, .type = GL_FLOAT, .stride = 4 * sizeof(float), .pointer = nullptr });
    m_fb_mesh.addAttribute(1, { .size = 2, .type = GL_FLOAT, .stride = 4 * sizeof(float), .pointer = (void*)(2 * sizeof(float)) });
}

void Scene::render() {
    glBindFramebuffer(GL_FRAMEBUFFER, m_fbo);  
    glEnable(GL_DEPTH_TEST);
    glClearColor(0, 0, 0, 0);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    auto new_view = m_view * m_rot;
    m_cube.draw(new_view, m_proj);

    glBindFramebuffer(GL_FRAMEBUFFER, 0);
    glClearColor(0, 0.5, 0.5, 1);
    glClear(GL_COLOR_BUFFER_BIT);
    glDisable(GL_DEPTH_TEST);
    glActiveTexture(0);
    glBindTexture(GL_TEXTURE_2D, m_texture);
    m_fb_shader.use();
    m_fb_mesh.draw();
}

void Scene::handleMouseMovement(glm::vec2 delta) {
    auto angles = delta / glm::vec2(m_width, m_height) * 360.0f;
    m_rot = glm::yawPitchRoll(glm::radians(angles.x), glm::radians(angles.y), 0.0f) * m_rot;
}

void Scene::handleKeyPress(SceneKey key, bool inverse) {
    switch (key) {
    case SceneKey::L:
        m_cube.execute_move(0, -1, inverse);
        break;
    case SceneKey::R:
        m_cube.execute_move(0, 1, inverse);
        break;
    case SceneKey::D:
        m_cube.execute_move(1, -1, inverse);
        break;
    case SceneKey::U:
        m_cube.execute_move(1, 1, inverse);
        break;
    case SceneKey::B:
        m_cube.execute_move(2, -1, inverse);
        break;
    case SceneKey::F:
        m_cube.execute_move(2, 1, inverse);
        break;
    case SceneKey::M:
        m_cube.execute_move(0, 0, inverse);
        break;
    case SceneKey::E:
        m_cube.execute_move(1, 0, inverse);
        break;
    case SceneKey::S:
        m_cube.execute_move(2, 0, !inverse);
        break;
    }
}
