int gridsize = 20;
int count = 0;
Orb[] orbs;
void setup() {
   orbs = new Orb[(gridsize - 1) * (gridsize - 1)];
   size(1080, 1080);
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


void draw() {
   background(255);
   for (int i = 0; i < count; i++) {
      orbs[i].move();
      // orbs[i].display();
   }
   collision();
}

void collision(){
   for (int i = 0; i < count; i++) {
      for (int j = 0; j < count; j++){
         float loc_dist = distance(orbs[i].xpos, orbs[i].ypos, orbs[j].xpos, orbs[j].ypos);
         float collision_dist = orbs[i].orbsize/2 + orbs[j].orbsize/2;
         if (loc_dist <= collision_dist){
            stroke(0);
            line(orbs[i].xpos, orbs[i].ypos, orbs[j].xpos, orbs[j].ypos);
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
   
   public Orb(float x, float y){
      orbsize = random(1,3) * height/20;
      x_speed = prop(random(-1, 1));
      y_speed = prop(random(-1, 1));
      xpos = x;
      ypos = y;
   }
   
   void display(){
      noStroke();
      fill(0, 0, 0, 0);
      ellipse(xpos, ypos, orbsize, orbsize);
   }
   
   void move(){
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

float prop(float value){
   return value * width / 512;
}

float distance(float x1, float y1, float x2, float y2){
   return (float)sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
}

