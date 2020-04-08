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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package org.zetool.math.geom;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class DiscretePoint implements NDimensional<Integer> {
  private final int x;
  private final int y;

  public DiscretePoint( int x, int y ) {
    this.x = x;
    this.y = y;
  }
  
  public int getX() {
    return x;
  }
  public int getY() {
    return y;
  }

  @Override
  public int getDimension() {
    return 2;
  }
  
  @Override
  public Integer get( int i ) {
    switch( i ) {
      case 1:
        return getX();
      case 2:
        return getY();
      default:
        throw new IllegalArgumentException( "DiscretePoint has only two ids." );
    }
  }
}