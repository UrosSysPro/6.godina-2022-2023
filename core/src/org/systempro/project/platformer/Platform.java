package org.systempro.project.platformer;

import org.systempro.project.physics2d.PhysicsBody;
import org.systempro.project.physics2d.RectBody;

public class Platform {
    public PhysicsBody hitbox;

    public Platform(PhysicsBody hitbox){
        this.hitbox=hitbox;
    }
}
