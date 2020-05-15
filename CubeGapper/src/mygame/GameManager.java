/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

/**
 *
 * @author panpa
 */

public class GameManager {
    AssetManager assetManager;
    InputManager inputManager;
    Node playerModel;
    Node wallNode;
    Node floorNode;
    Node rootNode;
    Node guiNode;
    int score;
    int speed = 10;
    Scene sc;
    Player p;
    Camera cam;
    BitmapText scoreText;
    
    public GameManager(AssetManager assetManager, Node playerModel, Node rootNode, 
            InputManager inputManager, Camera cam, Node guiNode, BitmapFont guiFont){
        this.assetManager = assetManager;
        this.playerModel = playerModel;
        this.rootNode = rootNode;
        this.inputManager = inputManager;
        this.cam = cam;
        this.guiNode = guiNode;
        
        sc = new Scene(assetManager);
        p = new Player(playerModel, inputManager);
        
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        scoreText = new BitmapText(guiFont, false);
        scoreText.setSize(guiFont.getCharSet().getRenderedSize());
        scoreText.setLocalTranslation(300, scoreText.getLineHeight(), 0);
        scoreText.setColor(ColorRGBA.White);
        guiNode.attachChild(scoreText);
        
        rootNode.attachChild(playerModel);
        initScene();
    }
    
    private void initScene(){
        wallNode = new Node();
        floorNode = new Node();
        rootNode.attachChild(wallNode);
        rootNode.attachChild(floorNode);
        
        playerModel.setLocalTranslation(0, -1, 0);
        for(int i = 0; i < 6; i++){
            int mult = i * -40;
            wallNode.attachChild(sc.makeWall(mult));
            floorNode.attachChild(sc.makeFloor(mult));
            //System.out.println(mult);
        }
        
    }
    
    public void updateScene(float tpf){
        for(int i = 0; i < wallNode.getQuantity(); i++){
            Node wallObstacle = (Node) wallNode.getChild(i);
            checkCollision(wallNode);
            if(!p.isDying){
                wallObstacle.move(0, 0, tpf * 1 * (speed + score));
            }
            
            if(wallObstacle.getLocalTranslation().z > 20){
                sc.resetWallObstacle(wallObstacle);
                score++;
                scoreText.setText("Score: " + score);
            }
            
        }
        for(int i = 0; i < floorNode.getQuantity(); i++){
            Node floorObstacle = (Node) floorNode.getChild(i);
            checkFloorCollision(floorNode, tpf);
            if(!p.isDying){
                floorObstacle.move(0, 0, tpf * 1 * (speed + score));
            }
            
            if(floorObstacle.getLocalTranslation().z > 40){
                sc.resetFloorObstacle(floorObstacle);
            }
        }
        p.update(tpf);
    }
    
    public void updateCamera(){        
        Vector3f v1 = playerModel.getLocalTranslation();
        cam.lookAt(v1, new Vector3f(0, 1, 0));
        cam.setLocation(new Vector3f(v1.x, v1.y + 10, v1.z + 20));
    }
    
    private void checkCollision(Node wallNode){
        CollisionResults results = new CollisionResults();
        wallNode.collideWith(playerModel.getWorldBound(), results);
        if (results.size() > 0) {
            p.isDying = true;
            p.die();
        }
    }
    
    private void checkFloorCollision(Node floorNode, float tpf){
        CollisionResults results = new CollisionResults();
        floorNode.collideWith(playerModel.getWorldBound(), results);
        if(results.size() == 0){
            if(playerModel.getLocalTranslation().y <= -1){
                p.isDying = true;
                p.fallToDie(tpf);
            }
        }
    }
}
