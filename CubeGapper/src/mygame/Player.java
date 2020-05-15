/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.scene.Node;
import com.jme3.input.controls.ActionListener;

/**
 *
 * @author panpa
 */
public class Player implements ActionListener {
    
    private final InputManager inputManager;
    Node playerModel;
    boolean left = false;
    boolean right = false;
    boolean space = false;
    boolean isJumping = false;
    boolean isDying = false;
    boolean jumpAction = false;
    int speed = 10;
    int jumpCharge = 0;
    float jumpHeight = 5.1f;
    float playerPos;
    float floorHeight = -1;
    
    public Player(Node playerModel, InputManager inputManager){
        this.playerModel = playerModel;
        this.inputManager = inputManager;
        setUpKeys();
    }
    
    private void setUpKeys(){
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        
        inputManager.addListener(this, "Left", "Right", "Space");
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("Left")){
            left = isPressed;
        }
        if(name.equals("Right")){
            right = isPressed;
        }
        if(name.equals("Space")){
            space = isPressed;
        }
    }
    public void update(float tpf){
        playerPos = playerModel.getLocalTranslation().y;
        if(right){
            playerModel.move(tpf * speed * 1, 0, 0);
        }
        if(left){
            playerModel.move(tpf * speed * -1, 0, 0);
        }
        if(space && playerModel.getLocalTranslation().y == -1){
            isJumping = true;
        }
        updateJump(tpf);
    }
    
    private void updateJump(float tpf){
        //System.out.println(isJumping);
        //System.out.println(jumpAction);
        if(isJumping){
            playerModel.move(0, tpf * speed, 0);
            isDying = false;
            //System.out.println(playerPos);  
            
            if(playerPos >= jumpHeight){

                isJumping = false;
                
            }
        }
        else if(!isJumping && playerPos > -1){
            playerModel.move(0, tpf * -speed, 0);
        }

        else if (!isDying){
            playerModel.setLocalTranslation(playerModel.getLocalTranslation().x, -1, playerModel.getLocalTranslation().z);
        }
        //System.out.println(isDying);
        //System.out.println(playerPos);
    }
    
    public void die(){
        
    }
    
    public void fallToDie(float tpf){
        //System.out.println("you should be falling");
        playerModel.move(0, tpf * -5, 0);
    }
}
