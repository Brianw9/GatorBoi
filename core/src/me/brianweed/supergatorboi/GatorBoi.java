package me.brianweed.supergatorboi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class GatorBoi extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;

	Texture[] birds;
	int flapState = 0;
	float birdY = 0;
	float velocity;

	int gameState = 0;
	float gravity = 2;
	float tubeGap = 400;
	float bottomTubeY = 0;
	float topTubeY = 0;
	Random randomGen;

	float maxTubeOffset;
	float tubeVelocity = 4;
	int numberOfTubes = 4;
    float[] tubeX = new float[numberOfTubes];
    float[] tubeOffset = new float[numberOfTubes];
	float distanceBetweenTubes;

	Texture bottomtube;
	Texture toptube;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
        birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

        bottomtube = new Texture("bottomtube.png");
        toptube = new Texture("toptube.png");


        randomGen = new Random();
        maxTubeOffset = Gdx.graphics.getHeight() /2 - tubeGap / 2 - 100;

        bottomTubeY = 0 - bottomtube.getHeight() / 2;
        topTubeY = bottomTubeY + tubeGap;

        distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;

        for (int i = 0; i < numberOfTubes; i++) {

            tubeX[i] = Gdx.graphics.getWidth() / 2 - toptube.getWidth() / 2 + i * distanceBetweenTubes;


        }



	}

	@Override
	public void render () {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState != 0){

            if(Gdx.input.justTouched()) {
                velocity = -30;
            }

            for (int i = 0; i < numberOfTubes; i++) {

                if (tubeX[i] < - toptube.getWidth()) {
                    tubeX[i]  += numberOfTubes * distanceBetweenTubes;
                    tubeOffset[i] = (randomGen.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - tubeGap - 200);
                } else {
                    tubeX[i] = tubeX[i] - tubeVelocity;
                }

                batch.draw(toptube, tubeX[i], Gdx.graphics.getHeight() / 2  + tubeGap / 2 + tubeOffset[i]);
                batch.draw(bottomtube, tubeX[i], Gdx.graphics.getHeight() / 2 - tubeGap / 2 - bottomtube.getHeight() + tubeOffset[i]);

            }


            if (birdY > 0 || velocity < 0) {
                velocity = velocity + gravity;
                birdY -= velocity;
            }



        } else {

            if (Gdx.input.justTouched()) {

                gameState = 1;
            }
        }

        if (flapState == 0){
            flapState = 1;
        } else {
            flapState = 0;
        }

        batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);
        batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
