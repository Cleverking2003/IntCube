#pragma once

#include "cube.hpp"

#include <glm/glm.hpp>

enum class SceneKey {
    U,
    D,
    L,
    R,
    F,
    B,
    M,
    E,
    S,
    Reset,
};

class Scene {
public:
    explicit Scene(int width, int height, int size);
    void render();
    void handleMouseMovement(glm::vec2 delta);
    void handleKeyPress(SceneKey key, bool inverse);

private:
    unsigned int m_fbo, m_texture, m_rbo;
    Buffer m_vbo;
    VertexArray m_vao;
    ShaderProgram m_fb_shader;
    glm::mat4 m_view, m_proj;
    glm::mat4 m_rot { 1.0 };
    int m_width, m_height;
    bool m_redraw { true };

    Cube* m_cube;
};
