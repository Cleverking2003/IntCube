#pragma once
#define GL_GLEXT_PROTOTYPES
#include "mesh.hpp"

#include <array>
#include <map>

enum CubieType : int {
    ULF,
    UBL,
    UFR,
    URB,
    DFL,
    DLB,
    DRF,
    DBR,
};

class Cubie {
public:
    explicit Cubie(ShaderProgram*, CubieType type);
    Cubie(Cubie&&);
    void setPosition(glm::vec3 pos);
    void setRotation(glm::vec3 rot);
    void draw(glm::mat4& view, glm::mat4& proj);

    CubieType m_cube_position;
    int m_orientation { 0 };
private:
    Model m_model;
    ShaderProgram* m_shader_program;
    CubieType m_type;
};
