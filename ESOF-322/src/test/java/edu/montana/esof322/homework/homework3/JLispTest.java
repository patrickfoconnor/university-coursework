package edu.montana.esof322.homework.homework3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JLispTest {

    /*====================================================================
    // Homework 3
    //
    // Included in this directory is a simple lisp implementation.  This
    // lisp implementation supports basic math, akin to what our reverse
    // polish notation calculator did.
    //
    // The intention with this lisp is to support simple, binary addition
    // in the form:
    //
    //  (+ 1 2)
    //
    //  or
    //
    //  (+ 1 (+ 2 3))
    //
    //
    // The `+` operator can take two and only two arguments, and must be
    // enclosed in parenthesis to be valid.
    //
    // This implementation is buggy.  Your assignment is to create four (4)
    // tests showing *different* bugs within the simple language.
    //
    //====================================================================*/

    @Test
    // Test 1/5
    public void testSurroundChars() {
        JLisp lisp = new JLisp();
        try {
            Integer integer = lisp.eval("+ 1 [+ 2 3]");
            fail("expected an IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Test 2/5
    @Test
    public void testTooManyValues() {
        JLisp lisp = new JLisp();
        try {
            Integer integer = lisp.eval("+ 1 1 1");
            fail("expected an IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            // pass
        }
    }

    // Test 3/5: Very similar to above so I added an extra
    @Test
    public void testNotEnoughValues() {
        JLisp lisp = new JLisp();
        try {
            Integer integer = lisp.eval("+ ");
            fail("expected an IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testBadNumbers() {
        JLisp lisp = new JLisp();
        try {
            Integer integer = lisp.eval("+ 1 f");
            fail("expected an IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testIncorrectOrder() {
        JLisp lisp = new JLisp();
        try {
            Integer integer = lisp.eval("1 1 +");
            fail("expected an IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testIllegalOperation() {
        JLisp lisp = new JLisp();
        try {
            Integer integer = lisp.eval("* 1 1");
            fail("expected an IllegalArgumentException");
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    
}
