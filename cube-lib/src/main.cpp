#define GL_GLEXT_PROTOTYPES
#include <SFML/OpenGL.hpp>
#include <SFML/Window.hpp>

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

private:
    int m_programId;
};

struct VertexAttrib {
    GLint size;
    GLenum type;
    GLboolean normalized = false;
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

    void addAttribute(int index, VertexAttrib& attrib) {
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

    glm::vec3 m_position;
};

int main()
{
    auto window = sf::Window{ { 1920u, 1080u }, "Cube" };
    window.setFramerateLimit(144);

    glEnable(GL_DEPTH_TEST);

    // float vertices[] = {
    //     -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f,
    //     0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f,
    //     0.0f,  0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
    // }; 

    // int indices[] = {
    //     0, 1, 2,
    // };

    float vertices[] = {
        // face 1
        0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
        0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
        0.0f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f,
        0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f,
        // face 2
        0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 0.0f,
        0.5f, 0.0f, 0.5f, 1.0f, 0.5f, 0.0f,
        0.0f, 0.5f, 0.5f, 1.0f, 0.5f, 0.0f,
        0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.0f,
        // face 3
        0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
        0.0f, 0.0f, 0.5f, 0.0f, 1.0f, 0.0f,
        0.0f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f,
        0.0f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
        // face 4
        0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f,
        0.5f, 0.0f, 0.5f, 0.0f, 0.0f, 1.0f,
        0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
        0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
        // face 5
        0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
        0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
        0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 0.0f,
        0.5f, 0.0f, 0.5f, 1.0f, 1.0f, 0.0f,
        // face 6
        0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f,
        0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f,
        0.0f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f,
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

    // vertex attribs
    VertexAttrib pos = { .size = 3, .type = GL_FLOAT, .stride = 6 * sizeof(float)};
    VertexAttrib col = { .size = 3, .type = GL_FLOAT, .stride = 6 * sizeof(float), .pointer = (void*)(3 * sizeof(float))};

    // shader program
    ShaderProgram program("vertex.glsl", "fragment.glsl");
    auto mod = glm::translate(glm::mat4(1.0), glm::vec3(-0.25));
    auto view = glm::mat4(1.0f);
    view = glm::translate(view, glm::vec3(0.0f, 0.0f, -2.0f));
    glm::mat4 proj = glm::perspective(glm::radians(45.0f), (float)1920/(float)1080, 0.1f, 100.0f);

    // model
    Model model(vertices, indices, sizeof(vertices), sizeof(indices), 36);
    model.addAttribute(0, pos);
    model.addAttribute(1, col);
    model.setProgram(&program);

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
                std::cout << prev.x << '\n';
                std::cout << prev.y << '\n';
            }
            else if (event.type == sf::Event::MouseMoved && sf::Mouse::isButtonPressed(sf::Mouse::Left)) {
                std::cout << event.mouseMove.x << '\n';
                std::cout << event.mouseMove.y << '\n';
                prev = sf::Vector2i(event.mouseMove.x, event.mouseMove.y) - prev;
                angleX += prev.x / 1920.0f * 90.0f;
                angleY += prev.y / 1080.0f * 90.0f;
                mod = glm::mat4(1.0);
                mod = glm::rotate(mod, glm::radians(angleX), glm::vec3(0.0, 1.0, 0.0));
                mod = glm::rotate(mod, glm::radians(angleY), glm::vec3(1.0, 0.0, 0.0));
                mod = glm::translate(mod, glm::vec3(-0.25));
                prev = sf::Vector2i(event.mouseMove.x, event.mouseMove.y);
            }
        }

        glClearColor(0, 0.5, 0.5, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        program.setUniformMatrix4fv("model", mod);
        program.setUniformMatrix4fv("view", view);
        program.setUniformMatrix4fv("projection", proj);
        model.draw();
        window.display();
    }
}
