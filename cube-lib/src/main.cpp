#define GL_GLEXT_PROTOTYPES
#include <SFML/OpenGL.hpp>
#include <SFML/Window.hpp>
#include <SFML/Graphics.hpp>

#include <fstream>
#include <iostream>
#include <sstream>

#include <glm/glm.hpp>
#include <glm/gtc/type_ptr.hpp>
#include <glm/gtx/euler_angles.hpp>

#include "cube.hpp"
#include "mesh.hpp"
#include "shader.hpp"

const int screen_width = 1920;
const int screen_height = 1080;

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
    view = glm::translate(view, glm::vec3(0.0f, 0.0f, -5.0f));
    glm::mat4 proj = glm::perspective(glm::radians(45.0f), (float)screen_width/(float)screen_height, 0.1f, 100.0f);

    Cube<3> cube;

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
    screen.addAttribute(0, { .size = 2, .type = GL_FLOAT, .stride = 4 * sizeof(float), .pointer = nullptr });
    screen.addAttribute(1, { .size = 2, .type = GL_FLOAT, .stride = 4 * sizeof(float), .pointer = (void*)(2 * sizeof(float)) });

    glm::vec2 prev;
    bool is_moving = false;

    glm::mat4 rot = glm::yawPitchRoll(0, 0, 0);

    while (window.isOpen())
    {
        for (auto event = sf::Event{}; window.pollEvent(event);)
        {
            if (event.type == sf::Event::Closed)
            {
                window.close();
            }
            else if ((event.type == sf::Event::MouseButtonPressed || event.type == sf::Event::MouseButtonReleased) && event.mouseButton.button == sf::Mouse::Left) {
                prev = glm::vec2(event.mouseButton.x, event.mouseButton.y);
                is_moving = event.type == sf::Event::MouseButtonPressed;
            }
            else if (event.type == sf::Event::MouseMoved && sf::Mouse::isButtonPressed(sf::Mouse::Left) && is_moving) {
                prev = glm::vec2(event.mouseMove.x, event.mouseMove.y) - prev;
                auto angles = prev / glm::vec2(screen_width, screen_height) * 360.0f;
                rot = glm::yawPitchRoll(glm::radians(angles.x), glm::radians(angles.y), 0.0f) * rot;
                prev = glm::vec2(event.mouseMove.x, event.mouseMove.y);
            }
            else if (event.type == sf::Event::KeyPressed) {
                auto inverse = event.key.shift;
                switch (event.key.code) {
                case sf::Keyboard::L:
                    cube.execute_move(0, -1, inverse);
                    break;
                case sf::Keyboard::R:
                    cube.execute_move(0, 1, inverse);
                    break;
                case sf::Keyboard::D:
                    cube.execute_move(1, -1, inverse);
                    break;
                case sf::Keyboard::U:
                    cube.execute_move(1, 1, inverse);
                    break;
                case sf::Keyboard::B:
                    cube.execute_move(2, -1, inverse);
                    break;
                case sf::Keyboard::F:
                    cube.execute_move(2, 1, inverse);
                    break;
                case sf::Keyboard::M:
                    cube.execute_move(0, 0, inverse);
                    break;
                case sf::Keyboard::E:
                    cube.execute_move(1, 0, inverse);
                    break;
                case sf::Keyboard::S:
                    cube.execute_move(2, 0, !inverse);
                    break;
                }
            }
        }

        glBindFramebuffer(GL_FRAMEBUFFER, fbo);  
        glEnable(GL_DEPTH_TEST);
        glClearColor(0, 0.5, 0.5, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        auto new_view = view * rot;
        cube.draw(new_view, proj);

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glClearColor(0, 0.5, 0.5, 1);
        glClear(GL_COLOR_BUFFER_BIT);
        glDisable(GL_DEPTH_TEST);
        glActiveTexture(0);
        glBindTexture(GL_TEXTURE_2D, texture);
        screen_prog.use();
        screen.draw();
        window.display();
    }
}
