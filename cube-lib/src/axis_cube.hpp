#pragma once
#include "cube.hpp"

class AxisCube : public Cube {
public:
    AxisCube();
    AxisCube(char** vertexShader, char** fragmentShader);
};
