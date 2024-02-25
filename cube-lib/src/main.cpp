#define GL_GLEXT_PROTOTYPES
#include <SFML/OpenGL.hpp>
#include <SFML/Window.hpp>
#include <SFML/Graphics.hpp>

#include <fstream>
#include <iostream>
#include <sstream>

#include <glm/glm.hpp>
#include <glm/gtc/type_ptr.hpp>

class ShaderProgram {
public:
    ShaderProgram(char const* vertex, char const* fragment) {
        m_programId = glCreateProgram();

        if (!loadFromFile(vertex, GL_VERTEX_SHADER)) {
            std::cout << "Couldn't load vertex shader\n";
        }

        if (!loadFromFile(fragment, GL_FRAGMENT_SHADER)) {
            std::cout << "Couldn't load fragment shader\n";
        }

        glLinkProgram(m_programId);
    }

    bool loadFromFile(char const* filename, GLenum type) {
        std::ifstream file(filename);

        if (!file.is_open()) {
            std::cout << "Couldn't open the file!\n";
            return false;
        }

        int shaderId = glCreateShader(type);

        std::stringstream buffer;
        buffer << file.rdbuf();
        auto str = buffer.str();
        auto c_str = str.c_str();
        file.close();
        glShaderSource(shaderId, 1, &c_str, NULL);
        glCompileShader(shaderId);

        int success;
        char log[512];
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, &success);
        if (!success) {
            glGetShaderInfoLog(shaderId, 512, NULL, log);
            std::cout << "Shader compilation error: " << log << '\n';
            return false;
        }

        glAttachShader(m_programId, shaderId);
        glDeleteShader(shaderId);
        return true;
    }

    void use() {
        glUseProgram(m_programId);
    }

    ~ShaderProgram() {
        glDeleteProgram(m_programId);
    }

    void setUniformMatrix4fv(char const* name, glm::mat4x4& mat) {
        glUseProgram(m_programId);
        auto loc = glGetUniformLocation(m_programId, name);
        glUniformMatrix4fv(loc, 1, GL_FALSE, glm::value_ptr(mat));
    }

    void setInt(char const* name, int value) {
        glUseProgram(m_programId);
        auto loc = glGetUniformLocation(m_programId, name);
        glUniform1i(loc, value);
    }

private:
    int m_programId;
};

struct VertexAttrib {
    GLint size;
    GLenum type;
    GLboolean normalized = GL_FALSE;
    GLsizei stride;
    void* pointer = nullptr;
};

class Model {
public:
    Model() {
        glGenBuffers(1, &m_vbo);
        glGenVertexArrays(1, &m_vao);
        glGenBuffers(1, &m_ebo);
    }

    Model(void* vertexData, void* indexData, int vertexSize, int indexSize, int count) {
        glGenBuffers(1, &m_vbo);
        glGenVertexArrays(1, &m_vao);
        glGenBuffers(1, &m_ebo);

        glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexSize, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexSize, indexData, GL_STATIC_DRAW);
        m_count = count;
    }

    void setVertexData(void* vertexData, int size, int count) {
        glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
        glBufferData(GL_ARRAY_BUFFER, size, vertexData, GL_STATIC_DRAW);
        m_count = count;
    }

    void setProgram(ShaderProgram* program) {
        m_program = program;
    }

    void addAttribute(int index, VertexAttrib attrib) {
        glBindVertexArray(m_vao);
        glVertexAttribPointer(index, attrib.size, attrib.type, attrib.normalized, attrib.stride, attrib.pointer);
        glEnableVertexAttribArray(index);
    }

    void draw() {
        glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
        glBindVertexArray(m_vao);
        if (m_program)
            m_program->use();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ebo);
        glDrawElements(GL_TRIANGLES, m_count, GL_UNSIGNED_INT, nullptr);
    }

    ~Model() {
        glDeleteBuffers(1, &m_vbo);
        glDeleteVertexArrays(1, &m_vao);
    }

private:
    GLuint m_vbo;
    GLuint m_vao;
    GLuint m_ebo;
    ShaderProgram* m_program;
    int m_count;
};

float vertices[] = {
    // face 1
    -0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
     0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
    -0.5f,  0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
     0.5f,  0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
    // face 2
    -0.5f, -0.5f, 0.5f, 1.0f, 0.5f, 0.0f,
    0.5f, -0.5f, 0.5f, 1.0f, 0.5f, 0.0f,
    -0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.0f,
    // face 3
    -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
    -0.5f, -0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
    -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
    -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
    // face 4
    0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
    0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
    0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
    0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
    // face 5
    -0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.0f,
    0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.0f,
    -0.5f, -0.5f, 0.5f, 1.0f, 1.0f, 0.0f,
    0.5f, -0.5f, 0.5f, 1.0f, 1.0f, 0.0f,
    // face 6
    -0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 1.0f,
    0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 1.0f,
    -0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f,
    0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f,
};

unsigned int indices[] = {
    // face 1
    0, 1, 2,
    1, 2, 3,
    // face 2
    4, 5, 6,
    5, 6, 7,
    // face 3
    8, 9, 10,
    9, 10, 11,
    // face 4
    12, 13, 14,
    13, 14, 15,
    // face 5
    16, 17, 18,
    17, 18, 19,
    // face 6
    20, 21, 22,
    21, 22, 23,
};

const int screen_width = 1920;
const int screen_height = 1080;

class Cubie {
public:
    Cubie(glm::vec3 pos, glm::vec3 rot, glm::vec3 scale) 
            : m_shader_program("cube_vertex.glsl", "cube_fragment.glsl"), 
            m_model(vertices, indices, sizeof(vertices), sizeof(indices), 36) {
        // vertex attribs
        VertexAttrib coord = { .size = 3, .type = GL_FLOAT, .stride = 6 * sizeof(float)};
        VertexAttrib col = { .size = 3, .type = GL_FLOAT, .stride = 6 * sizeof(float), .pointer = (void*)(3 * sizeof(float))};
        m_model.addAttribute(0, coord);
        m_model.addAttribute(1, col);
        m_model.setProgram(&m_shader_program);
        m_position = pos;
        m_rotation = rot;
        m_scale = scale;
    }

    void setPosition(glm::vec3 pos) {
        m_position = pos;
    }

    void setRotation(glm::vec3 rot) {
        m_rotation = rot;
    }

    void draw(glm::mat4& view, glm::mat4& proj) {
        auto mod = glm::mat4(1.0);
        mod = glm::scale(mod, m_scale);
        mod = glm::rotate(mod, glm::radians(m_rotation.x), glm::vec3(0.0, 1.0, 0.0));
        mod = glm::rotate(mod, glm::radians(m_rotation.y), glm::vec3(1.0, 0.0, 0.0));
        mod = glm::translate(mod, m_position);
        m_shader_program.setUniformMatrix4fv("model", mod);
        m_shader_program.setUniformMatrix4fv("view", view);
        m_shader_program.setUniformMatrix4fv("projection", proj);
        m_model.draw();
    }

private:
    Model m_model;
    ShaderProgram m_shader_program;

    glm::vec3 m_position;
    glm::vec3 m_rotation;
    glm::vec3 m_scale;
};

int main()
{
    auto window = sf::Window{ { screen_width, screen_height }, "Cube"};
    window.setFramerateLimit(144);

    glEnable(GL_DEPTH_TEST);

    unsigned int fbo;
    glGenFramebuffers(1, &fbo);
    glBindFramebuffer(GL_FRAMEBUFFER, fbo);  

    unsigned int texture;
    glGenTextures(1, &texture);
    glBindTexture(GL_TEXTURE_2D, texture);
    
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, screen_width, screen_height, 0, GL_RGBA, GL_UNSIGNED_BYTE, NULL);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);  

    glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0); 

    unsigned int rbo;
    glGenRenderbuffers(1, &rbo);
    glBindRenderbuffer(GL_RENDERBUFFER, rbo); 
    glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, screen_width, screen_height);  
    glBindRenderbuffer(GL_RENDERBUFFER, 0);
    glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbo);

    if(glCheckFramebufferStatus(GL_FRAMEBUFFER) == GL_FRAMEBUFFER_COMPLETE)
        std::cout << "yay\n";
    glBindFramebuffer(GL_FRAMEBUFFER, 0);  

    auto view = glm::mat4(1.0f);
    view = glm::translate(view, glm::vec3(0.0f, 0.0f, -2.0f));
    glm::mat4 proj = glm::perspective(glm::radians(45.0f), (float)screen_width/(float)screen_height, 0.1f, 100.0f);

    Cubie cubie(glm::vec3(0.0f), glm::vec3(0.0f), glm::vec3(1.0f));

    float quadVertices[] = {
        -1.0f,  1.0f,  0.0f, 1.0f,
        -1.0f, -1.0f,  0.0f, 0.0f,
         1.0f, -1.0f,  1.0f, 0.0f,
         1.0f,  1.0f,  1.0f, 1.0f
    };

    GLuint quadIndices[] = {
        0, 1, 2,
        0, 2, 3,
    };

    ShaderProgram screen_prog("simple_vertex.glsl", "simple_fragment.glsl");
    screen_prog.setInt("screenTexture", 0);
    Model screen(quadVertices, quadIndices, sizeof(quadVertices), sizeof(quadIndices), 6);
    screen.setProgram(&screen_prog);
    screen.addAttribute(0, { .size = 2, .type = GL_FLOAT, .stride = 4 * sizeof(float), .pointer = nullptr });
    screen.addAttribute(1, { .size = 2, .type = GL_FLOAT, .stride = 4 * sizeof(float), .pointer = (void*)(2 * sizeof(float)) });

    sf::Vector2i prev = sf::Mouse::getPosition();
    float angleX = 0, angleY = 0;

    while (window.isOpen())
    {
        for (auto event = sf::Event{}; window.pollEvent(event);)
        {
            if (event.type == sf::Event::Closed)
            {
                window.close();
            }
            else if (event.type == sf::Event::MouseButtonPressed && event.mouseButton.button == sf::Mouse::Left) {
                prev = sf::Vector2i(event.mouseButton.x, event.mouseButton.y);
            }
            else if (event.type == sf::Event::MouseMoved && sf::Mouse::isButtonPressed(sf::Mouse::Left)) {
                prev = sf::Vector2i(event.mouseMove.x, event.mouseMove.y) - prev;
                angleX += (float)prev.x / screen_width * 90.0f;
                angleY += (float)prev.y / screen_height * 90.0f;
                cubie.setRotation(glm::vec3(angleX, angleY, 0.0f));
                prev = sf::Vector2i(event.mouseMove.x, event.mouseMove.y);
            }
        }

        glBindFramebuffer(GL_FRAMEBUFFER, fbo);  
        glEnable(GL_DEPTH_TEST);
        glClearColor(0, 0.5, 0.5, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        cubie.draw(view, proj);

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glClearColor(0, 0.5, 0.5, 1);
        glClear(GL_COLOR_BUFFER_BIT);
        glDisable(GL_DEPTH_TEST);
        glActiveTexture(0);
        glBindTexture(GL_TEXTURE_2D, texture);
        screen.draw();
        window.display();
    }
}
