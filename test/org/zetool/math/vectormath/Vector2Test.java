package org.zetool.math.vectormath;

import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class Vector2Test {

    @Test
    public void testBinomial() throws Exception {
        Vector2 v1 = new Vector2(1, 1);
        Vector2 v2 = new Vector2(2, 1);

        Vector3 p1 = new Vector3(1, 1);
        Vector3 p2 = new Vector3(2, 1);

        System.out.println(Vector2.orientation(v1, v2));
        System.out.println(Vector3.orientation(p1, p2));

        System.out.println(Vector2.orientation(v1, new Vector2(), v2));

    }
}
