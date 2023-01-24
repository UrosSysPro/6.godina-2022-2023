package org.systempro.project.physics2d;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Object o1=contact.getFixtureA().getUserData();
        Object o2=contact.getFixtureB().getUserData();
        if(o1==null||o2==null)return;
        if(o1 instanceof Collider){
            ((Collider) o1).beginContact(contact.getFixtureA(), contact.getFixtureB());
        }
        if(o2 instanceof Collider){
            ((Collider) o2).beginContact(contact.getFixtureB(), contact.getFixtureA());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Object o1=contact.getFixtureA().getUserData();
        Object o2=contact.getFixtureB().getUserData();
        if(o1==null||o2==null)return;
        if(o1 instanceof Collider){
            ((Collider) o1).endContact(contact.getFixtureA(), contact.getFixtureB());
        }
        if(o2 instanceof Collider){
            ((Collider) o2).endContact(contact.getFixtureB(), contact.getFixtureA());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
