package main.game.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import main.game.enums.DamageType;

/**
 * Damage annotation to mark a field as the damagetype in a projectile
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Damage {

	public DamageType value() default DamageType.BLUNT;
}
