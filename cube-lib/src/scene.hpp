#pragma once

#include "cube.hpp"

#include <glm/glm.hpp>
#include <queue>

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
    X,
    Y,
    Z,
    Reset,
};

extern "C" {
    int initGL();
    void initScene(int width, int height, int size);
    void render();
    void handleMouseMovement(int x, int y);
    void handleKeyPress(SceneKey key, bool inverse);
    void handleDragStart(int x, int y);
    void handleDragStop(int x, int y);
    void resize(int width, int height);
}

class Scene {
public:
    friend void ::initScene(int width, int height, int size);
    friend void ::render();
    friend void ::handleMouseMovement(int x, int y);
    friend void ::handleKeyPress(SceneKey key, bool inverse);
    ~Scene();

    void resize(int width, int height);
    void changeCube(int type);
    void render();
    void handleMouseMovement(int x, int y);
    bool handleKeyPress(SceneKey key, bool inverse);
    void apply_move(SceneKey key, bool inverse);
    void setClearColor(float r, float g, float b, float a) {
        m_clear_color = glm::vec4(r, g, b, a);
    }

    void setDistance(float z) {
        m_view = glm::translate(glm::mat4(1.0), glm::vec3(0.0, 0.0, z));
    }

    void handleDragStart(int x, int y);
    void handleDragStop(int x, int y);

    std::queue<std::pair<int, bool>> move_queue;

private:
    explicit Scene(int width, int height, int size);
    unsigned int m_fbo, m_texture, m_rbo;
    Buffer m_vbo;
    VertexArray m_vao;
    ShaderProgram* m_fb_shader;
    glm::mat4 m_view, m_proj;
    glm::mat4 m_rot { 1.0 };
    int m_width, m_height;
    bool m_redraw { true };
    bool m_in_drag { false }, m_in_cube_move { false };
    glm::vec2 m_drag_start;
    glm::vec3 m_orig_1, m_dir_1;
    glm::vec4 m_clear_color { 1.0 };

    Cube* m_cube;
};
