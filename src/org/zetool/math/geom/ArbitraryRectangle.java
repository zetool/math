/* copyright 2014-2015
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.zetool.math.geom;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ArbitraryRectangle implements Rectangle {
  private ArrayList<Point> recPoints;

  private ArbitraryRectangle( ArrayList<Point> rotated ) {
    this.recPoints = rotated;
  }

  public ArbitraryRectangle( Point nw, Point ne, Point se, Point sw ) {
    recPoints = new ArrayList<>( 4 );
    recPoints.add( nw );
    recPoints.add( ne );
    recPoints.add( sw );
    recPoints.add( se );
  }

  @Override
  public Point getCoordinate( CornerCoordinates c ) {
    return recPoints.get( c.ordinal() );
  }

  public Rectangle rotate( double angle, Point rotation ) {
    return rotate( this, angle, rotation );
  }

  public static Rectangle rotate( Rectangle r, double angle, Point rotation ) {
    ArrayList<Point> rotated = new ArrayList<>();
    final double cosTheta = Math.cos( angle * Math.PI / 180.0 );
    final double sinTheta = Math.sin( angle * Math.PI / 180.0 );

    for( Rectangle.CornerCoordinates dir : Rectangle.CornerCoordinates.values() ) {
      Point po = r.getCoordinate( dir );
      //PlanPoint rotate = new PlanPoint();
      double rotate_x = (po.getX() - rotation.getX()) * cosTheta + ((po.getY() - rotation.getY()) * (-sinTheta)) + rotation.getX();
      double rotate_y = (po.getX() - rotation.getX()) * sinTheta + ((po.getY() - rotation.getY()) * cosTheta) + rotation.getY();
      SimplePoint rotate = new SimplePoint( rotate_x, rotate_y );
      System.out.println( "Rotate point " + po + " to " + rotate );
      rotated.add( rotate );
    }

    return new ArbitraryRectangle( rotated );
  }

  @Override
  public Iterator<Point> iterator() {
    return recPoints.iterator();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder( 20 );

    sb.append( "[" );

    for( Point p : this ) {
      sb.append( p.toString() );
    }

    sb.append( ']' );

    return sb.toString();
  }

}
