package lab4E5;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Johan Wicklund.
 * Implemented from the pseudo-code @ http://en.wikipedia.org/wiki/Quadtree
 */
public class QuadTree {
    private static final int QT_NODE_CAPACITY = 2;
    private final ArrayList<BoxModel.Particle> particles = new ArrayList();
    private final Rectangle2D boundary;
    
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;
    
    public QuadTree(Rectangle2D boundary) {
        this.boundary = boundary;
    }
    
    public ArrayList<Rectangle2D> getBoxes() {
        ArrayList<Rectangle2D> list = new ArrayList();
        
        list.add(boundary);
        if ( northWest == null) {
            return list;
        }
        list.addAll(northWest.getBoxes());
        list.addAll(northEast.getBoxes());
        list.addAll(southWest.getBoxes());
        list.addAll(southEast.getBoxes());
        
        return list;
    }
    
    public boolean insert(BoxModel.Particle p) {
        if (!boundary.contains(p.getEllipse().getBounds2D())) {
            return false;
        }
        
        if (particles.size() < QT_NODE_CAPACITY) {
            particles.add(p);
            return true;
        }
        
        if (northWest == null) {
            subdivide();
        }
        
        if (northWest.insert(p)) {
            return true;
        }
        if (northEast.insert(p)) {
            return true;
        }
        if (southWest.insert(p)) {
            return true;
        }
        if (southEast.insert(p)) {
            return true;
        }
        
        particles.add(p);
        
        return true;
    }
    
    private void subdivide() {
        double x = boundary.getX();
        double y = boundary.getY();
        double w = boundary.getWidth()/2;
        double h = boundary.getHeight()/2;
        
        northWest = new QuadTree(new Rectangle2D.Double(x, y, w, h));
        northEast = new QuadTree(new Rectangle2D.Double(x + w, y, w, h));
        southWest = new QuadTree(new Rectangle2D.Double(x, y + h, w, h));
        southEast = new QuadTree(new Rectangle2D.Double(x + w, y + h, w, h));
        
        ArrayList<BoxModel.Particle> particlesClone = particles;
        for (int i = 0; i < particlesClone.size(); i++)
        {
            if (northWest.insert(particlesClone.get(i))) {
                particles.remove(i);
                continue;
            }
            if (northEast.insert(particlesClone.get(i))) {
                particles.remove(i);
                continue;
            }
            if (southWest.insert(particlesClone.get(i))) {
                particles.remove(i);
                continue;
            }
            if (southEast.insert(particlesClone.get(i))) {
                particles.remove(i);
            }
        }
    }
    
    public boolean query(Rectangle2D range) {
        
        // Automatically abort if the range does not collide with this quad
        if (!boundary.intersects(range)) {
            return false;
        }

        // Check objects at this quad level
        for (int i = 0; i < particles.size(); i++)
        {
            if (particles.get(i).getEllipse().getBounds2D().intersects(range)) {
                return true;
            }
        }

        // Terminate here, if there are no children
        if (northWest == null) {
            return false;
        }

        if ( northWest.query(range) ) {
            return true;
        }
        if ( northEast.query(range) ) {
            return true;
        }
        if ( southWest.query(range) ) {
            return true;
        }
        if ( southEast.query(range) ) {
            return true;
        }

        return false;      
    }
}
