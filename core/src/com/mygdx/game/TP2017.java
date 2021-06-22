package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class TP2017 extends ApplicationAdapter {
	private ShapeRenderer sh;
	private OrthographicCamera camera;
	private static final float VIRTUAL_WIDTH = 600;
	float x, y, cote;
	private SpriteBatch batch;
	private Sprite sprite;
	private Texture texture;

	public void create() {
		sh = new ShapeRenderer();
		camera = new OrthographicCamera();
		x=100;
		y=10;
		cote=120;
		texture = new Texture(Gdx.files.internal("toad.jpeg"));
		sprite = new Sprite(texture);
		batch = new SpriteBatch();
		sprite.setSize(sprite.getWidth()/3, sprite.getHeight()/3);
		sprite.setCenter (cote / 2 + x, cote / 2 + y);
	}

	@Override
	public void resize(int width, int height) {
		float virtualHeight = VIRTUAL_WIDTH * (float) height / width;
		camera.setToOrtho(false, VIRTUAL_WIDTH, virtualHeight);
	}


	/*private void drawGrid1 () {
		sh.begin(ShapeRenderer.ShapeType.Line);
		sh.setColor(Color.BLACK);
		float x1,y1;
		y1=y;
		for(int i=1; i<=3; i++) {
			x1=x;
			for (int j=1; j<=3; j++) {
				sh.rect(x1, y1, cote, cote);
				x1+=cote;
			}
			y1+=cote;
		}
		sh.end();
	}*/

	private void drawGrid2 () {
		sh.begin(ShapeRenderer.ShapeType.Line);
		sh.setColor(Color.BLACK);
		float pos,l;
		l=3*cote;
		pos=x;
		for(int i=1; i<=4; i++) {
			sh.line(pos,y,pos,y+l);
			pos+=cote;
		}

		pos=y;
		for(int i=1; i<=4; i++) {
			sh.line(x,pos,x+l,pos);
			pos+=cote;
		}
		sh.end();
	}

	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sh.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		this.drawGrid2();
		batch.begin();
		sprite.draw(batch);
		batch.end();
		this.handlePosition1();
		this.handlePosition2();
		this.handleInput();
		this.zoom();
		camera.update();
	}

	private void handlePosition1(){
		float xMin, xMax, yMin, yMax;
		xMin = cote;
		xMax = cote * 3;

		yMin = 0;
		yMax = cote * 2;

		sprite.setX(MathUtils.clamp(sprite.getX(),xMin , xMax));
		sprite.setY(MathUtils.clamp(sprite.getY(), yMin, yMax));

		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
			sprite.translate(cote,0);
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
			sprite.translate(-cote,0);
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
			sprite.translate(0,cote);
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
			sprite.translate(0,-cote);
	}

	private void handlePosition2(){
		//9
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_9))
			sprite.translate(cote * 3,cote * 3);
		//3
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_3))
			sprite.translate(cote * 2,0);
		//7
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_7))
			sprite.translate(0,cote * 2);
		//8
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_8))
			sprite.translate(cote ,cote * 3);
		//5
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_5))
			sprite.translate(cote ,cote);
		//6
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_6))
			sprite.translate(cote * 2 ,cote);
		//2
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2))
			sprite.translate(cote,0);
	}

	public void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.T)) {
			camera.rotate(16);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			camera.rotate(-16);
		}
	}

	public void zoom() {
		camera.viewportWidth *= camera.zoom;
		camera.viewportHeight *= camera.zoom;
		if(Gdx.input.isKeyJustPressed(Input.Keys.I)) {
			camera.zoom += 0.002;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.O)) {
			camera.zoom -= 0.002;
		}
		camera.viewportWidth *= camera.zoom;
		camera.viewportHeight *= camera.zoom;
	}

	public void dispose() {
		sh.dispose();
		batch.dispose();
		texture.dispose();
	}
}
