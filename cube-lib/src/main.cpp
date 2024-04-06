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
    auto res = gladLoadGL(sf::Context::getFunction);

    Scene scene(screen_width, screen_height, size);

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
                scene.handleMouseMovement(prev);
                prev = glm::vec2(event.mouseMove.x, event.mouseMove.y);
            }
            else if (event.type == sf::Event::KeyPressed) {
                auto inverse = event.key.shift;
                switch (event.key.code) {
                case sf::Keyboard::L:
                    scene.handleKeyPress(SceneKey::L, inverse);
                    break;
                case sf::Keyboard::R:
                    if (event.key.control)
                        scene.handleKeyPress(SceneKey::Reset, inverse);
                    else
                        scene.handleKeyPress(SceneKey::R, inverse);
                    break;
                case sf::Keyboard::D:
                    scene.handleKeyPress(SceneKey::D, inverse);
                    break;
                case sf::Keyboard::U:
                    scene.handleKeyPress(SceneKey::U, inverse);
                    break;
                case sf::Keyboard::B:
                    scene.handleKeyPress(SceneKey::B, inverse);
                    break;
                case sf::Keyboard::F:
                    scene.handleKeyPress(SceneKey::F, inverse);
                    break;
                case sf::Keyboard::M:
                    scene.handleKeyPress(SceneKey::M, inverse);
                    break;
                case sf::Keyboard::E:
                    scene.handleKeyPress(SceneKey::E, inverse);
                    break;
                case sf::Keyboard::S:
                    scene.handleKeyPress(SceneKey::S, inverse);
                    break;
                }
            }
        }

        scene.render();
        window.display();
    }
}
