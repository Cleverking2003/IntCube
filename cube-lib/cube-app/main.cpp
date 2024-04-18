#include <glad/gl.h>
#include <SFML/Window.hpp>

#include <glm/glm.hpp>
#include <iostream>

#include "scene.hpp"

const int screen_width = 1920;
const int screen_height = 1080;

int main()
{
    int size;
    std::cin >> size;
    auto window = sf::Window{ { screen_width, screen_height }, "Cube"};
    window.setFramerateLimit(144);
    // auto res = gladLoadGL(sf::Context::getFunction);
    initGL();

    initScene(screen_width, screen_height, size);

    glm::vec2 prev;
    bool is_moving = false;

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
                handleMouseMovement(prev.x, prev.y);
                prev = glm::vec2(event.mouseMove.x, event.mouseMove.y);
            }
            else if (event.type == sf::Event::KeyPressed) {
                auto inverse = event.key.shift;
                switch (event.key.code) {
                case sf::Keyboard::L:
                    handleKeyPress(SceneKey::L, inverse);
                    break;
                case sf::Keyboard::R:
                    if (event.key.control)
                        handleKeyPress(SceneKey::Reset, inverse);
                    else
                        handleKeyPress(SceneKey::R, inverse);
                    break;
                case sf::Keyboard::D:
                    handleKeyPress(SceneKey::D, inverse);
                    break;
                case sf::Keyboard::U:
                    handleKeyPress(SceneKey::U, inverse);
                    break;
                case sf::Keyboard::B:
                    handleKeyPress(SceneKey::B, inverse);
                    break;
                case sf::Keyboard::F:
                    handleKeyPress(SceneKey::F, inverse);
                    break;
                case sf::Keyboard::M:
                    handleKeyPress(SceneKey::M, inverse);
                    break;
                case sf::Keyboard::E:
                    handleKeyPress(SceneKey::E, inverse);
                    break;
                case sf::Keyboard::S:
                    handleKeyPress(SceneKey::S, inverse);
                    break;
                }
            }
        }

        render();
        window.display();
    }
}
