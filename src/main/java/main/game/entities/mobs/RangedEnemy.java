package main.game.entities.mobs;

import java.lang.reflect.InvocationTargetException;

import io.sly.helix.utils.math.Vector2D;
import main.game.EntityManager;
import main.game.annotations.Damage;
import main.game.entities.Projectile;
import main.game.entities.utils.RangedAttackInfo;
import main.game.enums.DamageType;
import main.game.screens.GameScreen;

public abstract class RangedEnemy<ProjectileType extends Projectile> extends Enemy {
	protected static final int SHOOT_ALARM = 1;

	private final Class<ProjectileType> projectileType;

	private boolean can_shoot = true;
	
	public RangedEnemy(EntityManager em, Class<ProjectileType> projectileClass, Vector2D pos) {
		super(em, pos);
		
		this.projectileType = projectileClass;
	}
	
	public ProjectileType fireProjectile(float damage, float speed, Vector2D destination) {
		if(!can_shoot) {
			return null;
		} else {
			ProjectileType proj = this.createProjectile(damage, speed, destination);
			GameScreen.world.addEntity(proj);
			int time = (int)Math.ceil(1f / this.getStat("attack_speed"));
			System.out.println(time);
			this.setAlarm(SHOOT_ALARM, time, () -> {
				can_shoot = true;
			});
			can_shoot = false;
			return proj;
		}
	}
	
	private ProjectileType createProjectile(float damage, float speed, Vector2D destination) {
		ProjectileType newProjectile = null;
		
		// TODO: Potentially make projectiles based on range instead of destination
		// destination = destination.toVector2().getUnitVector().multiply(range);
		
		DamageType projectileDamageType = projectileType.getAnnotation(Damage.class).value();
		
		
		RangedAttackInfo attack = new RangedAttackInfo(projectileDamageType, damage, speed);
		try {
			newProjectile = projectileType.getDeclaredConstructor(EntityManager.class, RangedAttackInfo.class, Vector2D.class, Vector2D.class)
					.newInstance(this.getEntityManager(), attack, this.getPos(), destination);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			System.err.println("Error creating projectile from: " + projectileType.getCanonicalName());
			e.printStackTrace();
		}
		
		return newProjectile;
	}
	
	// Getters and Setters
	
	public boolean canShoot() {
		return can_shoot;
	}
	
	public void setCanShoot(boolean shoot) {
		this.can_shoot = shoot;
	}
}
