package rccookie.rendering;

import rccookie.geometry.Rotation;
import rccookie.geometry.Transform;
import rccookie.geometry.Vector;

public abstract class Body implements GameObject {
    private Transform transform;
    private RenderObject renderObject;

    public Body() {
        transform = new Transform();
    }

    @Override
    public Vector location() {
        return transform.location.clone();
    }

    @Override
    public void setLocation(Vector location) {
        if(location == null) return;
        transform.location = location.clone();
    }

    @Override
    public Rotation rotation() {
        return transform.rotation.clone();
    }

    @Override
    public void setRotation(Rotation rotation) {
        if(rotation == null) return;
        transform.rotation = rotation.clone();
    }

    @Override
    public Vector scale() {
        return transform.scale.clone();
    }

    @Override
    public void setScale(Vector scale) {
        if(scale == null) return;
        transform.scale = scale.clone();
    }

    @Override
    public Transform transform() {
        return transform.clone();
    }

    @Override
    public void setTransform(Transform transform) {
        if(transform == null) return;
        this.transform = transform;
    }


    @Override
    public RenderObject renderObject() {
        return renderObject;
    }

    @Override
    public void setRenderObject(RenderObject renderObject) {
        this.renderObject = renderObject;
    }


    public void earlyUpdate(double deltaTime) {}
    public void update(double deltaTime) {}
    public void lateUpdate(double deltaTime) {}
}
