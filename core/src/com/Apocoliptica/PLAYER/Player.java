/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Apocoliptica.PLAYER;

import MAP.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michael
 */
public class Player 
{
    public PerspectiveCamera camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public float x,y,z, camX, camY, camZ, fy, neutZ, neutX, neutY;
    public Map map;
    public Vector3 tmp = new Vector3();
    public float degreesPerPixel = 0.5f;
    //FirstPersonCameraController FPC;
    public String facing="NORTH";
    float animTime, moveTime;
    final float speed=(float).5; //.45;
    public float shiftX,shiftY=0;//1.7f;
    public boolean fallen=false;
    
    public Player()
    {
        x=y=(float)10;//10.2;//10;//3;
        z=(float) 1;
        camX=0;
        camY=540;
        camZ=-180;
        animTime=0;
        moveTime=0;
        //camera.position.set(0f,0f,25f);
        //camera.lookAt(0f,0f,25f);
        
        camera.position.set(x,y,z);
        camera.lookAt(camX, camY, camZ);//234  -62   0
        fy=getCameraRotation();
        neutZ=(float)0;//-0.017982937; //;//camera.direction.z;//0.9481552
        neutX=(float)0;//0;//0.013862649;
        neutY=(float)1;//0.9998383;//0.31750506;

        //System.out.println(getCameraRotation());
        // Near and Far (plane) repesent the minimum and maximum ranges of the camera in, um, units
        //camera.near = 0.1f; 
        //camera.far = 100.0f;
        //FPC=new FirstPersonCameraController(camera);
    }
    
    public void gravity()
    {
//        if(check()==false) 
//            return;
        animTime+=Gdx.graphics.getDeltaTime()*1000;
        //animTime+=TimeUtils.millis();
        if(animTime>100)
        {
            animTime=0;
            z-=.15;
            cameraShift();
            camera.position.set(x-shiftX,y-shiftY,z+.2f);
            camera.update();
        }
    }
    
//    public boolean check()
//    {
//        if(facing.equalsIgnoreCase("SOUTH"))
//                {
//                    System.out.println("SOUTH");
//                    if( map.world[(int)(x+.5)][(int)y][(int)z]!=' ' && x%1!=0 )// && world[(int)(player.x+.5)][y][z]==' ') )//|| world[(int)(player.x+.5)][y][z]==' ' )
//                    {
//                        return true;
//                    }
//                    
//                }
//                else if(player.facing.equalsIgnoreCase("NORTH"))
//                {
//                    System.out.println("NORTH");
//                    if( world[(int)(player.x+.5)][y][z]!=' ' && player.x%1!=0 )// && world[(int)(player.x+.5)][y][z]==' ') )//|| world[(int)(player.x+.5)][y][z]==' ' )
//                    {
//                        return false;
//                    }    
//                }
//                else if(player.facing.equalsIgnoreCase("EAST"))
//                {
//                    System.out.println("EAST");
//                    if( world[x][(int)(player.y+.5)][z]!=' ' && player.y%1!=0 )// && world[(int)(player.x+.5)][y][z]==' ') )//|| world[(int)(player.x+.5)][y][z]==' ' )
//                    {
//                        return false;
//                    }    
//                }
//                else if(player.facing.equalsIgnoreCase("WEST"))
//                {
//                    System.out.println("WEST");
//                    if( world[x][(int)(player.y+.5)][z]!=' ' && player.y%1!=0 )// && world[(int)(player.x+.5)][y][z]==' ') )//|| world[(int)(player.x+.5)][y][z]==' ' )
//                    {
//                        return false;
//                    }    
//                }
//    }

    
    public void fire(int timer, Bullet bullet, ModelBatch MB, Environment enviroment)
    {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(timer<=0)
            return;
        bullet.move(MB, enviroment);
        if(map.world[(int)bullet.x][(int)bullet.y][(int)bullet.z]=='b')
        {
            bullet.count=-1;
            map.world[(int)bullet.x][(int)bullet.y][(int)bullet.z]=' ';
        }
       
    }
    
    public void respawn()
    {

        map.world[8][5][1]='b';
        map.world[8][5][2]='b';
        map.world[8][5][3]='b';
        map.world[10][5][1]='b';
        map.world[10][5][2]='b';
        map.world[10][5][3]='b';
        map.world[12][5][1]='b';
        map.world[12][5][2]='b';
        map.world[12][5][3]='b';

        map.world[5][8][1]='b';
        map.world[5][8][2]='b';
        map.world[5][8][3]='b';
        map.world[5][10][1]='b';
        map.world[5][10][2]='b';
        map.world[5][10][3]='b';
        map.world[5][12][1]='b';
        map.world[5][12][2]='b';
        map.world[5][12][3]='b';

        map.world[8][15][1]='b';
        map.world[8][15][2]='b';
        map.world[8][15][3]='b';
        map.world[10][15][1]='b';
        map.world[10][15][2]='b';
        map.world[10][15][3]='b';
        map.world[12][15][1]='b';
        map.world[12][15][2]='b';
        map.world[12][15][3]='b';
        
        
        map.world[15][8][1]='b';
        map.world[15][8][2]='b';
        map.world[15][8][3]='b';
        map.world[15][10][1]='b';
        map.world[15][10][2]='b';
        map.world[15][10][3]='b';
        map.world[15][12][1]='b';
        map.world[15][12][2]='b';
        map.world[15][12][3]='b';      
        
    }
    public void setMap(Map temp)
    {
        map=temp;        
    }
    
    public void move(char dir) //W=1 A=2 S=3 D=0
    {
        animTime=0;
        int move=-100;
        //change dir to match facing
        if(facing.equals("NORTH"))
        {
            if(dir=='w')
            {
                move=1;
            }
            else if('a'==dir)
            {
                move=2;
            }
            else if('s'==dir)
            {
                move=3;
            }
            else if('d'==dir)
            {
                move=0;
            }
        }
        else if(facing.equals("EAST"))
        {
            if('w'==dir)
            {
                move=0;//move right
            }
            else if('a'==dir)
            {
                move=1;//move up
            }
            else if('s'==dir)
            {
                move=2;//
            }
            else if('d'==dir)
            {
                move=3;
            }
        }
        else if(facing.equals("SOUTH"))
        {
            if('w'==dir)
            {
                move=3;
            }
            else if('a'==dir)
            {
                move=0;
            }
            else if('s'==dir)
            {
                move=1;
            }
            else if('d'==dir)
            {
                move=2;
            }
        }
        else if(facing.equals("WEST"))
        {
            if('w'==dir)
            {
                move=2;
            }
            else if('a'==dir)
            {
                move=3;
            }
            else if('s'==dir)
            {
                move=0;
            }
            else if('d'==dir)
            {
                move=1;
            }
        }
        if(' '==dir)
        {
            move=4;
        }
        else if('y'==dir)
        {
            move=5;
        }
        if(move==-100)
        {
            Gdx.app.exit();
        }
        //move
        if(move==0 && map.legal((int)(x+speed),(int)(y),(int)z)==true)//right has issues not going far enough
        {
                x+=speed;
                //camera.translate(1,0,0);    
        }else if(move==1 && map.legal((int)(x),(int)(y+speed),(int)z)==true)//has issues not going far enough
        {
                y+=speed;
            //camera.translate(0,1,0);
        }else if(move==2 && map.legal((int)(x-(speed)),(int)(y),(int)z)==true)
        {
                x-=speed;
            //camera.translate(-1,0,0);
        }
        else if(move==3 && map.legal((int)(x),(int)(y-(speed)),(int)z)==true)
        {
                y-=speed;
            //camera.translate(0,-1,0);
        }else if(move==4 && map.legal((int)x,(int)y,(int)z+2)==true)
        {
                z+=1.5;
            //camera.translate(0,0,1);
        }else if(move==5 && map.legal((int)x,(int)y,(int)z-2)==true)
        {  
                z-=2;
            //camera.translate(0, 0, -1);
        }
        cameraShift();
        camera.position.set(x-shiftX,y-shiftY,z+.2f);
        camera.update();
    }
    
    public void setDir(float a, float b)
    {
        float loc=getCameraRotation();
        if( loc>=fy-45 && loc<=fy+45)
        {
            facing="NORTH";
            shiftX=0;//1.7f;
//            if(facing.equalsIgnoreCase("NORTH"))
//            {
//                System.out.println("NORTH");
//                if( map.world[(int)(x)][(int)(y+2)][(int)z]!=' ' || map.world[(int)(x)][(int)(y+1)][(int)z]!=' ')// && world[(int)(player.x+.5)][y][z]==' ') )//|| world[(int)(player.x+.5)][y][z]==' ' )
//                {
//                    shiftY=1.5f;
//                }    
//                else
//                    shiftY=0;
//            }
            
        }
        else if( loc>=fy-135 && loc<=fy-45 )
        {
            facing="EAST";
            shiftX=1.7f;//1.7f;
            shiftY=0;//1.7f;
        }
        else if( loc>=fy+45 && loc<=fy+135 )
        {
            facing="WEST";
            shiftX=-1.7f;//1.7f;
            shiftY=0;//1.7f;
        }
        else 
        {
            shiftX=.0f;//1.7f;
            shiftY=-.0f;
            facing="SOUTH";
        }
        //System.out.println(getCameraRotation());
        camera.position.set(x-shiftX,y-shiftY,z+.2f);
        camera.update();
    }
    public float getCameraRotation()
    {                       
        float camAngle = -(float)Math.atan2(camera.up.x, camera.up.y)*MathUtils.radiansToDegrees + 180;         
        return camAngle;
    }
    
    public boolean canMove()
    {
        animTime+=Gdx.graphics.getDeltaTime()*1000;
        //animTime+=TimeUtils.millis();
        if(animTime>100)
        {
            return true;
        }
        return false;
    }
    
    public void cameraShift()
    {
        int closest=16;
        int tempX=0,tempY=0;
        if(facing.equalsIgnoreCase("NORTH"))
        {
            System.out.println(shiftY);
            shiftY=0;
            shiftX=0;
            for(int a=(int)z; a<(int)z+15; ++a)
            {
                if(a>map.worldZ) 
                    break;
                for(int b=(int)x-15; b<(int)x+15; ++b)
                {
                    if(b<0) 
                        b=0;
                    if(b>map.worldX)
                       break;
                    for(int i=(int)y; i<(int)y+15; ++i)
                    {    
                        if(i>map.worldY)
                            break;
                        //System.out.println(b+"      "+i+"   "+a);
                        if( map.world[b][i][a]!=' ') 
                        {
                            tempX=b;
                            tempY=i;   
                            //System.out.println(tempX+"      "+x+"   "+tempY+"       "+y+"   "+a);
                            if(closest> ( Math.abs(tempY-(int)(y+.5))+Math.abs(tempX-(int)x) ))
                            {
                                closest=( Math.abs(tempY-(int)(y+.5))+Math.abs(tempX-(int)(x)) );
                            }
                        }    
                    }
                }
            }
            //System.out.println(Math.abs(tempY-(int)(y+.5))+"             "+(y+.5)+"             "+Math.abs(tempX-(int)x)+"             "+x);
             //System.out.println(b+"   "+i+"   "+a);
            //System.out.println(closest);
        shiftY=(float)(16-(closest))/10f;
        if(shiftY>1.5f) 
            shiftY=1.5f;
        shiftX=(float)(16-(16))/10f;
        if(shiftX>1.5f) 
            shiftX=1.5f;
        }
        else if(facing.equalsIgnoreCase("SOUTH"))
        {
            System.out.println(shiftY);
            shiftY=0;
            shiftX=0;
            for(int a=(int)z; a<(int)z+15; ++a)
            {
                if(a>map.worldZ) 
                    break;
                for(int b=(int)x-15; b<(int)x+15; ++b)
                {
                    if(b<0) 
                        b=0;
                    if(b>map.worldX)
                       break;
                    for(int i=(int)y-15; i<(int)y; ++i)
                    {    
                        if(i>=0 && i<map.worldY)
                        {
                            //System.out.println(b+"      "+i+"   "+a);
                            if( map.world[b][i][a]!=' ') 
                            {
                                tempX=b;
                                tempY=i;   
                                //System.out.println(tempX+"      "+x+"   "+tempY+"       "+y+"   "+a);
                                if(closest> ( Math.abs(tempY-(int)(y+.5))+Math.abs(tempX-(int)x) ))
                                {
                                    closest=( Math.abs(tempY-(int)(y+.5))+Math.abs(tempX-(int)(x)) );
                                    System.out.println(tempX+"      "+x+"   "+tempY+"       "+y+"   "+closest);
                                }
                            }
                        }    
                    }
                }
            }
        shiftY=(float)(16-(closest))/10f;
        shiftY=shiftY*(-1);
        if(shiftY<-1.5f || closest<2) 
            shiftY=-1.5f;
        shiftX=(float)(16-(16))/10f;
        if(shiftX>1.5f) 
            shiftX=1.5f;
        }
        else if(facing.equalsIgnoreCase("EAST"))
        {
            System.out.println(shiftX);
            shiftY=0;
            shiftX=0;
            for(int a=(int)z; a<(int)z+15; ++a)
            {
                if(a>map.worldZ) 
                    break;
                for(int b=(int)x-15; b<(int)x+15; ++b)
                {
                    if(b<0) 
                        b=0;
                    if(b>map.worldX)
                       break;
                    for(int i=(int)y; i<(int)y+15; ++i)
                    {    
                        if(i>=0 && i<map.worldY)
                        {
                            //System.out.println(b+"      "+i+"   "+a);
                            if( map.world[b][i][a]!=' ') 
                            {
                                tempX=b;
                                tempY=i;   
                                //System.out.println(tempX+"      "+x+"   "+tempY+"       "+y+"   "+a);
                                if(closest> ( Math.abs(tempY-(int)(y+.5))+Math.abs(tempX-(int)x) ))
                                {
                                    closest=( Math.abs(tempY-(int)(y+.5))+Math.abs(tempX-(int)(x)) );
                                    System.out.println(tempX+"      "+x+"   "+tempY+"       "+y+"   "+closest);
                                }
                            }
                        }    
                    }
                }
            }
        shiftY=(float)(16-(16))/10f;
        shiftX=(float)(16-(closest))/10f;
        if(shiftX>1.5f) 
            shiftX=1.5f;
        }
        else if(facing.equalsIgnoreCase("WEST"))
        {
            System.out.println(shiftX);
            shiftY=0;
            shiftX=0;
            for(int a=(int)z; a<(int)z+15; ++a)
            {
                if(a>map.worldZ) 
                    break;
                for(int b=(int)x-15; b<(int)x+15; ++b)
                {
                    if(b<0) 
                        b=0;
                    if(b>map.worldX)
                       break;
                    for(int i=(int)y-15; i<(int)y; ++i)
                    {    
                        if(i>=0 && i<map.worldY)
                        {
                            //System.out.println(b+"      "+i+"   "+a);
                            if( map.world[b][i][a]!=' ') 
                            {
                                tempX=b;
                                tempY=i;   
                                //System.out.println(tempX+"      "+x+"   "+tempY+"       "+y+"   "+a);
                                if(closest> ( Math.abs(tempY-(int)(y+.5))+Math.abs(tempX-(int)x) ))
                                {
                                    closest=( Math.abs(tempY-(int)(y+.5))+Math.abs(tempX-(int)(x)) );
                                    System.out.println(tempX+"      "+x+"   "+tempY+"       "+y+"   "+closest);
                                }
                            }
                        }    
                    }
                }
            }
        shiftY=(float)(16-(16))/10f;
        shiftX=(float)(16-(closest))/10f;
        if(shiftX>1.5f) 
            shiftX=1.5f;
        shiftX=shiftX*(-1);
        }
        camera.position.set(x-shiftX,y-shiftY,z+.2f);
        camera.update();
    }
    
    public String setAllDir()
    {
        float loc=getCameraRotation();
        String dir="";
        
        if( loc>=fy-22.5 && loc<=fy+22.5)
        {
            dir="N";          
        }
        else if( loc>=fy-67.5 && loc<=fy-22.5)
        {
            dir="NE";          
        }
        else if( loc<=fy-67.5 && loc>=fy-112.5)
        {
            dir="E";          
        } 
        else if( loc<=fy-112.5 && loc>=fy-157.5)
        {
            dir="SE";          
        }
        else if( loc>=fy+22.5 && loc<=fy+67.5)
        {
            dir="NW";          
        }
        else if( loc>=fy+67.5 && loc<=fy+112.5)
        {
            dir="W";          
        }
        else if( loc>=fy+112.5 && loc<=fy+157.5)
        {
            dir="SW";          
        }
        else 
        {
            dir="S";
        }
        System.out.println(dir);
        return dir;
    }
}
