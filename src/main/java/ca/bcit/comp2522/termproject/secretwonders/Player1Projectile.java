package ca.bcit.comp2522.termproject.secretwonders;

public class Player1Projectile extends Projectile {

    public Player1Projectile() {
        super("short_spear.png", Constants.PROJECTILE_PLAYER_ONE_WIDTH,
                Constants.PROJECTILE_PLAYER_ONE_HEIGHT, Constants.PROJECTILE_PLAYER_DMG);
    }

    public Player1Projectile(double originX, double originY, double angle) {
        super("short_spear.png", Constants.PROJECTILE_PLAYER_ONE_WIDTH,
                Constants.PROJECTILE_PLAYER_ONE_HEIGHT, Constants.PROJECTILE_PLAYER_DMG,
                originX, originY, Constants.PROJECTILE_PLAYER_ONE_DELTA_X,
                Constants.PROJECTILE_PLAYER_ONE_DELTA_Y, angle);
    }
}