package org.systempro.project.platformer;

import org.systempro.project.physics2d.PhysicsBody;
import org.systempro.project.physics2d.RectBody;

public class Platform {
    public RectBody hitbox;

    public Platform(RectBody hitbox){

        this.hitbox=hitbox;
        hitbox.getFixture().setUserData(this);
    }
}
