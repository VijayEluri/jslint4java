package com.googlecode.jslint4java;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ScriptableObject;

/**
 * @author dom
 * @version $Id$
 */
public class UtilTest {

    private final ContextFactory contextFactory = new ContextFactory();
    private final Context cx = contextFactory.enterContext();
    private ScriptableObject scope = null;

    @Before
    public void setUp() throws Exception {
        cx.setLanguageVersion(Context.VERSION_1_5);
        scope = cx.initStandardObjects();
    }

    @After
    public void tearDown() throws Exception {
        Context.exit();
    }

    @Test
    public void testIntValueFromJava() throws Exception {
        scope.put("foo", scope, 42);
        assertThat(Util.intValue("foo", scope), is(42));
    }

    @Test
    public void testIntValueFromJavaScript() throws Exception {
        cx.evaluateString(scope, "var foo = 42", "-", 1, null);
        assertThat(Util.intValue("foo", scope), is(42));
    }

    @Test
    public void testIntValueNullScope() throws Exception {
        assertThat(Util.intValue("foo", null), is(0));
    }

    @Test
    public void testIntValueOfUndefined() {
        assertThat(Util.intValue("foo", scope), is(0));
    }

    @Test
    public void testListValueString() throws Exception {
        cx.evaluateString(scope, "var l = ['a','b','c'];", "-", 1, null);
        List<String> l = Util.listValue("l", String.class, scope);
        assertThat(l.size(), is(3));
        assertThat(l.get(0), is("a"));
        assertThat(l.get(1), is("b"));
        assertThat(l.get(2), is("c"));
    }

    @Test
    public void testListValueInteger() throws Exception {
        cx.evaluateString(scope, "var l = [9,8,7];", "-", 1, null);
        List<Integer> l = Util.listValue("l", Integer.class, scope);
        assertThat(l.size(), is(3));
        assertThat(l.get(0), is(9));
        assertThat(l.get(1), is(8));
        assertThat(l.get(2), is(7));
    }

    @Test
    public void testReaderToString() throws Exception {
        StringReader reader = new StringReader("foo bar");
        assertThat(Util.readerToString(reader), is("foo bar"));
    }

    @Test
    public void testStringValueFromJava() throws Exception {
        scope.put("foo", scope, "bar");
        assertThat(Util.stringValue("foo", scope), is("bar"));
    }

    @Test
    public void testStringValueFromJavaScript() throws Exception {
        cx.evaluateString(scope, "var foo = 'bar'", "-", 1, null);
        assertThat(Util.stringValue("foo", scope), is("bar"));
    }

    @Test
    public void testStringValueNullScope() throws Exception {
        assertThat(Util.stringValue("foo", null), is(nullValue()));
    }

    @Test
    public void testStringValueOfUndefined() {
        assertThat(Util.stringValue("foo", scope), is(nullValue()));
    }
}
