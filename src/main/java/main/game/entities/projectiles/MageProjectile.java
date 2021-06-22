package main.game.entities.projectiles;

import helix.utils.math.Point;
import helix.utils.math.Vector2;
import main.game.RpgGame;
import main.game.annotations.Damage;
import main.game.annotations.QueueAsset;
import main.game.entities.Projectile;
import main.game.entities.mobs.neutral.Player;
import main.game.entities.utils.RangedAttackInfo;
import main.game.enums.DamageType;

@Damage(DamageType.BLUNT)
public class MageProjectile extends Projectile {
	private static final float MIN_SCALE = 0.75f;
	private static final float MAX_SCALE = 1.25f;

	private float scaleX, scaleY;
	private int scaleChangeSign = 1;
	
	private Player player;
	
	@QueueAsset(ref = "res/sprites/projectile/mage_ball.png")
	public static String SPRITE_REF;

	public MageProjectile(RpgGame game, RangedAttackInfo attack, Point pos, Point destination) {
		super(game, attack, pos, destination, SPRITE_REF);
		
		player = this.getPlayer();
	}

	@Override
	public void step(float delta) {
		super.step(delta);
		
		if(this.getPos().getDistTo(player.getPos()) < this.getWidth() / 2) {
			player.subStat("health", this.attack.amount);
			this.dispose();
			return;
		}
		
		if(scaleX >= MAX_SCALE)
			scaleChangeSign = -1;
		else if(scaleX <= MIN_SCALE)
			scaleChangeSign = 1;
			
		//scaleX = NumberUtils.loop(scaleX + delta, MIN_SCALE, MAX_SCALE).floatValue();
		scaleX += delta * scaleChangeSign;
		scaleY = scaleX;//NumberUtils.loop(scaleY + delta, MIN_SCALE, MAX_SCALE).floatValue();

		//this.destination.setX(player.getPos().getX());
		//this.destination.setY(player.getPos().getY());
		
		
		
		if (this.getSprite() != null)
			this.getSprite().setScale(new Vector2(scaleX, scaleY));
	}
}
