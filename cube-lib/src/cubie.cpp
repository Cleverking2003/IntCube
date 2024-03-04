#include "cubie.hpp"
#include <glm/glm.hpp>
#include <glm/gtc/type_ptr.hpp>
#include <iostream>

static const struct CubieVertex {
    float x;
    float y;
    float z;
    int color;
} vertices[] = {
    // face 1
    { .x = -0.5f, .y = -0.5f, .z = -0.5f, .color = 6 },
    { .x =  0.5f, .y = -0.5f, .z = -0.5f, .color = 6 },
    { .x = -0.5f, .y =  0.5f, .z = -0.5f, .color = 6 },
    { .x =  0.5f, .y =  0.5f, .z = -0.5f, .color = 6 },
    // face 2
    { .x = -0.5f, .y = -0.5f, .z =  0.5f, .color = 6 },
    { .x =  0.5f, .y = -0.5f, .z =  0.5f, .color = 6 },
    { .x = -0.5f, .y =  0.5f, .z =  0.5f, .color = 6 },
    { .x =  0.5f, .y =  0.5f, .z =  0.5f, .color = 6 },
    // face 3
    { .x = -0.5f, .y = -0.5f, .z = -0.5f, .color = 6 },
    { .x = -0.5f, .y = -0.5f, .z =  0.5f, .color = 6 },
    { .x = -0.5f, .y =  0.5f, .z = -0.5f, .color = 6 },
    { .x = -0.5f, .y =  0.5f, .z =  0.5f, .color = 6 },
    // face 4
    { .x =  0.5f, .y = -0.5f, .z = -0.5f, .color = 6 },
    { .x =  0.5f, .y = -0.5f, .z =  0.5f, .color = 6 },
    { .x =  0.5f, .y =  0.5f, .z = -0.5f, .color = 6 },
    { .x =  0.5f, .y =  0.5f, .z =  0.5f, .color = 6 },
    // face 5
    { .x = -0.5f, .y = -0.5f, .z = -0.5f, .color = 6 },
    { .x =  0.5f, .y = -0.5f, .z = -0.5f, .color = 6 },
    { .x = -0.5f, .y = -0.5f, .z =  0.5f, .color = 6 },
    { .x =  0.5f, .y = -0.5f, .z =  0.5f, .color = 6 },
    // face 6
    { .x = -0.5f, .y =  0.5f, .z = -0.5f, .color = 6 },
    { .x =  0.5f, .y =  0.5f, .z = -0.5f, .color = 6 },
    { .x = -0.5f, .y =  0.5f, .z =  0.5f, .color = 6 },
    { .x =  0.5f, .y =  0.5f, .z =  0.5f, .color = 6 },

    // stickers
    // face 1
    { .x = -0.45f, .y = -0.45f, .z = -0.501f, .color = 0 },
    { .x =  0.45f, .y = -0.45f, .z = -0.501f, .color = 0 },
    { .x = -0.45f, .y =  0.45f, .z = -0.501f, .color = 0 },
    { .x =  0.45f, .y =  0.45f, .z = -0.501f, .color = 0 },
    // face 2
    { .x = -0.45f, .y = -0.45f, .z =  0.501f, .color = 1 },
    { .x =  0.45f, .y = -0.45f, .z =  0.501f, .color = 1 },
    { .x = -0.45f, .y =  0.45f, .z =  0.501f, .color = 1 },
    { .x =  0.45f, .y =  0.45f, .z =  0.501f, .color = 1 },
    // face 3
    { .x = -0.501f, .y = -0.45f, .z = -0.45f, .color = 2 },
    { .x = -0.501f, .y = -0.45f, .z =  0.45f, .color = 2 },
    { .x = -0.501f, .y =  0.45f, .z = -0.45f, .color = 2 },
    { .x = -0.501f, .y =  0.45f, .z =  0.45f, .color = 2 },
    // face 4
    { .x =  0.501f, .y = -0.45f, .z = -0.45f, .color = 3 },
    { .x =  0.501f, .y = -0.45f, .z =  0.45f, .color = 3 },
    { .x =  0.501f, .y =  0.45f, .z = -0.45f, .color = 3 },
    { .x =  0.501f, .y =  0.45f, .z =  0.45f, .color = 3 },
    // face 5
    { .x = -0.45f, .y = -0.501f, .z = -0.45f, .color = 4 },
    { .x =  0.45f, .y = -0.501f, .z = -0.45f, .color = 4 },
    { .x = -0.45f, .y = -0.501f, .z =  0.45f, .color = 4 },
    { .x =  0.45f, .y = -0.501f, .z =  0.45f, .color = 4 },
    // face 6
    { .x = -0.45f, .y =  0.501f, .z = -0.45f, .color = 5 },
    { .x =  0.45f, .y =  0.501f, .z = -0.45f, .color = 5 },
    { .x = -0.45f, .y =  0.501f, .z =  0.45f, .color = 5 },
    { .x =  0.45f, .y =  0.501f, .z =  0.45f, .color = 5 },

};

static unsigned int indices[] = {
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

    // stickers
    // face 1
    24, 25, 26,
    25, 26, 27,
    // face 2
    28, 29, 30,
    29, 30, 31,
    // face 3
    32, 33, 34,
    33, 34, 35,
    // face 4
    36, 37, 38,
    37, 38, 39,
    // face 5
    40, 41, 42,
    41, 42, 43,
    // face 6
    44, 45, 46,
    45, 46, 47,
};

static std::map<CubieType, glm::vec3> cubie_coords {
    {ULF, glm::vec3{0, 1, 1}},
    {UBL, glm::vec3{0, 1, 0}},
    {UFR, glm::vec3{1, 1, 1}},
    {URB, glm::vec3{1, 1, 0}},
    {DFL, glm::vec3{0, 0, 1}},
    {DLB, glm::vec3{0, 0, 0}},
    {DRF, glm::vec3{1, 0, 1}},
    {DBR, glm::vec3{1, 0, 0}},
};

static std::map<CubieType, std::array<int,3>> cubieColors {
    { ULF, { 5, 2, 1 } },
    { UBL, { 5, 0, 2 } },
    { UFR, { 5, 1, 3 } },
    { URB, { 5, 3, 0 } },
    { DFL, { 4, 1, 2 } },
    { DLB, { 4, 2, 0 } },
    { DRF, { 4, 3, 1 } },
    { DBR, { 4, 0, 3 } },
};

Cubie::Cubie(ShaderProgram* program, CubieType type) 
        : m_shader_program(program), 
        m_model((void*)vertices, indices, sizeof(vertices), sizeof(indices), 72),
        m_type(type), m_cube_position(type) {
    // vertex attribs
    VertexAttrib coord = { .size = 3, .type = GL_FLOAT, .stride = sizeof(CubieVertex)};
    VertexAttrib col = { .size = 1, .type = GL_UNSIGNED_INT, .stride = sizeof(CubieVertex), .pointer = (void*)(3*sizeof(float))};
    m_model.addAttribute(0, coord);
    m_model.addIntAttribute(1, col);
}

Cubie::Cubie(Cubie&& other) 
    : m_model(std::move(other.m_model)),
    m_shader_program(other.m_shader_program),
    m_type(other.m_type), m_cube_position(other.m_cube_position),
    m_orientation(other.m_orientation) {
    // vertex attribs
    VertexAttrib coord = { .size = 3, .type = GL_FLOAT, .stride = sizeof(CubieVertex)};
    VertexAttrib col = { .size = 1, .type = GL_UNSIGNED_INT, .stride = sizeof(CubieVertex), .pointer = (void*)(3*sizeof(float))};
    m_model.addAttribute(0, coord);
    m_model.addIntAttribute(1, col);
}

void Cubie::draw(glm::mat4& view, glm::mat4& proj) {
    auto mod = glm::mat4(1.0);
    mod = glm::scale(mod, glm::vec3(0.5f));
    mod = glm::translate(mod, cubie_coords[m_cube_position] - 0.5f);

    int colors[7] = { 0, 0, 0, 0, 0, 0, 6 }, i = 0;

    for (auto c : cubieColors[m_cube_position]) {
        colors[c] = cubieColors[m_type][((i++) + m_orientation) % 3];
    }

    m_shader_program->setUniformMatrix4fv("model", mod);
    m_shader_program->setUniformMatrix4fv("view", view);
    m_shader_program->setUniformMatrix4fv("projection", proj);
    m_shader_program->setIntArray("state", colors, 7);
    m_shader_program->use();
    m_model.draw();
}
