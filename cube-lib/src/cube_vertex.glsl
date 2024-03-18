#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in uint color;

out vec3 colorOut;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform int state[7];
uniform vec3 rot;

vec3 colors[] = vec3[](
    vec3(1.0, 0.5, 0.0),
    vec3(1.0, 0.0, 0.0),
    vec3(0.0, 1.0, 0.0),
    vec3(0.0, 0.0, 1.0),
    vec3(1.0, 1.0, 0.0),
    vec3(1.0, 1.0, 1.0),
    vec3(0.0, 0.0, 0.0)
);

void main()
{
    gl_Position = projection * view * model * vec4(aPos.x, aPos.y, aPos.z, 1.0);
    colorOut = colors[state[color]];
    // colorOut = rot / 4.0;
}
