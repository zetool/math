/*
 * zet evacuation tool copyright © 2007-20 zet evacuation team
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
package org.zetool.math.vectormath;

import static java.lang.Double.doubleToLongBits;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class Vector3Test {

    @Test
    public void getReturnsCorrectValues() {
        Vector3 fixture = new Vector3(2.0, -4.0, 9.2);
        
        assertThat(doubleToLongBits(fixture.get(0)), is(equalTo(doubleToLongBits(2.0))));
        assertThat(doubleToLongBits(fixture.get(1)), is(equalTo(doubleToLongBits(-4.0))));
        assertThat(doubleToLongBits(fixture.get(2)), is(equalTo(doubleToLongBits(9.2))));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void negativeIndicesThrow() {
        new Vector3().get(-1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void outOfRangeCoordinateIndicesThrow() {
        new Vector3().get(3);
    }
}
