package com.lyashenkogs;

import com.lyashenkogs.collections.Stack;
import com.lyashenkogs.collections.StackImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class UnitTest {
    @Test
    public void customStack() {
        //given a stack with 3 elements
        Stack stack = new StackImpl();
        stack.push("aaa");
        stack.push("bbb");
        stack.push("ccc");
        //then size should be 3
        assertEquals(3, stack.size());
        //then pop the last element
        assertEquals("ccc", stack.pop());
        assertEquals(2, stack.size());
    }

    @Test
    public void JDKstack() {
        java.util.Stack<String> stack = new java.util.Stack<>();
        //given a stack with 3 elements
        stack.push("aaa");
        stack.push("bbb");
        stack.push("ccc");
        //then size should be 3
        assertEquals(3, stack.size());
        //then pop the last element
        assertEquals("ccc", stack.pop());
        assertEquals(2, stack.size());
    }

}
