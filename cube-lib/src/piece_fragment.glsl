#version 330 core
in vec3 colorOut;
out vec4 FragColor;

void main()
{
    FragColor = vec4(colorOut, 1.0);
} 
