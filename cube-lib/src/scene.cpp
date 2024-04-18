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
static JNIEnv * jni_env;
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
    auto len = AAsset_getLength(file);
    auto buf = new char[len + 1];
    std::fill(buf, buf + len, 0);
    char* src = (char*)AAsset_getBuffer(file);
    std::copy(src, src + len, buf);
    __android_log_print(ANDROID_LOG_DEBUG, "glsl", "%s\n", buf);
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

void initScene(int width, int height, int size) {
    // if (!s_scene)
        s_scene = new Scene(width, height, size);
}

void render() {
    s_scene->render();
}

void handleMouseMovement(int x, int y) {
    s_scene->handleMouseMovement(glm::vec2(x, y));
}

void handleKeyPress(SceneKey key, bool inverse) {
    s_scene->handleKeyPress(key, inverse);
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

    auto vertex = loadStringFromFile("simple_vertex.glsl");
    auto frag = loadStringFromFile("simple_fragment.glsl");
    m_fb_shader = new ShaderProgram(&vertex, &frag);

    auto cube_vertex = loadStringFromFile("piece_vertex.glsl");
    std::cout << cube_vertex;
    auto cube_frag = loadStringFromFile("piece_fragment.glsl");

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

void Scene::handleMouseMovement(glm::vec2 delta) {
    m_redraw = true;
    auto angles = delta / glm::vec2(m_width, m_height) * 360.0f;
    m_rot = glm::yawPitchRoll(glm::radians(angles.x), glm::radians(angles.y), 0.0f) * m_rot;
}

void Scene::handleKeyPress(SceneKey key, bool inverse) {
    m_redraw = true;
    switch (key) {
    case SceneKey::L:
        m_cube->execute_move(0, -1, inverse);
        break;
    case SceneKey::R:
        m_cube->execute_move(0, 1, inverse);
        break;
    case SceneKey::D:
        m_cube->execute_move(1, -1, inverse);
        break;
    case SceneKey::U:
        m_cube->execute_move(1, 1, inverse);
        break;
    case SceneKey::B:
        m_cube->execute_move(2, -1, inverse);
        break;
    case SceneKey::F:
        m_cube->execute_move(2, 1, inverse);
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
    case SceneKey::Reset:
        m_cube->reset();
        break;
    }
}

#ifdef __ANDROID__
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_intcube_CubeGLRenderer_initGL(JNIEnv *env, jobject thiz) {
    return initGL();
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_intcube_CubeGLRenderer_initScene(JNIEnv *env, jobject thiz, jint width,
                                                  jint height, jint size, jobject mgr) {
    s_mgr = AAssetManager_fromJava(env, mgr);
    __android_log_print(ANDROID_LOG_DEBUG, "glsl", "SCREEN SIZE %d %d\n", width, height);
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
#endif