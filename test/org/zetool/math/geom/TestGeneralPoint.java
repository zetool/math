/* zet evacuation tool copyright (c) 2007-14 zet evacuation team
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

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class TestGeneralPoint {
  
  @Test
  public void testParsebleString() {
    NDimensional<Integer> testPoint = new NDimensional<Integer>() {

      private final Integer[] numbers = new Integer[]{1, 2, 3};
      
      @Override
      public int getDimension() {
        return 3;
      }

      @Override
      public Integer get( int i ) {
        return numbers[i];
      }
    };
    assertEquals( "Parseable representation invalid", "(1,2,3)", testPoint.parseableString() );
  }
}
