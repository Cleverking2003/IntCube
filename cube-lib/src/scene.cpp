#define GLM_ENABLE_EXPERIMENTAL
#include <glm/gtx/euler_angles.hpp>
#include "axis_cube.hpp"
#include "scene.hpp"

#ifdef __ANDROID__
#include <GLES3/gl3.h>
#include <jni.h>
#include <android/asset_manager_jni.h>
#include <android/log.h>
#else
#include <SFML/Window/Context.hpp>
#endif
#include <fstream>
#include <iostream>
#include <sstream>

static Scene* s_scene = nullptr;

#ifdef __ANDROID__
static AAssetManager* s_mgr;
#endif

int initGL() {
#ifdef __ANDROID__
    return 0;
#else
    return gladLoadGL(sf::Context::getFunction);
#endif
}

static char* loadStringFromFile(const char* filename) {
#ifdef __ANDROID__
    auto file = AAssetManager_open(s_mgr, filename, AASSET_MODE_BUFFER);
    auto len = AAsset_getLength64(file);
    auto buf = new char[len + 1];
    std::fill(buf, buf + len + 1, 0);
    auto res = AAsset_read(file, buf, len);
    AAsset_close(file);
    return buf;
#else
    std::ifstream file(filename);

    if (!file.is_open()) {
        std::cout << "Couldn't open the file!\n";
        return nullptr;
    }

    std::stringstream buffer;
    buffer << file.rdbuf();
    auto str = buffer.str();
    file.close();
    auto c_str = new char[str.size() + 1];
    std::copy(str.begin(), str.end() + 1, c_str);
    return c_str;
#endif
}

static char *vertex, *frag, *cube_vertex, *cube_frag;

void initScene(int width, int height, int size) {
    // if (!s_scene)
        s_scene = new Scene(width, height, size);
}

void render() {
    s_scene->render();
}

void handleDragStart(int x, int y) {
    s_scene->handleDragStart(x, y);
}

void handleMouseMovement(int x, int y) {
    s_scene->handleMouseMovement(x, y);
}

void handleDragStop(int x, int y) {
    s_scene->handleDragStop(x, y);
}

void handleKeyPress(SceneKey key, bool inverse) {
    s_scene->handleKeyPress(key, inverse);
}

void resize(int width, int height) {
    s_scene->resize(width, height);
}

static float quadVertices[] = {
    -1.0f,  1.0f,  0.0f, 1.0f,
    -1.0f, -1.0f,  0.0f, 0.0f,
    1.0f, -1.0f,  1.0f, 0.0f,
    -1.0f,  1.0f,  0.0f, 1.0f,
    1.0f, -1.0f,  1.0f, 0.0f,
    1.0f,  1.0f,  1.0f, 1.0f
};

Scene::Scene(int width, int height, int size)
    : m_view(glm::translate(glm::mat4(1.0), glm::vec3(0.0, 0.0, -5.0))),
    m_proj(glm::perspective(glm::radians(45.0f), (float)width/(float)height, 0.1f, 100.0f)),
    m_width(width), m_height(height) {

    vertex = loadStringFromFile("simple_vertex.glsl");
    frag = loadStringFromFile("simple_fragment.glsl");
    m_fb_shader = new ShaderProgram(&vertex, &frag);

    cube_vertex = loadStringFromFile("piece_vertex.glsl");
    cube_frag = loadStringFromFile("piece_fragment.glsl");

    if (size == 0) {
        m_cube = new AxisCube(&cube_vertex, &cube_frag);
    }
    else {
        m_cube = new Cube(size, &cube_vertex, &cube_frag);
    }

    glEnable(GL_DEPTH_TEST);
    // glEnable(GL_CULL_FACE);

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

    m_vbo.setData(GL_ARRAY_BUFFER, sizeof(quadVertices), quadVertices, GL_STATIC_DRAW);
    m_vao.setAttribute(0, 2, GL_FLOAT, GL_FALSE, 4 * sizeof(float), 0);
    m_vao.setAttribute(1, 2, GL_FLOAT, GL_FALSE, 4 * sizeof(float), 2 * sizeof(float));

#ifdef __ANDROID__
    GLenum err;
    while ((err = glGetError()) != GL_NO_ERROR)
        __android_log_print(ANDROID_LOG_ERROR, "gl", "OPENGL ERROR %x\n", err);
#endif
}

Scene::~Scene() {
    if (s_scene)
        s_scene = nullptr;
}

void Scene::resize(int width, int height) {
    glDeleteFramebuffers(1, &m_fbo);
    glDeleteRenderbuffers(1, &m_rbo);
    glDeleteTextures(1, &m_texture);

    m_width = width;
    m_height = height;

    m_proj = glm::perspective(glm::radians(45.0f), (float)width/(float)height, 0.1f, 100.0f);

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
    m_redraw = true;
}

void Scene::changeCube(int type) {
    m_redraw = true;
    delete m_cube;
    if (type == 0) {
        m_cube = new AxisCube(&cube_vertex, &cube_frag);
    }
    else {
        m_cube = new Cube(type, &cube_vertex, &cube_frag);
    }
    m_rot = glm::mat4(1.0);
}

void Scene::render() {
    if (m_redraw || m_cube->is_in_animation()) {
        m_redraw = false;
        glBindFramebuffer(GL_FRAMEBUFFER, m_fbo);  
        glEnable(GL_DEPTH_TEST);
        glClearColor(0, 0.1, 0.5, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        auto new_view = m_view * m_rot;
        m_cube->draw(new_view, m_proj);
    }

    glBindFramebuffer(GL_FRAMEBUFFER, 0);
    glClearColor(0, 0.5, 0.5, 1);
    glClear(GL_COLOR_BUFFER_BIT);
    glDisable(GL_DEPTH_TEST);
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, m_texture);
    m_fb_shader->use();
    m_fb_shader->setInt("screenTexture", 0);
    m_vbo.bind(GL_ARRAY_BUFFER);
    m_vao.bind();
    glDrawArrays(GL_TRIANGLES, 0, 6);
}

void Scene::handleMouseMovement(int x, int y) {
    if (!m_in_drag || m_in_cube_move) return;
    auto delta = glm::vec2(x, y) - m_drag_start;
    m_redraw = true;
    auto angles = delta / glm::vec2(m_width, m_height) * 360.0f;
    m_rot = glm::yawPitchRoll(glm::radians(angles.x), glm::radians(angles.y), 0.0f) * m_rot;
    m_drag_start = glm::vec2(x, y);
}

void Scene::handleKeyPress(SceneKey key, bool inverse) {
    m_redraw = true;
    switch (key) {
    case SceneKey::L:
        m_cube->execute_move(0, -1, inverse);
        break;
    case SceneKey::R:
        m_cube->execute_move(0, 1, !inverse);
        break;
    case SceneKey::D:
        m_cube->execute_move(1, -1, inverse);
        break;
    case SceneKey::U:
        m_cube->execute_move(1, 1, !inverse);
        break;
    case SceneKey::B:
        m_cube->execute_move(2, -1, inverse);
        break;
    case SceneKey::F:
        m_cube->execute_move(2, 1, !inverse);
        break;
    case SceneKey::M:
        m_cube->execute_move(0, 0, inverse);
        break;
    case SceneKey::E:
        m_cube->execute_move(1, 0, inverse);
        break;
    case SceneKey::S:
        m_cube->execute_move(2, 0, !inverse);
        break;
    case SceneKey::X:
        m_cube->rotate_around_axis(0, inverse);
        break;
    case SceneKey::Y:
        m_cube->rotate_around_axis(1, !inverse);
        break;
    case SceneKey::Z:
        m_cube->rotate_around_axis(2, inverse);
        break;
    case SceneKey::Reset:
        m_cube->reset();
        break;
    }
}

void Scene::handleDragStart(int x, int y) {
    m_in_drag = true;

    glm::mat4 inverse_transform = glm::inverse(m_view * m_rot);
    glm::mat4 inverse_proj = glm::inverse(m_proj);
    glm::vec2 pos = glm::vec2(
        ((float)x / (float)m_width - 0.5) * 2.0,
        -((float)y / (float)m_height - 0.5) * 2.0
    );
    glm::vec4 orig = inverse_proj * glm::vec4(pos, -1, 1);
    orig /= orig.w;
    orig = inverse_transform * orig;
    glm::vec4 dir = inverse_proj * glm::vec4(pos, 1, 1);
    dir /= dir.w;
    dir = inverse_transform * dir;
    dir -= orig;
    dir = glm::normalize(dir);
    glm::vec3 c, p;

    if (m_cube->intersect(orig, dir, c, p)) {
        m_in_cube_move = true;
        m_orig_1 = orig;
        m_dir_1 = dir;
    }
    else {
        m_drag_start = glm::vec2(x, y);
    }
}

void Scene::handleDragStop(int x, int y) {
    m_in_drag = false;
    if (!m_in_cube_move) return;

    glm::mat4 inverse_transform = glm::inverse(m_view * m_rot);
    glm::mat4 inverse_proj = glm::inverse(m_proj);
    glm::vec2 pos = glm::vec2(
        ((float)x / (float)m_width - 0.5) * 2.0,
        -((float)y / (float)m_height - 0.5) * 2.0
    );
    glm::vec4 orig = inverse_proj * glm::vec4(pos, -1, 1);
    orig /= orig.w;
    orig = inverse_transform * orig;
    glm::vec4 dir = inverse_proj * glm::vec4(pos, 1, 1);
    dir /= dir.w;
    dir = inverse_transform * dir;
    dir -= orig;
    dir = glm::normalize(dir);
    glm::vec3 c, p;

    m_cube->execute_move_between_points(m_orig_1, m_dir_1, orig, dir);
    m_in_cube_move = false;
}

#ifdef __ANDROID__
extern "C"
JNIEXPORT void JNICALL
Java_com_example_intcube_CubeGLRenderer_initScene(JNIEnv *env, jobject thiz, jint width,
                                                  jint height, jint size, jobject mgr) {
    s_mgr = AAssetManager_fromJava(env, mgr);
    initScene(width, height, size);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_intcube_CubeGLRenderer_render(JNIEnv *env, jobject thiz) {
    render();
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_intcube_CubeGLView_handleMouseMovement(JNIEnv *env, jobject thiz, jint x, jint y) {
    handleMouseMovement(x, y);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_intcube_CubeGLRenderer_resize(JNIEnv *env, jobject thiz, jint w, jint h) {
    s_scene->resize(w, h);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_intcube_CubeGLRenderer_changeCube(JNIEnv *env, jobject thiz, jint type) {
    s_scene->changeCube(type);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_intcube_CubeGLRenderer_executeMove(JNIEnv *env, jobject thiz, jint move,
                                                    jboolean inverse) {
    s_scene->handleKeyPress((SceneKey)move, inverse);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_intcube_CubeGLView_handleDragStart(JNIEnv *env, jobject thiz, jint x, jint y) {
    s_scene->handleDragStart(x, y);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_intcube_CubeGLView_handleDragStop(JNIEnv *env, jobject thiz, jint x, jint y) {
    s_scene->handleDragStop(x, y);
}
#endif