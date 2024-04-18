#version 300 es

precision mediump float;

out vec4 FragColor;
  
in vec2 texCoords;

uniform sampler2D screenTexture;

void main()
{ 
    FragColor = texture(screenTexture, texCoords);
}
