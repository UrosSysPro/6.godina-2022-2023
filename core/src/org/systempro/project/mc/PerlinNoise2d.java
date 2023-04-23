package org.systempro.project.mc;

import com.badlogic.gdx.math.Vector2;

public class PerlinNoise2d {

    public PerlinNoise2d(){

    }
    private float interpolate(float a0, float a1, float w) {
        return (a1 - a0) * w + a0;
    }
    private Vector2 randomGradient(int ix, int iy) {
        // No precomputed gradients mean this works for any number of grid coordinates
        int w = 8 * 32;
        int s = w / 2; // rotation width
        int a = ix, b = iy;
        a *= 328415741; b ^= a << s | a >> w-s;
        b *= 1911520717; a ^= b << s | b >> w-s;
        a *= 2048419325;
        float random = a * 3.14159265f * 2; // in [0, 2*Pi]
        Vector2 v=new Vector2();
        v.x =(float) Math.cos(random);
        v.y =(float) Math.sin(random);
        return v;
    }

    private float dotGridGradient(int ix, int iy, float x, float y) {
        // Get gradient from integer coordinates
        Vector2 gradient = randomGradient(ix, iy);

        // Compute the distance vector
        float dx = x - (float)ix;
        float dy = y - (float)iy;

        // Compute the dot-product
        return (dx*gradient.x + dy*gradient.y);
    }

    public float get(float x, float y) {
        // Determine grid cell coordinates
        int x0 = (int)x;
        int x1 = x0 + 1;
        int y0 = (int)y;
        int y1 = y0 + 1;

        // Determine interpolation weights
        // Could also use higher order polynomial/s-curve here
        float sx = x - (float)x0;
        float sy = y - (float)y0;

        // Interpolate between grid point gradients
        float n0, n1, ix0, ix1, value;

        n0 = dotGridGradient(x0, y0, x, y);
        n1 = dotGridGradient(x1, y0, x, y);
        ix0 = interpolate(n0, n1, sx);

        n0 = dotGridGradient(x0, y1, x, y);
        n1 = dotGridGradient(x1, y1, x, y);
        ix1 = interpolate(n0, n1, sx);

        value = interpolate(ix0, ix1, sy);
        value=value*0.5f+0.5f;
        value=3*value*value-2*value*value*value;
        return value; // Will return in range -1 to 1. To make it in range 0 to 1, multiply by 0.5 and add 0.5
    }
}
