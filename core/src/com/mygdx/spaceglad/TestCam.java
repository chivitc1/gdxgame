package com.mygdx.spaceglad;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class TestCam extends ApplicationAdapter {
    public PerspectiveCamera camera;
    public Model model;
    public ModelInstance instance;
    public ModelBatch modelBatch;
    public Environment environment;

    Vector3 position = new Vector3();
    float rotation;
    float scale = 1;
    boolean increment = true;

    private void move() {
        instance.transform.getTranslation(position);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.x += Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.z += Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.z -= Gdx.graphics.getDeltaTime();
        }
        instance.transform.setTranslation(position);
    }

    void rotate() {
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            instance.transform.rotate(Vector3.X, Gdx.graphics.getDeltaTime() * 100);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            instance.transform.rotate(Vector3.Y, Gdx.graphics.getDeltaTime() * 100);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            instance.transform.rotate(Vector3.Z, Gdx.graphics.getDeltaTime() * 100);
        }
    }

    void autoRotate() {
        rotation = (rotation + Gdx.graphics.getDeltaTime() * 100) % 360;
//        instance.transform.setFromEulerAngles(0, 0, rotation).trn(position.x, position.y, position.z);
    }

    void autoScale() {
        if (increment) {
            scale = (scale + Gdx.graphics.getDeltaTime() / 5);
            if (scale >= 1.5f) {
                increment = false;
            }
        } else {
            scale = (scale - Gdx.graphics.getDeltaTime() / 5);
            if (scale <= 0.5f) {
                increment = true;
            }
        }
    }

    void updateTransformation() {
        instance.transform.setFromEulerAngles(0, 0, rotation).trn(position.x, position.y, position.z)
            .scale(scale, scale, scale);
    }

    @Override
    public void create() {
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0, 0, 0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();
        ModelBuilder builder = new ModelBuilder();
        Material material = new Material(ColorAttribute.createDiffuse(Color.BLUE));
        model = builder.createBox(5, 5, 5, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
    }

    @Override
    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        modelBatch.begin(camera);

        autoRotate();
        move();
        autoScale();
        updateTransformation();

        modelBatch.render(instance, environment);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        model.dispose();
    }
}
