#include "axis_cube.hpp"

std::vector<float> axis_vertices = {
    0, 0, 0,
    0.500000, 0.500000, 0.500000,
    0.500000, 0.000000, 0.500000,
    0.000000, 0.500000, 0.500000,
    0.500000, 0.500000, 0.000000,
    0.166667, 0.166667, 0.166667,
    0.500000, 0.048284, 0.480000,
    0.500000, 0.480000, 0.048284,
    0.500000, 0.480000, 0.480000,
    0.480000, 0.048284, 0.500000,
    0.480000, 0.480000, 0.500000,
    0.048284, 0.480000, 0.500000,
    0.048284, 0.500000, 0.480000,
    0.480000, 0.500000, 0.480000,
    0.480000, 0.500000, 0.048284,
    0.029428, 0.461144, 0.481144,
    0.461144, 0.029428, 0.481144,
    0.173333, 0.173333, 0.193333,
    0.481144, 0.461144, 0.029428,
    0.481144, 0.029428, 0.461144,
    0.193333, 0.173333, 0.173333,
    0.461144, 0.481144, 0.029428,
    0.173333, 0.193333, 0.173333,
    0.029428, 0.481144, 0.461144,
    0.500000, 0.500000, -0.500000,
    0.500000, 0.500000, 0.000000,
    0.166667, 0.166667, 0.166667,
    0.166667, 0.500000, -0.166667,
    -0.055556, 0.277778, -0.055556,
    0.277778, -0.055556, -0.055556,
    0.500000, 0.166667, -0.166667,
    0.055556, 0.055556, -0.277778,
    0.500000, 0.480000, -0.451716,
    0.500000, 0.480000, -0.032361,
    0.500000, 0.200430, -0.172146,
    0.480000, 0.500000, -0.032361,
    0.480000, 0.500000, -0.451716,
    0.200430, 0.500000, -0.172146,
    0.485093, 0.465093, -0.002546,
    0.485093, 0.171760, -0.149213,
    0.284444, -0.028889, -0.048889,
    0.186667, 0.166667, 0.146667,
    -0.035556, 0.277778, -0.075556,
    0.167810, 0.481144, -0.177239,
    0.461144, 0.481144, -0.470572,
    0.062222, 0.082222, -0.271111,
    -0.055556, 0.277778, -0.055556,
    0.166667, 0.500000, -0.166667,
    -0.166667, 0.500000, 0.166667,
    0.166667, 0.166667, 0.166667,
    0.500000, 0.500000, 0.000000,
    0.000000, 0.500000, 0.500000,
    -0.142331, 0.500000, 0.170616,
    0.005479, 0.500000, 0.466237,
    0.466237, 0.500000, 0.005479,
    0.170616, 0.500000, -0.142331,
    -0.149213, 0.485093, 0.171760,
    -0.002546, 0.485093, 0.465093,
    0.146667, 0.186667, 0.166667,
    -0.048889, 0.284444, -0.028889,
    -0.277778, 0.055556, 0.055556,
    -0.055556, 0.277778, -0.055556,
    -0.166667, 0.500000, 0.166667,
    -0.055556, -0.055556, 0.277778,
    -0.166667, 0.166667, 0.500000,
    0.166667, 0.166667, 0.166667,
    0.000000, 0.500000, 0.500000,
    -0.500000, 0.500000, 0.500000,
    -0.177239, 0.167810, 0.481144,
    -0.470572, 0.461144, 0.481144,
    -0.271111, 0.062222, 0.082222,
    -0.075556, -0.035556, 0.277778,
    -0.032361, 0.480000, 0.500000,
    -0.451716, 0.480000, 0.500000,
    -0.172146, 0.200430, 0.500000,
    -0.451716, 0.500000, 0.480000,
    -0.032361, 0.500000, 0.480000,
    -0.172146, 0.500000, 0.200430,
    -0.277778, 0.055556, 0.055556,
    -0.055556, 0.277778, -0.055556,
    -0.166667, 0.500000, 0.166667,
    -0.500000, 0.166667, -0.166667,
    -0.500000, 0.500000, -0.500000,
    -0.500000, 0.500000, 0.500000,
    -0.480000, 0.500000, -0.415279,
    -0.480000, 0.500000, 0.451716,
    -0.191002, 0.500000, 0.162718,
    -0.450186, 0.485093, -0.430186,
    -0.169213, 0.485093, 0.131760,
    -0.075556, 0.297778, -0.055556,
    -0.500000, 0.480000, 0.415279,
    -0.500000, 0.480000, -0.451716,
    -0.500000, 0.191002, -0.162718,
    -0.055556, 0.277778, -0.055556,
    0.166667, 0.500000, -0.166667,
    -0.166667, 0.500000, 0.166667,
    -0.500000, 0.500000, -0.500000,
    -0.455279, 0.500000, -0.455279,
    -0.161188, 0.500000, 0.132903,
    0.132903, 0.500000, -0.161188,
    -0.430186, 0.485093, -0.450186,
    0.131760, 0.485093, -0.169213,
    -0.055556, 0.297778, -0.075556,
    -0.500000, 0.500000, -0.500000,
    -0.055556, 0.277778, -0.055556,
    -0.166667, 0.166667, -0.500000,
    0.055556, 0.055556, -0.277778,
    -0.500000, 0.500000, -0.500000,
    0.500000, 0.500000, -0.500000,
    0.166667, 0.500000, -0.166667,
    -0.055556, 0.277778, -0.055556,
    -0.166667, 0.166667, -0.500000,
    0.055556, 0.055556, -0.277778,
    0.451716, 0.500000, -0.480000,
    -0.415279, 0.500000, -0.480000,
    0.162718, 0.500000, -0.191002,
    -0.451716, 0.480000, -0.500000,
    0.415279, 0.480000, -0.500000,
    -0.162718, 0.191002, -0.500000,
    0.500000, 0.166667, -0.166667,
    0.277778, -0.055556, -0.055556,
    0.500000, -0.166667, 0.166667,
    0.166667, 0.166667, 0.166667,
    0.500000, 0.500000, 0.000000,
    0.500000, 0.000000, 0.500000,
    0.304444, -0.048889, -0.048889,
    0.481144, -0.137239, 0.127811,
    0.481144, 0.127811, -0.137239,
    0.500000, 0.005479, 0.466237,
    0.500000, -0.142331, 0.170616,
    0.500000, 0.170616, -0.142331,
    0.500000, 0.466237, 0.005479,
    0.500000, 0.166667, -0.166667,
    0.277778, -0.055556, -0.055556,
    0.500000, -0.166667, 0.166667,
    0.500000, -0.500000, -0.500000,
    0.500000, -0.161188, 0.132903,
    0.500000, -0.455279, -0.455279,
    0.500000, 0.132903, -0.161188,
    0.485093, -0.169213, 0.131760,
    0.485093, -0.450186, -0.430186,
    0.297778, -0.075556, -0.055556,
    0.500000, -0.500000, -0.500000,
    0.277778, -0.055556, -0.055556,
    0.500000, 0.166667, -0.166667,
    0.055556, 0.055556, -0.277778,
    0.500000, 0.166667, -0.166667,
    0.277778, -0.055556, -0.055556,
    0.166667, -0.166667, -0.500000,
    0.500000, 0.500000, -0.500000,
    0.500000, -0.500000, -0.500000,
    0.500000, -0.415279, -0.480000,
    0.500000, 0.451716, -0.480000,
    0.500000, 0.162718, -0.191002,
    0.480000, 0.415279, -0.500000,
    0.480000, -0.451716, -0.500000,
    0.191002, -0.162718, -0.500000,
    0.055556, 0.055556, -0.277778,
    -0.166667, 0.166667, -0.500000,
    0.166667, -0.166667, -0.500000,
    0.500000, 0.500000, -0.500000,
    0.455279, 0.455279, -0.500000,
    0.161188, -0.132903, -0.500000,
    -0.132903, 0.161188, -0.500000,
    -0.166667, -0.166667, -0.166667,
    0.055556, 0.055556, -0.277778,
    -0.166667, 0.166667, -0.500000,
    0.166667, -0.166667, -0.500000,
    0.000000, -0.500000, -0.500000,
    -0.500000, 0.000000, -0.500000,
    -0.005479, -0.466237, -0.500000,
    -0.466237, -0.005479, -0.500000,
    -0.170616, 0.142331, -0.500000,
    0.142331, -0.170616, -0.500000,
    -0.461144, -0.029428, -0.481144,
    -0.029428, -0.461144, -0.481144,
    -0.173333, -0.173333, -0.193333,
    -0.166667, -0.166667, -0.166667,
    0.055556, 0.055556, -0.277778,
    -0.166667, 0.166667, -0.500000,
    -0.277778, 0.055556, 0.055556,
    -0.055556, 0.277778, -0.055556,
    -0.500000, 0.166667, -0.166667,
    -0.500000, 0.000000, -0.500000,
    -0.500000, 0.500000, -0.500000,
    -0.485093, 0.149213, -0.171760,
    -0.485093, 0.002546, -0.465093,
    -0.186667, -0.146667, -0.166667,
    -0.284444, 0.048889, 0.028889,
    -0.480000, 0.032361, -0.500000,
    -0.480000, 0.451716, -0.500000,
    -0.200430, 0.172146, -0.500000,
    -0.500000, 0.032361, -0.480000,
    -0.500000, 0.172146, -0.200430,
    -0.500000, 0.451716, -0.480000,
    -0.166667, -0.166667, -0.166667,
    -0.277778, 0.055556, 0.055556,
    -0.500000, -0.166667, 0.166667,
    -0.500000, -0.500000, 0.000000,
    -0.500000, 0.166667, -0.166667,
    -0.500000, 0.000000, -0.500000,
    -0.500000, -0.466237, -0.005479,
    -0.500000, -0.170616, 0.142331,
    -0.500000, 0.142331, -0.170616,
    -0.500000, -0.005479, -0.466237,
    -0.481144, 0.137239, -0.127810,
    -0.304444, 0.048889, 0.048889,
    -0.481144, -0.127810, 0.137239,
    -0.277778, 0.055556, 0.055556,
    -0.500000, -0.166667, 0.166667,
    -0.500000, 0.166667, -0.166667,
    -0.500000, 0.500000, 0.500000,
    -0.500000, 0.455279, 0.455279,
    -0.500000, 0.161188, -0.132903,
    -0.500000, -0.132903, 0.161188,
    -0.277778, 0.055556, 0.055556,
    -0.500000, -0.166667, 0.166667,
    -0.055556, -0.055556, 0.277778,
    -0.166667, 0.166667, 0.500000,
    -0.500000, 0.500000, 0.500000,
    -0.500000, -0.500000, 0.500000,
    -0.500000, -0.451716, 0.480000,
    -0.500000, 0.415279, 0.480000,
    -0.500000, -0.162718, 0.191002,
    -0.480000, 0.451716, 0.500000,
    -0.480000, -0.415279, 0.500000,
    -0.191002, 0.162718, 0.500000,
    -0.055556, -0.055556, 0.277778,
    -0.166667, 0.166667, 0.500000,
    0.166667, -0.166667, 0.500000,
    0.166667, 0.166667, 0.166667,
    0.000000, 0.500000, 0.500000,
    0.500000, 0.000000, 0.500000,
    0.466237, 0.005479, 0.500000,
    0.005479, 0.466237, 0.500000,
    -0.142331, 0.170616, 0.500000,
    0.170616, -0.142331, 0.500000,
    -0.055556, -0.055556, 0.277778,
    -0.166667, 0.166667, 0.500000,
    0.166667, -0.166667, 0.500000,
    -0.500000, -0.500000, 0.500000,
    0.132903, -0.161188, 0.500000,
    -0.161188, 0.132903, 0.500000,
    -0.455279, -0.455279, 0.500000,
    -0.055556, -0.075556, 0.297778,
    0.131760, -0.169213, 0.485093,
    -0.430186, -0.450186, 0.485093,
    0.055556, -0.277778, 0.055556,
    0.277778, -0.055556, -0.055556,
    0.500000, -0.166667, 0.166667,
    -0.055556, -0.055556, 0.277778,
    0.166667, -0.166667, 0.500000,
    0.166667, 0.166667, 0.166667,
    0.500000, 0.000000, 0.500000,
    0.500000, -0.500000, 0.500000,
    0.500000, -0.032361, 0.480000,
    0.500000, -0.451716, 0.480000,
    0.500000, -0.172146, 0.200430,
    0.062222, -0.271111, 0.082222,
    -0.035556, -0.075556, 0.277778,
    0.167810, -0.177239, 0.481144,
    0.461144, -0.470572, 0.481144,
    0.480000, -0.451716, 0.500000,
    0.480000, -0.032361, 0.500000,
    0.200430, -0.172146, 0.500000,
    0.055556, -0.277778, 0.055556,
    -0.166667, -0.500000, 0.166667,
    0.166667, -0.500000, -0.166667,
    0.500000, -0.500000, 0.500000,
    0.161188, -0.500000, -0.132903,
    0.455279, -0.500000, 0.455279,
    -0.132903, -0.500000, 0.161188,
    -0.166667, -0.166667, -0.166667,
    0.055556, -0.277778, 0.055556,
    -0.166667, -0.500000, 0.166667,
    -0.500000, -0.500000, 0.000000,
    0.166667, -0.500000, -0.166667,
    0.000000, -0.500000, -0.500000,
    -0.171760, -0.485093, 0.149213,
    -0.465093, -0.485093, 0.002546,
    -0.166667, -0.186667, -0.146667,
    0.028889, -0.284444, 0.048889,
    -0.005479, -0.500000, -0.466237,
    0.142331, -0.500000, -0.170616,
    -0.170616, -0.500000, 0.142331,
    -0.466237, -0.500000, -0.005479,
    0.002546, -0.485093, -0.465093,
    0.149213, -0.485093, -0.171760,
    0.048889, -0.284444, 0.028889,
    -0.146667, -0.186667, -0.166667,
    -0.166667, -0.166667, -0.166667,
    -0.500000, -0.500000, 0.000000,
    0.000000, -0.500000, -0.500000,
    -0.500000, 0.000000, -0.500000,
    -0.500000, -0.500000, -0.500000,
    -0.500000, -0.480000, -0.480000,
    -0.500000, -0.480000, -0.048284,
    -0.500000, -0.048284, -0.480000,
    -0.480000, -0.500000, -0.480000,
    -0.048284, -0.500000, -0.480000,
    -0.480000, -0.500000, -0.048284,
    -0.480000, -0.480000, -0.500000,
    -0.480000, -0.048284, -0.500000,
    -0.048284, -0.480000, -0.500000,
    -0.166667, -0.166667, -0.166667,
    0.055556, -0.277778, 0.055556,
    -0.166667, -0.500000, 0.166667,
    -0.277778, 0.055556, 0.055556,
    -0.500000, -0.166667, 0.166667,
    -0.500000, -0.500000, 0.000000,
    -0.055556, -0.055556, 0.277778,
    -0.500000, -0.500000, 0.500000,
    -0.500000, -0.480000, 0.032361,
    -0.500000, -0.480000, 0.451716,
    -0.500000, -0.200430, 0.172146,
    -0.480000, -0.500000, 0.032361,
    -0.200430, -0.500000, 0.172146,
    -0.480000, -0.500000, 0.451716,
    -0.166667, -0.166667, -0.166667,
    0.055556, 0.055556, -0.277778,
    0.055556, -0.277778, 0.055556,
    0.277778, -0.055556, -0.055556,
    0.166667, -0.166667, -0.500000,
    0.166667, -0.500000, -0.166667,
    0.000000, -0.500000, -0.500000,
    0.500000, -0.500000, -0.500000,
    0.032361, -0.500000, -0.480000,
    0.451716, -0.500000, -0.480000,
    0.172146, -0.500000, -0.200430,
    0.032361, -0.480000, -0.500000,
    0.172146, -0.200430, -0.500000,
    0.451716, -0.480000, -0.500000,
    0.055556, -0.277778, 0.055556,
    -0.166667, -0.500000, 0.166667,
    -0.055556, -0.055556, 0.277778,
    0.166667, -0.166667, 0.500000,
    0.500000, -0.500000, 0.500000,
    -0.500000, -0.500000, 0.500000,
    -0.415279, -0.480000, 0.500000,
    0.451716, -0.480000, 0.500000,
    0.162718, -0.191002, 0.500000,
    0.415279, -0.500000, 0.480000,
    -0.451716, -0.500000, 0.480000,
    -0.162718, -0.500000, 0.191002,
    0.055556, -0.277778, 0.055556,
    0.277778, -0.055556, -0.055556,
    0.500000, -0.166667, 0.166667,
    0.166667, -0.500000, -0.166667,
    0.500000, -0.500000, -0.500000,
    0.500000, -0.500000, 0.500000,
    0.480000, -0.500000, -0.451716,
    0.480000, -0.500000, 0.415279,
    0.191002, -0.500000, -0.162718,
    0.500000, -0.480000, 0.451716,
    0.500000, -0.480000, -0.415279,
    0.500000, -0.191002, 0.162718,
    -0.166667, -0.166667, -0.166667,
    0.055556, 0.055556, -0.277778,
    0.055556, -0.277778, 0.055556,
    0.277778, -0.055556, -0.055556,
    -0.277778, 0.055556, 0.055556,
    -0.055556, 0.277778, -0.055556,
    -0.055556, -0.055556, 0.277778,
    0.166667, 0.166667, 0.166667,
};

MeshData cwrg = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                15, 16, 17,
                18, 19, 20,
                21, 22, 23,
                2, 4, 7, 2, 7, 6,
                4, 1, 8, 4, 8, 7,
                1, 2, 6, 1, 6, 8,
                2, 1, 10, 2, 10, 9,
                1, 3, 11, 1, 11, 10,
                3, 2, 9, 3, 9, 11,
                3, 1, 13, 3, 13, 12,
                1, 4, 14, 1, 14, 13,
                4, 3, 12, 4, 12, 14,
                3, 2, 16, 3, 16, 15,
                2, 5, 17, 2, 17, 16,
                5, 3, 15, 5, 15, 17,
                4, 2, 19, 4, 19, 18,
                2, 5, 20, 2, 20, 19,
                5, 4, 18, 5, 18, 20,
                4, 5, 22, 4, 22, 21,
                5, 3, 23, 5, 23, 22,
                3, 4, 21, 3, 21, 23,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                9, 10, 11,
            },
            .color = glm::vec3(0.0, 1.0, 0.0),
        },
        {
            .indices = {
                6, 7, 8,
            },
            .color = glm::vec3(1.0, 0.0, 0.0),
        },
        {
            .indices = {
                12, 13, 14,
            },
            .color = glm::vec3(1.0),
        },
    }
};

MeshData zwr = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                27, 25, 26, 27, 26, 28,
                29, 31, 28, 29, 28, 26,
                38, 39, 40, 38, 40, 41,
                42, 43, 44, 42, 44, 45,
                24, 30, 29, 24, 29, 31,
                24, 25, 33, 24, 33, 32,
                25, 30, 34, 25, 34, 33,
                30, 24, 32, 30, 32, 34,
                25, 24, 36, 25, 36, 35,
                24, 27, 37, 24, 37, 36,
                27, 25, 35, 27, 35, 37,
                25, 30, 39, 25, 39, 38,
                30, 29, 40, 30, 40, 39,
                29, 26, 41, 29, 41, 40,
                26, 25, 38, 26, 38, 41,
                28, 27, 43, 28, 43, 42,
                27, 24, 44, 27, 44, 43,
                24, 31, 45, 24, 45, 44,
                31, 28, 42, 31, 42, 45,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                32, 33, 34,
            },
            .color = glm::vec3(1.0, 0.0, 0.0),
        },
        {
            .indices = {
                35, 36, 37,
            },
            .color = glm::vec3(1.0),
        },
    }
};

MeshData zrg = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                248, 247, 254, 248, 254, 249,
                253, 249, 248, 253, 248, 252,
                252, 248, 247, 252, 247, 250,
                258, 259, 260, 258, 260, 261,
                252, 253, 251, 252, 251, 250,
                253, 254, 256, 253, 256, 255,
                254, 249, 257, 254, 257, 256,
                249, 253, 255, 249, 255, 257,
                247, 250, 259, 247, 259, 258,
                250, 251, 260, 250, 260, 259,
                251, 254, 261, 251, 261, 260,
                254, 247, 258, 254, 258, 261,
                254, 253, 263, 254, 263, 262,
                253, 251, 264, 253, 264, 263,
                251, 254, 262, 251, 262, 264,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                262, 263, 264,
            },
            .color = glm::vec3(0.0, 1.0, 0.0),
        },
        {
            .indices = {
                255, 256, 257,
            },
            .color = glm::vec3(1.0, 0.0, 0.0),
        },
    }
};

MeshData zyb= {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                319, 321, 320, 319, 320, 318,
                325, 322, 319, 325, 319, 321,
                324, 323, 320, 324, 320, 318,
                319, 318, 324, 319, 324, 322,
                321, 325, 323, 321, 323, 320,
                324, 325, 327, 324, 327, 326,
                325, 323, 328, 325, 328, 327,
                323, 324, 326, 323, 326, 328,
                324, 322, 330, 324, 330, 329,
                322, 325, 331, 322, 331, 330,
                325, 324, 329, 325, 329, 331,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                329, 330, 331,
            },
            .color = glm::vec3(0.0, 0.0, 1.0),
        },
        {
            .indices = {
                326, 327, 328,
            },
            .color = glm::vec3(1.0, 1.0, 0.0),
        },
    }
};

MeshData zyo = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                306, 309, 304, 306, 304, 305,
                310, 305, 306, 310, 306, 311,
                309, 308, 307, 309, 307, 304,
                304, 305, 310, 304, 310, 307,
                307, 310, 311, 307, 311, 308,
                309, 311, 313, 309, 313, 312,
                311, 308, 314, 311, 314, 313,
                308, 309, 312, 308, 312, 314,
                309, 306, 316, 309, 316, 315,
                306, 311, 317, 306, 317, 316,
                311, 309, 315, 311, 315, 317,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                312, 313, 314,
            },
            .color = glm::vec3(1.0, 0.5, 0.0),
        },
        {
            .indices = {
                315, 316, 317,
            },
            .color = glm::vec3(1.0, 1.0, 0.0),
        },
    }
};

MeshData zob = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                185, 186, 187, 185, 187, 188,
                181, 180, 177, 181, 177, 178,
                184, 181, 180, 184, 180, 182,
                179, 184, 181, 179, 181, 178,
                179, 178, 177, 179, 177, 183,
                182, 183, 186, 182, 186, 185,
                183, 177, 187, 183, 187, 186,
                177, 180, 188, 177, 188, 187,
                180, 182, 185, 180, 185, 188,
                183, 184, 190, 183, 190, 189,
                184, 179, 191, 184, 191, 190,
                179, 183, 189, 179, 189, 191,
                183, 182, 193, 183, 193, 192,
                182, 184, 194, 182, 194, 193,
                184, 183, 192, 184, 192, 194,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                189, 190, 191,
            },
            .color = glm::vec3(0.0, 0.0, 1.0),
        },
        {
            .indices = {
                192, 193, 194,
            },
            .color = glm::vec3(1.0, 0.5, 0.0),
        },
    }
};

MeshData zwg = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                62, 61, 60, 62, 60, 67,
                68, 69, 70, 68, 70, 71,
                61, 65, 63, 61, 63, 60,
                65, 63, 64, 65, 64, 66,
                62, 66, 65, 62, 65, 61,
                64, 67, 69, 64, 69, 68,
                67, 60, 70, 67, 70, 69,
                60, 63, 71, 60, 71, 70,
                63, 64, 68, 63, 68, 71,
                66, 67, 73, 66, 73, 72,
                67, 64, 74, 67, 74, 73,
                64, 66, 72, 64, 72, 74,
                67, 66, 76, 67, 76, 75,
                66, 62, 77, 66, 77, 76,
                62, 67, 75, 62, 75, 77,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                72, 73, 74,
            },
            .color = glm::vec3(0.0, 1.0, 0.0),
        },
        {
            .indices = {
                75, 76, 77,
            },
            .color = glm::vec3(1.0),
        },
    }
};

MeshData cyob = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                293, 290, 291,
                292, 291, 290,
                293, 292, 290,
                294, 291, 296, 294, 296, 295,
                291, 293, 297, 291, 297, 296,
                293, 294, 295, 293, 295, 297,
                294, 292, 299, 294, 299, 298,
                292, 291, 300, 292, 300, 299,
                291, 294, 298, 291, 298, 300,
                294, 293, 302, 294, 302, 301,
                293, 292, 303, 293, 303, 302,
                292, 294, 301, 292, 301, 303,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                301, 302, 303,
            },
            .color = glm::vec3(0.0, 0.0, 1.0),
        },
        {
            .indices = {
                295, 296, 297,
            },
            .color = glm::vec3(1.0, 0.5, 0.0),
        },
        {
            .indices = {
                298, 299, 300,
            },
            .color = glm::vec3(1.0, 1.0, 0.0),
        },
    }
};

MeshData cy = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                265, 268, 266,
                267, 265, 268,
                265, 267, 266,
                267, 268, 270, 267, 270, 269,
                268, 266, 271, 268, 271, 270,
                266, 267, 269, 266, 269, 271,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                269, 270, 271,
            },
            .color = glm::vec3(1.0, 1.0, 0.0),
        },
    }
};

MeshData cw = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                94, 93, 95,
                96, 95, 93,
                100, 101, 102,
                96, 95, 98, 96, 98, 97,
                95, 94, 99, 95, 99, 98,
                94, 96, 97, 94, 97, 99,
                96, 94, 101, 96, 101, 100,
                94, 93, 102, 94, 102, 101,
                93, 96, 100, 93, 100, 102,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                97, 98, 99,
            },
            .color = glm::vec3(1.0),
        },
    }
};

MeshData cr = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                133, 134, 132,
                132, 135, 133,
                139, 140, 141,
                134, 135, 137, 134, 137, 136,
                135, 132, 138, 135, 138, 137,
                132, 134, 136, 132, 136, 138,
                134, 135, 140, 134, 140, 139,
                135, 133, 141, 135, 141, 140,
                133, 134, 139, 133, 139, 141,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                136, 137, 138,
            },
            .color = glm::vec3(1.0, 0.0, 0.0),
        },
    }
};

MeshData co = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                208, 209, 211,
                210, 208, 209,
                211, 210, 208,
                211, 210, 213, 211, 213, 212,
                210, 209, 214, 210, 214, 213,
                209, 211, 212, 209, 212, 214,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                212, 213, 214,
            },
            .color = glm::vec3(1.0, 0.5, 0.0),
        },
    }
};

MeshData cg = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                238, 237, 239,
                237, 240, 238,
                244, 245, 246,
                239, 238, 242, 239, 242, 241,
                238, 240, 243, 238, 243, 242,
                240, 239, 241, 240, 241, 243,
                237, 239, 245, 237, 245, 244,
                239, 240, 246, 239, 246, 245,
                240, 237, 244, 240, 244, 246,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                241, 242, 243,
            },
            .color = glm::vec3(0.0, 1.0, 0.0),
        },
    }
};

MeshData cb = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                158, 157, 160,
                159, 158, 157,
                157, 160, 159,
                160, 159, 162, 160, 162, 161,
                159, 158, 163, 159, 163, 162,
                158, 160, 161, 158, 161, 163,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                161, 162, 163,
            },
            .color = glm::vec3(0.0, 0.0, 1.0),
        },
    }
};

MeshData ey = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                278, 279, 280, 278, 280, 281,
                277, 275, 272,
                286, 287, 288, 286, 288, 289,
                273, 276, 274,
                274, 275, 279, 274, 279, 278,
                275, 272, 280, 275, 280, 279,
                272, 273, 281, 272, 281, 280,
                273, 274, 278, 273, 278, 281,
                277, 276, 283, 277, 283, 282,
                276, 274, 284, 276, 284, 283,
                274, 275, 285, 274, 285, 284,
                275, 277, 282, 275, 282, 285,
                277, 276, 287, 277, 287, 286,
                276, 273, 288, 276, 288, 287,
                273, 272, 289, 273, 289, 288,
                272, 277, 286, 272, 286, 289,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                282, 283, 284, 282, 284, 285,
            },
            .color = glm::vec3(1.0, 1.0, 0.0),
        },
    }
};

MeshData ew = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
               47, 46, 48,
                50, 49, 51,
                47, 50, 49, 47, 49, 46,
                56, 57, 58, 56, 58, 59,
                48, 51, 53, 48, 53, 52,
                51, 50, 54, 51, 54, 53,
                50, 47, 55, 50, 55, 54,
                47, 48, 52, 47, 52, 55,
                48, 51, 57, 48, 57, 56,
                51, 49, 58, 51, 58, 57,
                49, 46, 59, 49, 59, 58,
                46, 48, 56, 46, 56, 59,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                52, 53, 54, 52, 54, 55,
            },
            .color = glm::vec3(1.0),
        },
    }
};

MeshData er = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                125, 126, 127,
                124, 121, 120, 124, 120, 122,
                123, 119, 120, 123, 120, 122,
                123, 124, 122,
                120, 121, 126, 120, 126, 125,
                121, 119, 127, 121, 127, 126,
                119, 120, 125, 119, 125, 127,
                124, 121, 129, 124, 129, 128,
                121, 119, 130, 121, 130, 129,
                119, 123, 131, 119, 131, 130,
                123, 124, 128, 123, 128, 131,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                128, 129, 130, 128, 130, 131,
            },
            .color = glm::vec3(1.0, 0.0, 0.0),
        },
    }
};

MeshData eo = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                200, 195, 198,
                199, 200, 195, 199, 195, 196,
                198, 197, 196, 198, 196, 195,
                205, 206, 207,
                198, 197, 202, 198, 202, 201,
                197, 199, 203, 197, 203, 202,
                199, 200, 204, 199, 204, 203,
                200, 198, 201, 200, 201, 204,
                199, 196, 206, 199, 206, 205,
                196, 197, 207, 196, 207, 206,
                197, 199, 205, 197, 205, 207,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                201, 202, 203, 201, 203, 204,
            },
            .color = glm::vec3(1.0, 0.5, 0.0),
        },
    }
};

MeshData eg = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                228, 227, 229,
                230, 232, 229, 230, 229, 227,
                230, 227, 228, 230, 228, 231,
                231, 232, 230,
                232, 231, 234, 232, 234, 233,
                231, 228, 235, 231, 235, 234,
                228, 229, 236, 228, 236, 235,
                229, 232, 233, 229, 233, 236,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                233, 234, 235, 233, 235, 236,
            },
            .color = glm::vec3(0.0, 1.0, 0.0),
        },
    }
};

MeshData eb = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                166, 165, 164, 166, 164, 169,
                167, 166, 165,
                165, 164, 168, 165, 168, 167,
                174, 175, 176,
                168, 169, 171, 168, 171, 170,
                169, 166, 172, 169, 172, 171,
                166, 167, 173, 166, 173, 172,
                167, 168, 170, 167, 170, 173,
                169, 168, 175, 169, 175, 174,
                168, 164, 176, 168, 176, 175,
                164, 169, 174, 164, 174, 176,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                170, 171, 172, 170, 172, 173,
            },
            .color = glm::vec3(0.0, 0.0, 1.0),
        },
    }
};

MeshData erb = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                144, 142, 143,
                149, 146, 147, 149, 147, 145,
                150, 148, 145, 150, 145, 147,
                145, 149, 148,
                150, 149, 152, 150, 152, 151,
                149, 146, 153, 149, 153, 152,
                146, 150, 151, 146, 151, 153,
                149, 150, 155, 149, 155, 154,
                150, 148, 156, 150, 156, 155,
                148, 149, 154, 148, 154, 156,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                151, 152, 153,
            },
            .color = glm::vec3(1.0, 0.0, 0.0),
        },
        {
            .indices = {
                154, 155, 156,
            },
            .color = glm::vec3(0.0, 0.0, 1.0),
        },
    }
};

MeshData ewb = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                105, 103, 104, 105, 104, 106,
                107, 109, 110,
                110, 109, 108, 110, 108, 112,
                111, 112, 108,
                108, 107, 114, 108, 114, 113,
                107, 109, 115, 107, 115, 114,
                109, 108, 113, 109, 113, 115,
                107, 108, 117, 107, 117, 116,
                108, 111, 118, 108, 118, 117,
                111, 107, 116, 111, 116, 118,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                113, 114, 115,
            },
            .color = glm::vec3(1.0),
        },
        {
            .indices = {
                116, 117, 118,
            },
            .color = glm::vec3(0.0, 0.0, 1.0),
        },
    }
};

MeshData eog = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                215, 216, 219,
                218, 219, 215, 218, 215, 217,
                217, 220, 218,
                215, 217, 220, 215, 220, 216,
                220, 219, 222, 220, 222, 221,
                219, 216, 223, 219, 223, 222,
                216, 220, 221, 216, 221, 223,
                219, 220, 225, 219, 225, 224,
                220, 218, 226, 220, 226, 225,
                218, 219, 224, 218, 224, 226,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                224, 225, 226,
            },
            .color = glm::vec3(0.0, 1.0, 0.0),
        },
        {
            .indices = {
                221, 222, 223,
            },
            .color = glm::vec3(1.0, 0.5, 0.0),
        },
    }
};

MeshData eyg = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                332, 336, 333,
                334, 332, 333, 334, 333, 337,
                332, 334, 335, 332, 335, 336,
                334, 335, 337,
                337, 336, 339, 337, 339, 338,
                336, 335, 340, 336, 340, 339,
                335, 337, 338, 335, 338, 340,
                336, 337, 342, 336, 342, 341,
                337, 333, 343, 337, 343, 342,
                333, 336, 341, 333, 341, 343,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                341, 342, 343,
            },
            .color = glm::vec3(1.0, 1.0, 0.0),
        },
        {
            .indices = {
                338, 339, 340,
            },
            .color = glm::vec3(0.0, 1.0, 0.0),
        },
    }
};

MeshData eyr = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                345, 344, 349, 345, 349, 346,
                347, 344, 349,
                345, 348, 347, 345, 347, 344,
                346, 348, 345,
                348, 349, 351, 348, 351, 350,
                349, 347, 352, 349, 352, 351,
                347, 348, 350, 347, 350, 352,
                349, 348, 354, 349, 354, 353,
                348, 346, 355, 348, 355, 354,
                346, 349, 353, 346, 353, 355,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                353, 354, 355,
            },
            .color = glm::vec3(1.0, 0.0, 0.0),
        },
        {
            .indices = {
                350, 351, 352,
            },
            .color = glm::vec3(1.0, 1.0, 0.0),
        },
    }
};

MeshData ewo = {
    .vertices = axis_vertices,
    .face_data = {
        {
            .indices = {
                80, 79, 78, 80, 78, 83,
                87, 88, 89,
                82, 79, 78, 82, 78, 81,
                83, 81, 78,
                82, 83, 85, 82, 85, 84,
                83, 80, 86, 83, 86, 85,
                80, 82, 84, 80, 84, 86,
                82, 80, 88, 82, 88, 87,
                80, 79, 89, 80, 89, 88,
                79, 82, 87, 79, 87, 89,
                83, 82, 91, 83, 91, 90,
                82, 81, 92, 82, 92, 91,
                81, 83, 90, 81, 90, 92,
            },
            .color = glm::vec3(0.0),
        },
        {
            .indices = {
                90, 91, 92,
            },
            .color = glm::vec3(1.0, 0.5, 0.0),
        },
        {
            .indices = {
                84, 85, 86,
            },
            .color = glm::vec3(1.0),
        },
    }
};

AxisCube::AxisCube(char** vertexShader, char** fragmentShader) : Cube(3, vertexShader, fragmentShader) {
    m_axes = {
            std::pair { glm::vec3(0.25, -0.5, -0.5), 4 },
            { glm::vec3(0.5, 0.5, -0.25), 4 },
            { glm::vec3(0.5, -0.25, 0.5), 4 },
    };

    m_cubies.clear();
    m_cubies.emplace_back(Mesh(zwr, m_shader), glm::vec3(0, 1, 0), glm::vec3(0, 1, 0));
    m_cubies.emplace_back(Mesh(zrg, m_shader), glm::vec3(0, 0, 1), glm::vec3(0, 0, 1));
    m_cubies.emplace_back(Mesh(zyb, m_shader), glm::vec3(1, 0, 0), glm::vec3(1, 0, 0));
    m_cubies.emplace_back(Mesh(zyo, m_shader), glm::vec3(0, -1, 0), glm::vec3(0, -1, 0));
    m_cubies.emplace_back(Mesh(zob, m_shader), glm::vec3(0, 0, -1), glm::vec3(0, 0, -1));
    m_cubies.emplace_back(Mesh(zwg, m_shader), glm::vec3(-1, 0, 0), glm::vec3(-1, 0, 0));

    m_cubies.emplace_back(Mesh(cwrg, m_shader), glm::vec3(-1, 1, 1), glm::vec3(-1, 1, 1));
    m_cubies.emplace_back(Mesh(cyob, m_shader), glm::vec3(1, -1, -1), glm::vec3(1, -1, -1));

    m_cubies.emplace_back(Mesh(cw, m_shader), glm::vec3(-1, 1, -1), glm::vec3(-1, 1, -1));
    m_cubies.emplace_back(Mesh(cy, m_shader), glm::vec3(1, -1, 1), glm::vec3(1, -1, 1));
    m_cubies.emplace_back(Mesh(cr, m_shader), glm::vec3(1, 1, 1), glm::vec3(1, 1, 1));
    m_cubies.emplace_back(Mesh(co, m_shader), glm::vec3(-1, -1, -1), glm::vec3(-1, -1, -1));
    m_cubies.emplace_back(Mesh(cg, m_shader), glm::vec3(-1, -1, 1), glm::vec3(-1, -1, 1));
    m_cubies.emplace_back(Mesh(cb, m_shader), glm::vec3(1, 1, -1), glm::vec3(1, 1, -1));

    m_cubies.emplace_back(Mesh(ew, m_shader), glm::vec3(-1, 1, 0), glm::vec3(-1, 1, 0));
    m_cubies.emplace_back(Mesh(ey, m_shader), glm::vec3(1, -1, 0), glm::vec3(1, -1, 0));
    m_cubies.emplace_back(Mesh(er, m_shader), glm::vec3(0, 1, 1), glm::vec3(0, 1, 1));
    m_cubies.emplace_back(Mesh(eo, m_shader), glm::vec3(0, -1, -1), glm::vec3(0, -1, -1));
    m_cubies.emplace_back(Mesh(eg, m_shader), glm::vec3(-1, 0, 1), glm::vec3(-1, 0, 1));
    m_cubies.emplace_back(Mesh(eb, m_shader), glm::vec3(1, 0, -1), glm::vec3(1, 0, -1));

    m_cubies.emplace_back(Mesh(ewo, m_shader), glm::vec3(-1, 0, -1), glm::vec3(-1, 0, -1));
    m_cubies.emplace_back(Mesh(eyr, m_shader), glm::vec3(1, 0, 1), glm::vec3(1, 0, 1));
    m_cubies.emplace_back(Mesh(erb, m_shader), glm::vec3(1, 1, 0), glm::vec3(1, 1, 0));
    m_cubies.emplace_back(Mesh(eog, m_shader), glm::vec3(-1, -1, 0), glm::vec3(-1, -1, 0));
    m_cubies.emplace_back(Mesh(eyg, m_shader), glm::vec3(0, -1, 1), glm::vec3(0, -1, 1));
    m_cubies.emplace_back(Mesh(ewb, m_shader), glm::vec3(0, 1, -1), glm::vec3(0, 1, -1));
}
