/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.Random;

/**
 *
 * @author panpa
 */
public class Scene {
    
    AssetManager assetManager;
    int wallGap = 135;
    
    //int wallAxis = randomInt(-20, 20);
    
    public Scene(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    public Node makeWall(int multiplier) {
        Node wallNode = new Node();
        Box      wallShape      = new Box(130, 100, 2);
        Geometry wall1 = new Geometry("Box", wallShape);
        Material wallMat   = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        wallMat.setColor("Color", ColorRGBA.Red);
        wall1.setMaterial(wallMat);
        
        Geometry wall2 = new Geometry("Box", wallShape);
        wall2.setMaterial(wallMat);
        

        wallNode.attachChild(wall1);
        wallNode.attachChild(wall2);
        wall1.setLocalTranslation(-135, 0, 0);
        wall2.setLocalTranslation(wallGap, 0, 0);
        wallNode.setLocalTranslation(randomInt(-10, 10), 98, multiplier - 30);
        return wallNode;
    }
    
    public Node makeFloor(int multiplier){
        Node floorNode = new Node();
        Box floorShape = new Box(260, 2, 15);
        Geometry floor = new Geometry("Box", floorShape);
        Material fMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        fMat.setColor("Color", ColorRGBA.randomColor());
        
        floor.setMaterial(fMat);
        floorNode.setLocalTranslation(0, -4, multiplier);
        floorNode.attachChild(floor);
        return floorNode;
    }
    
    public Node resetWallObstacle(Node obstacle){
        
        obstacle.setLocalTranslation(randomInt(-10, 10), 98, -200);
        return obstacle;
    }
    
    public Node resetFloorObstacle(Node obstacle){
        
        obstacle.setLocalTranslation(0, -4, -195);
        return obstacle;
    }
    
    public  int randomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
