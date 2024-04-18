#version 300 es

layout (location = 0) in vec3 aPos;

out vec3 colorOut;

uniform vec3 color;
uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;


void main()
{
    gl_Position = projection * view * model * vec4(aPos.x, aPos.y, aPos.z, 1.0);
    colorOut = color;
}
