package ca.bcit.comp2522.termproject.secretwonders;

public class Player2Projectile extends Projectile {


    public Player2Projectile() {
        super("fireblast.gif", Constants.PROJECTILE_PLAYER_TWO_WIDTH,
                Constants.PROJECTILE_PLAYER_TWO_HEIGHT, Constants.PROJECTILE_PLAYER_DMG);
    }


    public Player2Projectile(double originX, double originY, double angle) {
        super("fireblast.gif", Constants.PROJECTILE_PLAYER_TWO_WIDTH,
                Constants.PROJECTILE_PLAYER_TWO_HEIGHT, Constants.PROJECTILE_PLAYER_DMG,
                originX - (Constants.PROJECTILE_PLAYER_TWO_WIDTH / 2),
                originY - (Constants.PROJECTILE_PLAYER_TWO_HEIGHT / 2),
                Constants.PROJECTILE_PLAYER_TWO_DELTA_X,
                Constants.PROJECTILE_PLAYER_TWO_DELTA_Y, angle);
    }
}