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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.zetool.math.geom;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <E>
 */
public interface NDimensional<E extends Number> {

    /**
     * Returns the dimension of the {@literal n}-dimensional tuple.
     *
     * @return
     */
    int getDimension();

    /**
     * Returns the value of a given coordinate {@code index}. The coordinate is in the range {@code 0} to
     * {@link #getDimension() getDimension() - 1}.
     *
     * @param index the index of the value that is to be returned
     * @return the value of the coordinate with the given {@code index}
     * @throws IllegalArgumentException if the index is out of range, i.e. negative or larger than the dimension
     */
    E get(int index);

    /**
     * Returns a representation of the {@literal n}-dimensional tuple in form {@literal (x_1, x_2, ..., x_n)}.
     *
     * @return string representation as {@literal (x_1, x_2, ..., x_n)}
     */
    default String parseableString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < getDimension(); ++i) {
            sb.append(get(i)).append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), ")");
        return sb.toString();
    }
}
