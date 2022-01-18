// Author: Scintoth from ShaderToy.com, modified by Sandra Moen
// Title: Moving Star Constellations
#ifdef GL_ES
precision highp float;
#endif

#define PI 3.14159265359

uniform vec2 u_resolution;
uniform float u_time;

float distLine( vec2 p, vec2 a, vec2 b){
    vec2 pa = (p - a) / 2.0;
    vec2 ba = (b - a) / 2.0;
    float t = clamp(dot(pa, ba)/dot(ba, ba), 0.0,1.0);
    return length(pa - ba*t);
}

float noiseFloat(vec2 p){
    p = fract(p * vec2(213.53, 970.19));
    p = p + dot(p, p+548.23);
    return fract(p.x * p.y);
}

vec2 noiseVector(vec2 p){
float n = noiseFloat(p);
    return vec2(n, noiseFloat(p+n) * 1.0);
}


vec2 GetPos(vec2 id, vec2 offsets){
vec2 n = noiseVector(id + offsets) * 500.0 * ((u_time*.001 + 8000.0 + u_time) / 50000.0);
    return (sin(n) * 0.9) + offsets;
}

float Line(vec2 p, vec2 a, vec2 b){
float d = distLine(p, a, b);
    float m = smoothstep(0.15, 0.01, d * 10.0);
    m = m * smoothstep(0.9, 0.32, length(a-b));
    return m;
}

mat2 rotate2d(float _angle){
    return mat2(cos(_angle),-sin(_angle),
                sin(_angle),cos(_angle));
}

void main()
{
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = (gl_FragCoord.xy-1.0*u_resolution.xy)/u_resolution.y * 0.8;

    float m = 0.0;
    uv = uv * 10.0;

    uv = rotate2d( sin(u_time * .001)*PI ) * uv;
    vec2 gv = fract(uv) - 0.5;
    vec2 id = floor(uv);

    vec2 p[9];

    p[0] = GetPos(id, vec2(-1., -1));
    p[1] = GetPos(id, vec2(0., -1));
    p[2] = GetPos(id, vec2(1., -1));
    p[3] = GetPos(id, vec2(-1., 0));
    p[4] = GetPos(id, vec2(0., 0));
    p[5] = GetPos(id, vec2(1., 0));
    p[6] = GetPos(id, vec2(-1., 1));
    p[7] = GetPos(id, vec2(0., 1));
    p[8] = GetPos(id, vec2(1., 1));

    for(int i=0; i<9; i++){
    	m = m + Line(gv, p[4], p[i]);

        vec2 j = (p[i] - gv) * 2.0;
        float sparkle = 0.1/dot(j, j) * (50.0 / (8000.0 + u_time));

        m = m + sparkle * (sin(((8000.0 + u_time)+p[i].x)* 2.5)*0.5 + 0.9);
    }

    m = m + Line(gv, p[1], p[3]);
    m = m + Line(gv, p[1], p[5]);
    m = m + Line(gv, p[7], p[3]);
    m = m + Line(gv, p[7], p[5]);
    m = m + Line(gv, p[2], p[5]);
    m = m + Line(gv, p[4], p[7]);

    vec3 col = vec3(m * 0.28, m * 0.28, m * 0.4);

    // Output to screen
    gl_FragColor  = vec4(col,1.0);
}