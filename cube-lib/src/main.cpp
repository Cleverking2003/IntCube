#define GL_GLEXT_PROTOTYPES
#include <SFML/OpenGL.hpp>
#include <SFML/Window.hpp>

#include <fstream>
#include <iostream>
#include <sstream>

class Shader {
public:
    Shader(GLenum type) {
        shader = glCreateShader(type);
    }

    void loadFromFile(char const* filename) {
        std::ifstream file(filename);

        if (!file.is_open()) {
            std::cout << "Couldn't open the file!\n";
            return;
        }

        std::stringstream buffer;
        buffer << file.rdbuf();
        auto str = buffer.str();
        auto c_str = str.c_str();
        glShaderSource(shader, 1, &c_str, NULL);
        glCompileShader(shader);

        int success;
        char log[512];
        glGetShaderiv(shader, GL_COMPILE_STATUS, &success);
        if (!success) {
            glGetShaderInfoLog(shader, 512, NULL, log);
            std::cout << "Shader compilation error: " << log << '\n';
        }

        file.close();
    }

    void attach(int program) {
        glAttachShader(program, shader);
    }

private:
    unsigned int shader;
};

class Buffer {
public:
    Buffer() {
        glGenBuffers(1, &index);
    }

    Buffer(GLenum target, void* data, int size) {
        glGenBuffers(1, &index);
        glBindBuffer(target, index);
        glBufferData(target, size, data, GL_STATIC_DRAW);
    }

    void bind(GLenum target) {
        glBindBuffer(target, index);
    }

    void setData(GLenum target, void* data, int size) {
        glBufferData(target, size, data, GL_STATIC_DRAW);
    }

private:
    unsigned int index;
};

struct VertexAttrib {
    GLint size;
    GLenum type;
    GLboolean normalized = false;
    GLsizei stride;
    void* pointer = nullptr;
};

class VertexArray {
public:
    VertexArray() {
        glGenVertexArrays(1, &vao);
        glBindVertexArray(vao);
    }

    VertexArray(VertexAttrib* attribs, int length) {
        glGenVertexArrays(1, &vao);
        glBindVertexArray(vao);

        for (auto i = 0; i < length; i++) {
            addAttribute(i, attribs[i]);
        }
    }

    void bind() {
        glBindVertexArray(vao);
    }

    void addAttribute(int index, VertexAttrib& attrib) {
        glVertexAttribPointer(index, attrib.size, attrib.type, attrib.normalized, attrib.stride, attrib.pointer);
        glEnableVertexAttribArray(index);
    }

private:
    unsigned int vao;
};

int main()
{
    auto window = sf::Window{ { 1920u, 1080u }, "CMake SFML Project" };
    window.setFramerateLimit(144);

    float vertices[] = {
        -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f,
        0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f,
        0.0f,  0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
    }; 

    // vertex buffer
    Buffer buf(GL_ARRAY_BUFFER, vertices, sizeof(vertices));

    //vertex shader
    Shader vertex(GL_VERTEX_SHADER);
    vertex.loadFromFile("./vertex.glsl");

    // fragment shader
    Shader fragment(GL_FRAGMENT_SHADER);
    fragment.loadFromFile("./fragment.glsl");

    // shader program
    unsigned int shaderProgram;
    shaderProgram = glCreateProgram();
    vertex.attach(shaderProgram);
    fragment.attach(shaderProgram);
    glLinkProgram(shaderProgram);
    glUseProgram(shaderProgram);

    // attributes
    VertexArray vao;
    VertexAttrib pos = { .size = 3, .type = GL_FLOAT, .stride = 6 * sizeof(float)};
    VertexAttrib col = { .size = 3, .type = GL_FLOAT, .stride = 6 * sizeof(float), .pointer = (void*)(3 * sizeof(float))};
    vao.addAttribute(0, pos);
    vao.addAttribute(1, col);

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
        buf.bind(GL_ARRAY_BUFFER);
        vao.bind();
        glDrawArrays(GL_TRIANGLES, 0, 3);
        window.display();
    }
}