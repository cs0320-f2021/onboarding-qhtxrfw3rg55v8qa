package edu.brown.cs.student.main;

import java.io.*;
import java.util.Map;
import java.io.BufferedReader;
import java.util.*;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;


import static org.junit.Assert.assertEquals;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) throws IOException {new Main(args).run();}

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws IOException {
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    //Buffered reader to take input in command line for mathbot problems
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;
      while ((input = br.readLine()) != null) {
        try {
          input = input.trim();
          String[] arguments = input.split(" ");

          MathBot bot = new MathBot();
          double num1 = Double.parseDouble(arguments[1]);
          double num2 = Double.parseDouble(arguments[2]);

          //input is in prefix notation
          if (arguments[0].equals("+")) {
            double sum = bot.add(num1, num2);
            System.out.println(sum);

          } else if(arguments[0].equals("-")) {
            double difference = bot.subtract(num1, num2);
            System.out.println(difference);
          }
          System.out.println(arguments[0]);

        } catch (Exception e) {
          // e.printStackTrace();
          System.out.println("ERROR: We couldn't process your input");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: Invalid input for REPL");
    }

 // Buffered Reader to read csv files
    // used "KNN algorithm in java" as source for this
    // link: https://www.youtube.com/watch?v=8kaYD2g9MVQ
  BufferedReader stbr = new BufferedReader(new FileReader("stardata.csv"));
    String line = "";
    List<Stars> starsList = new ArrayList();

    // uses two arguments constructor for x and y vals or three
    while((line = stbr.readLine()) != null){
      String k[] = line.split(",");
      Stars stars1 = new Stars(k[0], Integer.parseInt(k[1]), Integer.parseInt(k[2]), Integer.parseInt(k[3]));
      Stars stars2 = new Stars(k[0], Integer.parseInt(k[1]), Integer.parseInt(k[2]));
      starsList.add(stars1);
      starsList.add(stars2);
    }
    // example star object, except this should actually work for every type of input in stars to return nearest
    Stars s1 = new Stars("Lonely Star" , 5, -2.24 );
    List<Stars> cs = new ArrayList<>();
    Collections.sort(cs);
    for(int i = 0; i <3; i++)
    {
      System.out.println(cs.get(i));
    }

  }


  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_0);

    // this is the directory where FreeMarker templates are placed
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");

    // when there's a server error, use ExceptionPrinter to display error on GUI
    Spark.exception(Exception.class, new ExceptionPrinter());

    // initialize FreeMarker template engine (converts .ftl templates to HTML)
    FreeMarkerEngine freeMarker = createEngine();

    // setup Spark Routes
    Spark.get("/", new MainHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      // status 500 generally means there was an internal server error
      res.status(500);

      // write stack trace to GUI
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * A handler to serve the site's main page.
   *
   * @return ModelAndView to render.
   * (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // this is a map of variables that are used in the FreeMarker template
      Map<String, Object> variables = ImmutableMap.of("title",
          "Go go GUI");

      return new ModelAndView(variables, "main.ftl");
    }
  }
}
