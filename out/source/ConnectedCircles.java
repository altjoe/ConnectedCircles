import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ConnectedCircles extends PApplet {

int gridsize = 10;
int count = 0;
Orb[] orbs;
ArrayList<Vanishing_Line> lines = new ArrayList<Vanishing_Line>();
public void setup() {
   orbs = new Orb[(gridsize - 1) * (gridsize - 1)];
   
   background(255);
   count = 0;
   for (int i = 1; i < gridsize; i++) { 
      for (int j = 1; j < gridsize; j++) {
         float x = width*i/gridsize;
         float y = height*j/gridsize;
         Orb orb = new Orb(x,y);
         orbs[count] = orb;
         orbs[count].display();
         count += 1;
      }
   }
}


public void draw() {
   background(255);
   for (int i = 0; i < count; i++) {
      orbs[i].move();
   }
   collision();
   for (int i = lines.size() - 1; i >= 0; i--){
      Vanishing_Line line = lines.get(i);
      if (!line.finished) {
         line.display();
      } else {
         lines.remove(i);
      }
   }
}

public void collision(){
   for (int i = 0; i < count; i++) {
      for (int j = 0; j < count; j++){
         float loc_dist = distance(orbs[i].xpos, orbs[i].ypos, orbs[j].xpos, orbs[j].ypos);
         float collision_dist = orbs[i].orbsize/2 + orbs[j].orbsize/2;
         if (loc_dist <= collision_dist){
            Vanishing_Line line = new Vanishing_Line(orbs[i].xpos, orbs[i].ypos, orbs[j].xpos, orbs[j].ypos);
            lines.add(line);
         }
      }
   }
}

class Orb {
   float orbsize;
   float x_speed;
   float y_speed;
   float xpos;
   float ypos;
   float index;
   
   public Orb(float x, float y){
      orbsize = random(1,3) * height/prop(gridsize * 2);
      x_speed = prop(random(-1, 1));
      y_speed = prop(random(-1, 1));
      xpos = x;
      ypos = y;
   }
   
   public void display(){
      noStroke();
      fill(0, 0, 0, 0);
      ellipse(xpos, ypos, orbsize, orbsize);
   }
   
   public void move(){
      if (xpos + x_speed + orbsize/2 > width || xpos + x_speed - orbsize/2 < 0){
         x_speed *= -1;
      }
      if (ypos + y_speed + orbsize/2 > height || ypos + y_speed - orbsize/2 < 0){
         y_speed *= -1;
      }
      xpos = xpos + x_speed;
      ypos = ypos + y_speed;
   } 
}

class Vanishing_Line {
   float x0;
   float y0;
   float x1; 
   float y1;
   float frames_alive;
   float decay;
   boolean finished = false;

   public Vanishing_Line(float x0pos, float y0pos, float x1pos, float y1pos) {
      x0 = x0pos;
      y0 = y0pos;
      x1 = x1pos;
      y1 = y1pos;
      frames_alive = 90;
      decay = frames_alive;
   }

   public void display(){
      if (decay >= 0){
         float alpha = decay / frames_alive;
         stroke(0, 0, 0, alpha * 255);
         line(x0, y0, x1, y1);
         decay -= 1;
      } else {
         finished = true;
      }
   }
}

public float prop(float value){
   return value * width / 512;
}

public float distance(float x1, float y1, float x2, float y2){
   return (float)sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
}

  public void settings() {  size(512, 512); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ConnectedCircles" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
