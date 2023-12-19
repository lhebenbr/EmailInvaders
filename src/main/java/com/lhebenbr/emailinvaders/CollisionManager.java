package com.lhebenbr.emailinvaders;


import com.lhebenbr.emailinvaders.model.Entity;

import java.util.List;


public class CollisionManager {

    public static boolean checkCollision(Entity entity1, Entity entity2) {
        return entity1.getX() < entity2.getX() + entity2.getWidth()
                && entity1.getX() + entity1.getWidth() > entity2.getX()
                && entity1.getY() < entity2.getY() + entity2.getHeight()
                && entity1.getY() + entity1.getHeight() > entity2.getY();
    }

    public static boolean checkCollision(Entity entity1, List<Entity> entities) {
        for (Entity entity2 : entities) {
            if (checkCollision(entity1, entity2)) {
                return true;
            }
        }
        return false;
    }

}
