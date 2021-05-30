
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Henke
 */
public class rect_frame extends javax.swing.JFrame {

    private Timer t1;
    private int delay = 15;
    private int FRAME_WIDTH;
    private int CENTER;
    
    private Rect [] r;
    private int nr_of_rects;
    private int rect_size;
    private int rect_width = 70;
    
    private double rotate_speed = 0.01; //0.008
    private double solve_ratio = 1.005;
    private double resolve_val = -0.35;  //-0.4  
    private double bounce_gravity = 0.99;
    private double part_size = 15;
    private boolean collision = true;
    private boolean speed_add = false;
    private boolean rand_add = false;
    private boolean straight_resolve = false;
    
    
    private int counter = 0;
    private int mod = 25;
    
    public rect_frame() 
    {
        initComponents();
        init();
        init_rect();
        act_perf(delay);
    }
    
    public void resolve_rect_rect(int ix, int ix2, double angle , double x_off, double y_off)
    {
        double ix_ratio = r[ix].get_width()/(r[ix].get_width()+r[ix2].get_width());
        double ix2_ratio = r[ix2].get_width()/(r[ix].get_width()+r[ix2].get_width());
        boolean col = false;
        x_off = Math.abs(x_off);
        y_off = Math.abs(y_off);
        x_off *= 3.25;   //0.6
        y_off *= 0.025;
        
        if(r[ix].get_gravity_height() <= 0.05 || r[ix2].get_gravity_height() <= 0.05)
        {
            x_off *= 0.1;
        }
        
        do
        {
            
            
            
            col = false;
            if(r[ix].get_center_x() <= r[ix2].get_center_x())
            {
                r[ix].set_x0(r[ix].get_x0()-x_off*ix_ratio);
                r[ix2].set_x0(r[ix2].get_x0()+x_off*ix2_ratio);
            }
            else
            {
                r[ix].set_x0(r[ix].get_x0()+x_off*ix_ratio);
                r[ix2].set_x0(r[ix2].get_x0()-x_off*ix2_ratio);
            }
            
            if(r[ix].get_center_y() <= r[ix2].get_center_y())
            {
                r[ix].set_y0(r[ix].get_y0()-y_off*ix_ratio);
                r[ix2].set_y0(r[ix2].get_y0()+y_off*ix2_ratio);
            }
            else
            {
                r[ix].set_y0(r[ix].get_y0()+y_off*ix_ratio);
                r[ix2].set_y0(r[ix2].get_y0()-y_off*ix2_ratio);
            }
            
                  
            
            
            for(int k = 0; k < 4; k++)
            {
                int k0 = k;
                int k1 = (k+1) % 4;
                double x0 = r[ix].get_x_corner(k0);
                double y0 = r[ix].get_y_corner(k0);
                double x1 = r[ix].get_x_corner(k1);
                double y1 = r[ix].get_y_corner(k1);
                double dx = x1 - x0;
                double dy = y1 - y0;
                double part = part_size;
                for(double p = 0; p < part; p++)
                {
                    double x_cord = x0 + p/part * dx;
                    double y_cord = y0 + p/part * dy;
                    
                    for (int m = 0; m < 4; m++) 
                    {
                        int m0 = m;
                        int m1 = (m + 1) % 4;
                        double x0_ = r[ix2].get_x_corner(m0);
                        double y0_ = r[ix2].get_y_corner(m0);
                        double x1_ = r[ix2].get_x_corner(m1);
                        double y1_ = r[ix2].get_y_corner(m1);
                        double dx_ = x1_ - x0_;
                        double dy_ = y1_ - y0_;
                        
                        for(double p2 = 0; p2 < part; p2++)
                        {
                            double x2_cord = x0_ +p2/part * dx_;
                            double y2_cord = y0_ +p2/part * dy_;
                            
                            if (x2_cord > x_cord && x2_cord < x_cord) 
                            {
                                if (y2_cord > y_cord && y2_cord < y_cord) 
                                {
                                    col = true;
                                }
                            }
                            
                        }
                        
                    }
                                
                }
            }
        }
        while(col);
        
        
            if(r[ix].get_center_x() <= r[ix2].get_center_x())
            {
                r[ix].set_x0(r[ix].get_x0()+x_off*ix_ratio*resolve_val);
                r[ix2].set_x0(r[ix2].get_x0()-x_off*ix2_ratio*resolve_val);
            }
            else
            {
                r[ix].set_x0(r[ix].get_x0()-x_off*ix_ratio*resolve_val);
                r[ix2].set_x0(r[ix2].get_x0()+x_off*ix2_ratio*resolve_val);
            }
            
            if(r[ix].get_center_y() <= r[ix2].get_center_y())
            {
                r[ix].set_y0(r[ix].get_y0()+y_off*ix_ratio*resolve_val);
                r[ix2].set_y0(r[ix2].get_y0()-y_off*ix2_ratio*resolve_val);
            }
            else
            {
                r[ix].set_y0(r[ix].get_y0()-y_off*ix_ratio*resolve_val);
                r[ix2].set_y0(r[ix2].get_y0()+y_off*ix2_ratio*resolve_val);
            }
            
            
            
    }
    
    public void check_rect_rect_col(int ix, int ix2)
    {
        

            for(int k = 0; k < 4; k++)
                    {
                        int k0 = k;
                        int k1 = (k+1) % 4;
                        double x0 = r[ix].get_x_corner(k0);
                        double y0 = r[ix].get_y_corner(k0);
                        double x1 = r[ix].get_x_corner(k1);
                        double y1 = r[ix].get_y_corner(k1);
                        double dx = x1 - x0;
                        double dy = y1 - y0;
                    
                        double part = part_size;
                        for(double p = 0; p < part; p++)
                        {
                            double x_cord = x0 + p/part * dx;
                            double y_cord = y0 + p/part * dy;
                            
                                for(int m = 0; m < 4; m++)
                                {
                                    int m0 = m;
                                    int m1 = (m+1) % 4;
                                    double x0_ = r[ix2].get_x_corner(m0);
                                    double y0_ = r[ix2].get_y_corner(m0);
                                    double x1_ = r[ix2].get_x_corner(m1);
                                    double y1_ = r[ix2].get_y_corner(m1);
                                    double dx_ = x1_ - x0_;
                                    double dy_ = y1_ - y0_;
                                
                                    for(double p2 = 0; p2 < part; p2++)
                                    {
                                        double x2_cord = x0_ +p2/part * dx_;
                                        double y2_cord = y0_ +p2/part * dy_;
                                    
                                        if(x2_cord > x_cord - 3 && x2_cord < x_cord + 3)
                                        {
                                            if(y2_cord > y_cord - 3 && y2_cord < y_cord +3)
                                            {
                                                r[ix].set_collision_partner(ix2);
                                                double delta_x = r[ix].get_center_x() - r[ix2].get_center_x();
                                                double delta_y = r[ix].get_center_y() - r[ix2].get_center_y();
                                                double d = Math.sqrt((delta_x*delta_x)+(delta_y*delta_y));
                                                double a = 0;
                                                if(d != 0)
                                                {
                                                    a = Math.acos(delta_x/d);
                                                }
                                                if(delta_y < 0)
                                                {
                                                    a *= -1;
                                                }
                                                double x_off = 1.3 * Math.cos(a);
                                                double y_off = 1.3 * Math.sin(a);
                                                
                                                resolve_rect_rect(ix,ix2,a,x_off,y_off);
                                               
                                                
                                                
                                                //r[ix2].set_gravity_height(r[ix2].get_gravity_height()*0.01);
                                                
                                                r[ix].set_dx(r[ix].get_dx()*0.3);
                                                r[ix2].set_dx(r[ix2].get_dx()*0.3);
                                                
                                                r[ix].set_speed(r[ix].get_speed()*0.7);
                                                r[ix2].set_speed(r[ix2].get_speed()*0.7);
                                                r[ix].set_dx(r[ix].get_dx()*0.85);
                                                r[ix2].set_dx(r[ix2].get_dx()*0.85);
                                                    
                                                
                                                /*
                                                if(r[ix].get_y1() > FRAME_WIDTH)
                                                {
                                                    double val = Math.abs(FRAME_WIDTH - r[ix].get_y1());
                                                    r[ix].set_y0(r[ix].get_y0()-val-r[ix].get_width());
                                                    r[ix2].set_y0(r[ix2].get_y0()-val-r[ix2].get_width());
                                                }
                                                if(r[ix2].get_y1() > FRAME_WIDTH)
                                                {
                                                    double val = Math.abs(FRAME_WIDTH - r[ix2].get_y1());
                                                    r[ix2].set_y0(r[ix2].get_y0()-val-r[ix2].get_width());
                                                    r[ix].set_y0(r[ix].get_y0()-val-r[ix].get_width());
                                                }
                                                */
                                                
                                                if(r[ix2].get_center_y() < r[ix].get_center_y())
                                                {
                                                    r[ix2].set_gravity_height(r[ix2].get_gravity_height()*bounce_gravity); // 0.02
                                                    r[ix].set_gravity_height(r[ix].get_gravity_height()*0.005);
                                                    r[ix2].set_dir(-1);
                                                }
                                                else
                                                {
                                                    r[ix].set_gravity_height(r[ix].get_gravity_height()*bounce_gravity);  //0.02
                                                    r[ix2].set_gravity_height(r[ix2].get_gravity_height()*0.005);
                                                    r[ix].set_dir(-1);
                                                }
                                                
                                                // a = normal angle
                                                // ac = corner angle
                                                
                                                if(r[ix].get_center_y() < r[ix2].get_center_y())
                                                {
                                                    if(r[ix].get_center_x() >= r[ix2].get_center_x())
                                                    {
                                                        //right rotate
                                                        double rot_val = 0.25 * rotate_speed*Math.abs(part_size-p);  
                                                        r[ix].set_speed(r[ix].get_speed()+rot_val);
                                                        double dx_val = 0.125 * Math.abs(part_size-p);
                                                        r[ix].set_dx(r[ix].get_dx()+dx_val);
                                                        
                                                    }
                                                    else
                                                    {
                                                        //left rotate
                                                        double rot_val = 0.25 * rotate_speed*Math.abs(p);
                                                        r[ix].set_speed(r[ix].get_speed()-rot_val);
                                                        double dx_val = 0.125 * Math.abs(p);
                                                        r[ix].set_dx(r[ix].get_dx()-dx_val);
                                                    }
                                                }
                                                else
                                                {
                                                    if(r[ix2].get_center_x() >= r[ix].get_center_x())
                                                    {
                                                        //right rotate
                                                        double rot_val = 0.25 * rotate_speed*Math.abs(part_size-p2);
                                                        r[ix2].set_speed(r[ix2].get_speed()+rot_val);
                                                        double dx_val = 0.125 * Math.abs(part_size-p2);
                                                        r[ix2].set_dx(r[ix2].get_dx()+dx_val);
                                                        
                                                    }
                                                    else
                                                    {
                                                        //left rotate
                                                        double rot_val = 0.25 * rotate_speed*Math.abs(p2);
                                                        r[ix2].set_speed(r[ix2].get_speed()-rot_val);
                                                        double dx_val = 0.125 * Math.abs(p2);
                                                        r[ix2].set_dx(r[ix2].get_dx()-dx_val);
                                                    }
                                                }
                                                
                                                
                                            }
                                        }
                                    
                                    }
                                
                                }
                        
                        }
                        
                        
                    }
        
        
        
        
                    
    }
    
    public void check_resolve()
    {
        for(int ix = 0; ix < nr_of_rects; ix++)
        {
            r[ix].set_collision_partner(-1);
        }
        
        for(int ix = 0; ix < nr_of_rects; ix++)
        {
            for(int ix2 = 0; ix2 < nr_of_rects; ix2++)
            {
                if(ix != ix2)
                {
                    double dx = r[ix].get_center_x() - r[ix2].get_center_x();
                    double dy = r[ix].get_center_y() - r[ix2].get_center_y();
                    double d = Math.sqrt((dx*dx)+(dy*dy));
                    if(d < r[ix].get_width()/2 + r[ix2].get_width()/2)
                    {
                        check_rect_rect_col(ix,ix2);
                    }
                }
            }
        }
        
        
    }
    
    public void expand()
    {
        rect_size += 10;
        Rect [] t = new Rect[rect_size];
        for(int ix = 0; ix < rect_size; ix++)
        {
            t[ix] = null;
        }
        for(int ix = 0; ix < nr_of_rects; ix++)
        {
            t[ix] = r[ix];
        }
        r = t;
    }
    
    public void add_rect(double x, double y)
    {
        if(nr_of_rects >= rect_size)
        {
            expand();
        }
        Random rand = new Random();
        
        double s = (double)(10+rand.nextInt(91))/1000.0;
        int dir = rand.nextInt(2);
        if(dir == 0)
        {
            dir = -1;
        }
        s *= dir;
        double angle = (double)(rand.nextInt(629))/100.0;
        r[nr_of_rects++] = new Rect(x,y,rect_width,s,angle);
    }
    
    
    public void init_rect()
    {
        rect_size = 10;
        nr_of_rects = 0;
        r = new Rect[rect_size];
    }
    
    public void act_perf(int delay)
    {
        ActionListener taskPerformer = new ActionListener()
                {
                    
                    public void actionPerformed(ActionEvent evt)
                    {
                        update();
                        draw();
                        
                        
                    }
                };
        
        
        
        t1 = new Timer(delay,taskPerformer);
        t1.start();
    }
    
    public void wall_collision(int rect_ix)
    {
        
            for (int corner = 0; corner < 4; corner++) {
                if (r[rect_ix].get_y_corner(corner) > FRAME_WIDTH) 
                {

                    eval_dest(corner,rect_ix);

                }

                if (r[rect_ix].get_x_corner(corner) < 0) 
                {
                    eval_dest_left_x(corner,rect_ix);
                }

                if (r[rect_ix].get_x_corner(corner) > FRAME_WIDTH) 
                {
                    eval_dest_right_x(corner,rect_ix);
                }
            }
        
        
        
        
    }
    
    public void eval_corner_points(int ix)
    {
        //Graphics g = this.jPanel1.getGraphics();
        //g.setColor(Color.blue);
        
        double angle = r[ix].get_angle()+Math.PI/4;
        double w = r[ix].get_width();
        double radius = Math.sqrt((Math.pow(w,2))/4 + (Math.pow(w,2))/4);
        
        
        double [] x_vals = new double[4];
        double [] y_vals = new double[4];
        
        int count = 0;
        
        x_vals[count] = r[ix].get_center_x() + radius*Math.cos(angle);
        y_vals[count] = r[ix].get_center_y() + radius*Math.sin(angle);
        angle += Math.PI/2;
        count++;
        
        x_vals[count] = r[ix].get_center_x() + radius*Math.cos(angle);
        y_vals[count] = r[ix].get_center_y() + radius*Math.sin(angle);
        angle += Math.PI/2;
        count++;
        
        x_vals[count] = r[ix].get_center_x() + radius*Math.cos(angle);
        y_vals[count] = r[ix].get_center_y() + radius*Math.sin(angle);
        angle += Math.PI/2;
        count++;
        
        x_vals[count] = r[ix].get_center_x() + radius*Math.cos(angle);
        y_vals[count] = r[ix].get_center_y() + radius*Math.sin(angle);
        count++;
        
        r[ix].set_x_corners(x_vals);
        r[ix].set_y_corners(y_vals);
    }
    
    public void check_straightness()
    {
        for(int ix = 0; ix < nr_of_rects; ix++)
        {
            
            if(r[ix].get_gravity_height() <= 0.1)
            {
                if(r[ix].get_dx() >= 0.1)
                {
                    r[ix].set_dx(r[ix].get_dx()*0.25);
                }
                if(r[ix].get_angle() != 0)
                {
                    if(straight_resolve)
                    {
                        r[ix].set_angle(0);
                    }
                }
            }
        }
    }
    
    public void update()
    {
        for(int ix = 0; ix < nr_of_rects; ix++)
        {
           r[ix].update();
            eval_corner_points(ix);
            wall_collision(ix); 
        }
        if(collision)
        {
            check_resolve();
            check_straightness();
        }
        
        counter++;
        if(counter % mod == 0 && rand_add)
        {
            Random rand = new Random();
            add_rect(rand.nextInt(FRAME_WIDTH),rand.nextInt(rect_width+50));
        }
        
    }
    
    
    
    public void init()
    {
        FRAME_WIDTH = this.jPanel1.getWidth();
    }
    
    public void draw()
    {
            Graphics g = this.jPanel1.getGraphics();
            g.setColor(Color.white);
            g.fillRect(0,0,FRAME_WIDTH,FRAME_WIDTH);
            
            for(int ix = 0; ix < nr_of_rects; ix++)
            {
                Graphics2D g2d = (Graphics2D)(g.create());
                g2d.setColor(Color.blue);
            
            //rotate and draw rect
                double angle = r[ix].get_angle();
                double cent_x = r[ix].get_center_x();
                double cent_y = r[ix].get_center_y();
                g2d.rotate(angle,(int)cent_x,(int)cent_y);
                int x = (int)r[ix].get_x0();
                int y = (int)r[ix].get_y0();
                int w = (int)r[ix].get_width();
                if(r[ix].get_collision_partner() != -1)
                {
                    g2d.setColor(Color.green);
                }
                else
                {
                    g2d.setColor(Color.blue);
                }
                g2d.draw(new Rectangle(x,y,w,w));
                //draw_corner_points(ix);
                g2d.dispose();
            }
            
            
            
    }
    
    public void eval_dest_left_x(int corner, int ix)
    {
        double left_angle = Math.PI;
        double delta_angle = Math.abs(left_angle - r[ix].get_angle());
        double d_val = (left_angle-delta_angle)/Math.PI/2;
        r[ix].set_dx(r[ix].get_dx()*-1);
        r[ix].set_speed(r[ix].get_speed()*-1);
        double dx = 0 - r[ix].get_x_corner(corner);
        r[ix].set_x0(dx);
    }
    
    public void eval_dest_right_x(int corner, int ix)
    {
        double delta_angle = Math.abs(r[ix].get_angle());
        double d_val = (Math.PI/2-delta_angle)/Math.PI/2;
        r[ix].set_dx(r[ix].get_dx()*-1);
        r[ix].set_speed(r[ix].get_speed()*-1);
        double dx = Math.abs(r[ix].get_x_corner(corner)-FRAME_WIDTH);
        r[ix].set_x0(FRAME_WIDTH - dx - r[ix].get_width());
        
    }
    
    public void timeDelay(long t) {
    try {
        Thread.sleep(t);
    } catch (InterruptedException e) {}
}
    
    public void eval_dest(int corner, int ix)
    {
       
        Graphics g = this.jPanel1.getGraphics();
        g.setColor(Color.red);
        double up_angle = -Math.PI/2;
        r[ix].set_dir(-1);
        
        double dx = r[ix].get_center_x() - r[ix].get_x_corner(corner);
        double dy = r[ix].get_center_y() - r[ix].get_y_corner(corner);
        double d = Math.sqrt((dx*dx)+(dy*dy));
        double a = 0;
        if(d != 0)
        {
            a = Math.acos(dx/d);
        }
        if(dy < 0)
        {
            a *= -1;
        }
        
        if(r[ix].get_y_corner(corner) <= FRAME_WIDTH+5 && r[ix].get_gravity_height() <= 0.10)
        {
            r[ix].set_gravity_height(r[ix].get_gravity_height()*0.1);
            r[ix].set_dx(0);
            r[ix].set_dir(0);
            if(straight_resolve)
            {
                r[ix].set_angle(0);
            }
            
        }
        else
        {
            int x0_ = (int)(r[ix].get_center_x() + 100 * Math.cos(-Math.PI/4));
            int y0_ = (int)(r[ix].get_center_y() + 100 * Math.sin(-Math.PI/4));
            int x1_ = (int)(r[ix].get_center_x() + 100 * Math.cos(-3*Math.PI/4));
            int y1_ = (int)(r[ix].get_center_y() + 100 * Math.sin(-3*Math.PI/4));
            //g.setColor(Color.green);
            //g.fillOval(x0_-5,y0_-5,11,11);
            //g.fillOval(x1_-5,y1_-5,11,11);
            
            int x2_ = (int)(r[ix].get_x_corner(corner) + 100 * Math.cos(a));
            int y2_ = (int)(r[ix].get_y_corner(corner) + 100 * Math.sin(a));
            //g.setColor(Color.black);
            //g.drawLine((int)r[ix].get_center_x(),(int)r[ix].get_center_y(),x2_,y2_);
            
            //timeDelay(2000);
            
            
            double up_delta = a - up_angle;
            
            if(up_delta >= 0)
            {
                double delta_angle = Math.abs(a - Math.PI/4);
                double a_val = (Math.abs(delta_angle - Math.PI/4))/Math.PI/4;
                r[ix].set_dx(r[ix].get_dx()+a_val * 35);
                r[ix].set_speed(r[ix].get_speed()+0.015);
                if(r[ix].get_speed() > 0.1)
                {
                    r[ix].set_speed(0.1);
                }
                //r.set_gravity_height(r.get_gravity_height()*93/100);
                
                double h = Math.abs(a - (-Math.PI/2));
                double h_val = 3*(Math.abs((-Math.PI/2) - h) / Math.PI/2);
                if(h_val >= 0.97)
                {
                    h_val = 0.97;
                }
                r[ix].set_gravity_height(r[ix].get_gravity_height()*h_val);
                
                
               
            }
            else if(up_delta < 0)
            {
                
                double delta_angle = Math.abs(a - (-3*Math.PI/4));
                double a_val = (Math.abs(delta_angle - (-3*Math.PI/4)))/Math.PI/4;
                r[ix].set_dx(r[ix].get_dx()-a_val * 20);
                r[ix].set_speed(r[ix].get_speed()-0.015);
                if(r[ix].get_speed() < -0.1)
                {
                    r[ix].set_speed(-0.1);
                }
                //r.set_gravity_height(r.get_gravity_height()*93/100);
                double h = Math.abs(a - (-Math.PI/2));
                double h_val = 3*(Math.abs((-Math.PI/2) - h) / Math.PI/2);
                if(h_val >= 0.97)
                {
                    h_val = 0.97;
                }
                r[ix].set_gravity_height(r[ix].get_gravity_height()*h_val);
                if(r[ix].get_gravity_height() <= 1.0)
                {
                    r[ix].set_dx(r[ix].get_dx()*0.4);
                }
            }
            
            
            if(r[ix].get_dx() >= 5)
            {
                r[ix].set_dx(5);
            }
            else if(r[ix].get_dx() < -5)
            {
                r[ix].set_dx(-5);
            }
        }
        
        
        
                
        
        
        
        
    }
    
    public void draw_corner_points(int rect_ix)
    {
        Graphics g = this.jPanel1.getGraphics();
        g.setColor(Color.red);
            for(int z = 0; z < 4; z++)
            {
                int x = (int)r[rect_ix].get_x_corner(z);
                int y = (int)r[rect_ix].get_y_corner(z);
                g.fillOval(x-1,y-1,3,3);
            }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jSlider2 = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        jSlider3 = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();
        jSlider4 = new javax.swing.JSlider();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 600));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel1MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("collision");
        jCheckBox1.setFocusable(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("reset");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("rectangle width");

        jSlider1.setMinimum(20);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jLabel2.setText("intersect solve ratio");

        jSlider2.setMaximum(1200);
        jSlider2.setMinimum(1001);
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider2StateChanged(evt);
            }
        });

        jLabel3.setText("bounce gravity");

        jSlider3.setMaximum(99);
        jSlider3.setMinimum(20);
        jSlider3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider3StateChanged(evt);
            }
        });

        jLabel4.setText("resolve val");

        jSlider4.setMaximum(5);
        jSlider4.setMinimum(-12);
        jSlider4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider4StateChanged(evt);
            }
        });

        jCheckBox2.setText("speed add");
        jCheckBox2.setFocusable(false);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jCheckBox3.setText("rand rects");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox4.setText("straight resolve");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox3))
                    .addComponent(jCheckBox4))
                .addGap(138, 138, 138))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 218, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox4)
                .addGap(73, 73, 73)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSlider4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked

            
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jPanel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseReleased
        // TODO add your handling code here:
        
        add_rect(evt.getX(),evt.getY());
    }//GEN-LAST:event_jPanel1MouseReleased

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        // TODO add your handling code here:
        if(speed_add)
        {
            add_rect(evt.getX(),evt.getY());
        }
        
        
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed


        if(jCheckBox1.isSelected())
        {
            collision = true;
        }
        else
        {
            collision = false;
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed


        nr_of_rects = 0;
        init();
        init_rect();
        t1.stop();
        act_perf(delay);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged


        rect_width = this.jSlider1.getValue();
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jSlider1StateChanged

    private void jSlider2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider2StateChanged

        
      

        solve_ratio = (double)(jSlider2.getValue())/1000.0;
      
        System.out.println(solve_ratio);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jSlider2StateChanged

    private void jSlider3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider3StateChanged


        bounce_gravity = (double)(jSlider3.getValue())/100.0;
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jSlider3StateChanged

    private void jSlider4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider4StateChanged

        
        resolve_val = (double)(this.jSlider4.getValue())/10.0;

        // TODO add your handling code here:
    }//GEN-LAST:event_jSlider4StateChanged

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed


        if(jCheckBox2.isSelected())
        {
            speed_add = true;
        }
        else
        {
            speed_add = false;
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed

        if(jCheckBox3.isSelected())
        {
            rand_add = true;
        }
        else
        {
            rand_add = false;
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed

        if(jCheckBox4.isSelected())
        {
            straight_resolve = true;
        }
        else
        {
            straight_resolve = false;
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(rect_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(rect_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(rect_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(rect_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new rect_frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JSlider jSlider3;
    private javax.swing.JSlider jSlider4;
    // End of variables declaration//GEN-END:variables
}
