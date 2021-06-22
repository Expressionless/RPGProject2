package main.launch;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;

import helix.game.BaseGame;
import main.game.RpgGame;

public class Boot {

	public static void main(String[] args) {

		BaseGame game = new RpgGame();
		new Lwjgl3Application(game, game.config);
	}
}