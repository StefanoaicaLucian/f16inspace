package com.pp.particles;

public class Trio {

    private float x;
    private float y;
    private float z;

    public Trio(float x, float y, float z) {
	this.x = x;
	this.y = y;
	this.z = z;
    }

    public float getX() {
	return x;
    }

    public float getY() {
	return y;
    }

    public float getZ() {
	return z;
    }

    public void add(Trio trio) {
	x += trio.getX();
	y += trio.getY();
	z += trio.getZ();
    }
}