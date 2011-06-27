package simpledemo.client;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportClosure;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExporterUtil;
import org.timepedia.exporter.client.NoExport;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class SimpleDemo implements EntryPoint {
  
  public void onModuleLoad() {
    ExporterUtil.exportAll();
    print(Window.Navigator.getUserAgent());
    runJsTests();
  }
  
  public static <T> void print(T s) {
    RootPanel.get().add(new Label(s.toString()));
  }
  
  public static <T> void mAssertEqual(T a, T b) {
    if (a.toString().equals(b.toString())) {
      print("OK -> " + b);
    } else {
      print("ERROR -> " + a.toString() + " <=> " + b.toString() + " ["
          + a.getClass().getName() + ", " + b.getClass().getName() + "]");
    }
  }

  @ExportPackage("gwt")
  @Export
  public static class HelloAbstract implements Exportable {
    public String helloAbstract(){
      return this.getClass().getName();
    }
    
    @NoExport
    public String noHelloAbstract(){
      return this.getClass().getName();
    }
  }
  
  @ExportPackage("gwt")
  @Export
  public static class HelloClass extends HelloAbstract implements Exportable {
    public String hello(){
      return this.getClass().getName();
    }

    public static String[] test0(char c, byte b, int i, double d, float f, String s, Object o, Exportable e) {
      String[] ret = new String[8];
      ret[0] = "" + (int)c;
      ret[1] = "" + b;
      ret[2] = "" + i;
      ret[3] = "" + d;
      ret[4] = "" + f;
      ret[5] = "" + s;
      ret[6] = "" + o.getClass().getName();
      ret[7] = "" + e.getClass().getName();
      return ret;
    }

    public static int[] test1(char[]c, byte[] b, int[] i, double[]d, float[] f, long[] l, String[] s, Object[] o, Exportable[] e) {
      int[] ret = new int[9];
      ret[0] = c.length;
      ret[1] = b.length;
      ret[2] = i.length;
      ret[3] = d.length;
      ret[4] = f.length;
      ret[5] = l.length;
      ret[6] = s.length;
      ret[7] = o.length;
      ret[8] = e.length;
      return ret;
    }

    public static long[] test2() {
      return new long[]{1l,2l};
    }
    
    public static Exportable[] test3() {
      return new HelloClass[]{new HelloClass()};
    }
    
    public static char test4() {
      return 1;
    }
    
    public static byte test5() {
      return 2;
    }
    
    public static int test6() {
      return 3;
    }
    
    public static double test7() {
      return 4;
    }
    
    public static float test8() {
      return 5;
    }
    
    public static String test9() {
      return "A";
    }
    
    public static JavaScriptObject test10() {
      return new Label("").getElement();
    }
    
    public static long test11() {
      return 6;
    }
    
    public static String test12(long l){
      return "" + l;
    }
    
    public static long test13(long l, double d) {
      return l + (long)d;
    }
    
    public long test14(long l, double d, long[] a) {
      return l + (long)d + a[0];
    }
    
    public long[] test15(long[] a) {
      return a;
    }

    public static String test16(long l) {
      return "" + l;
    }

    public static long test16(long a, long b) {
      return (a + b);
    }

    public String test17(long l) {
      return "" + l;
    }

    public long test17(long a, long b) {
      return (a + b);
    }
  }
  
  @ExportPackage("gwt")
  @Export
  public static class Foo implements Exportable {
    
    String n = "foo";
    public Foo() {
    }
    public Foo(String id) {
      n= id;
    }
    public Foo(String id, String a) {
      n= id + a;
    }
    public String toString(){
      return n;
    }
    public String toString(String a){
      return n + ">" + a;
    }
    
    @ExportClosure
    public interface Closure extends Exportable {
      public String execute(String par1, String par2);
    }
    
    public String executeJsClosure(Closure closure){
       return closure.execute("Hello", "Friend");
    }
  }
  
  public native JavaScriptObject runJsTests() /*-{
    p = function(a, b) {@simpledemo.client.SimpleDemo::mAssertEqual(Ljava/lang/Object;Ljava/lang/Object;)(a, b);}
    
    var v1 = new $wnd.gwt.SimpleDemo.Foo();
    p("foo", v1);
    var v2 = new $wnd.gwt.SimpleDemo.Foo("foo2");
    p("foo2", v2);
    var v3 = new $wnd.gwt.SimpleDemo.Foo("foo3", "bbb");
    p("foo3bbb", v3);
    p("foo3bbb>ccc", v3.toString("ccc"));
    p("Hello,Friend", v3.executeJsClosure(function(arg1, arg2) {
        return arg1 + "," + arg2;
    }));
    
    var h = new $wnd.gwt.SimpleDemo.HelloClass();
    p("1,2,3,4.0,5.0,S,com.google.gwt.core.client.JavaScriptObject$,simpledemo.client.SimpleDemo$HelloClass", $wnd.gwt.SimpleDemo.HelloClass.test0(1, 2, 3, 4, 5, "S", window.document, h));
    p("1,1,1,1,1,2,2,2,1", $wnd.gwt.SimpleDemo.HelloClass.test1([0], [0], [0], [0], [0], [1,2], ["a","b"], [window,document], [h]));
    p("1,2", $wnd.gwt.SimpleDemo.HelloClass.test2());
    p("simpledemo.client.SimpleDemo$HelloClass", $wnd.gwt.SimpleDemo.HelloClass.test3()[0].hello());
    p("simpledemo.client.SimpleDemo$HelloClass", $wnd.gwt.SimpleDemo.HelloClass.test3()[0].helloAbstract());
    p("undefined", "" + $wnd.gwt.SimpleDemo.HelloClass.test3()[0].noHelloAbstract);
    
    p("1", "" + $wnd.gwt.SimpleDemo.HelloClass.test4(1, "A"));
    p("2", "" + $wnd.gwt.SimpleDemo.HelloClass.test5());
    p("3", "" + $wnd.gwt.SimpleDemo.HelloClass.test6());
    p("4", "" + $wnd.gwt.SimpleDemo.HelloClass.test7());
    p("5", "" + $wnd.gwt.SimpleDemo.HelloClass.test8());
    p("A", "" + $wnd.gwt.SimpleDemo.HelloClass.test9());
    p("div", "" + $wnd.gwt.SimpleDemo.HelloClass.test10().tagName.toLowerCase());
    p("6", "" + $wnd.gwt.SimpleDemo.HelloClass.test11());
    p("1", "" + $wnd.gwt.SimpleDemo.HelloClass.test12(1));
    p("5", "" + $wnd.gwt.SimpleDemo.HelloClass.test13(2, 3));
    p("4", "" + $wnd.gwt.SimpleDemo.HelloClass.test16(4));
    p("14", "" + $wnd.gwt.SimpleDemo.HelloClass.test16(4, 10));
    
    var h = new $wnd.gwt.SimpleDemo.HelloClass();
    p("102", "" + h.test14(1, 1, [100]));
    p("100,200", "" + h.test15([100, 200]));
    p("5", "" + h.test17(5));
    p("15", "" + h.test17(5,10));
    
  }-*/;
  
}