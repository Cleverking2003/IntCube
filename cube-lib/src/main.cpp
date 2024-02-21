#define GL_GLEXT_PROTOTYPES
#include <SFML/OpenGL.hpp>
#include <SFML/Window.hpp>

#include <fstream>
#include <iostream>
#include <sstream>

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
    }

    Model(void* vertexData, int size, int count) {
        glGenBuffers(1, &m_vbo);
        glGenVertexArrays(1, &m_vao);

        glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
        glBufferData(GL_ARRAY_BUFFER, size, vertexData, GL_STATIC_DRAW);
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
        glDrawArrays(GL_TRIANGLES, 0, m_count);
    }

    ~Model() {
        glDeleteBuffers(1, &m_vbo);
        glDeleteVertexArrays(1, &m_vao);
    }

private:
    GLuint m_vbo;
    GLuint m_vao;
    ShaderProgram* m_program;
    int m_count;
};

int main()
{
    auto window = sf::Window{ { 1920u, 1080u }, "Cube" };
    window.setFramerateLimit(144);

    float vertices[] = {
        -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f,
        0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f,
        0.0f,  0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
    }; 
    
    // vertex attribs
    VertexAttrib pos = { .size = 3, .type = GL_FLOAT, .stride = 6 * sizeof(float)};
    VertexAttrib col = { .size = 3, .type = GL_FLOAT, .stride = 6 * sizeof(float), .pointer = (void*)(3 * sizeof(float))};

    // shader program
    ShaderProgram program("vertex.glsl", "fragment.glsl");

    // model
    Model model(vertices, sizeof(vertices), 3);
    model.addAttribute(0, pos);
    model.addAttribute(1, col);
    model.setProgram(&program);

    while (window.isOpen())
    {
        for (auto event = sf::Event{}; window.pollEvent(event);)
        {
            if (event.type == sf::Event::Closed)
            {
                window.close();
            }
        }

        glClearColor(0, 0.5, 0.5, 1);
        glClear(GL_COLOR_BUFFER_BIT);
        model.draw();
        window.display();
    }
}
