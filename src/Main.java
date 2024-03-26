import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    /*
    Takes commands from the user and executes them. Possible commands are
    'read' - reads a file of weather data into the system
    'write' - writes weather data to a file -- overwrites the file if it exists
    'sort' - sorts weather data by the hottest to coldest average temperature
    'append' - writes weather data to a file -- appends data to the file if it exists
    'quit' - ends the program
     */
    public static void main(String[] args)
    {
        ArrayList<WeatherData> weatherData = null;
        while (true)
        {
            System.out.print("Enter A command: ");
            Scanner sc = new Scanner(System.in);

            String command = sc.next().toLowerCase();
            switch (command)
            {
                case "read":
                {
                    System.out.print("Enter the path to the file: ");
                    String path = sc.next();

                    weatherData = ReadFile(path);
                    PrintWeatherData(weatherData);
                    break;
                }
                case "sort":
                {
                    if (weatherData == null)
                    {
                        System.out.println("Please call 'read' first, before calling 'append'.");
                        break;
                    }

                    SortWeatherData(weatherData);
                    break;
                }
                case "write":
                {
                    if (weatherData == null)
                    {
                        System.out.println("Please call 'read' first, before calling 'write'.");
                        break;
                    }

                    System.out.print("Enter the path to the file: ");
                    String path = sc.next();

                    WriteFile(path, false, weatherData);
                    break;
                }
                case "append":
                {
                    if (weatherData == null)
                    {
                        System.out.println("Please call 'read' first, before calling 'append'.");
                        break;
                    }

                    System.out.print("Enter the path to the file: ");
                    String path = sc.next();

                    WriteFile(path, true, weatherData);
                    break;
                }
                case "quit":
                {
                    return;
                }
                default:
                {
                    System.out.println("Unrecognized command. Possible commands are 'read', 'write', 'append', 'sort', and 'quit'");
                    break;
                }
            }
        }
    }

    /*
    Reads a file from the given path and puts the information into an ArrayList.
    If the file does not exist, the function catches the exception, prints a message
    to the console, and return an empty (not null) array.
     */
    public static ArrayList<WeatherData> ReadFile(String path) {
        File myFile = new File(path);
        ArrayList<WeatherData> wD = new ArrayList<WeatherData>();
        try {
            Scanner scan = new Scanner(myFile);
            while (scan.hasNextLine()) {
                String c = "";
                double t = 0;
                double h = 0;
                //store file data
                String str = scan.nextLine();

                int index = str.indexOf(",");
                c = str.substring(0, index);
                str = str.substring(index + 1);
                index = str.indexOf(",");
                String temp = str.substring(0, index);
                t = Double.parseDouble(temp);
                String hu = str.substring(index + 1);
                h = Double.parseDouble(hu);
                WeatherData weather = new WeatherData(c, t, h);
                wD.add(weather);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return wD;
    }

    /*
    Prints the weather data ArrayList to the console. Each weather data item should
    go on a new line:

    [City1], [Average Temperature], [Average Humidity]
    [City2], [Average Temperature], [Average Humidity]
    ...
     */
    public static void PrintWeatherData(ArrayList<WeatherData> weatherData)
    {
        for (WeatherData weatherDatum : weatherData) {
            System.out.println(weatherDatum.toString());
        }
    }

    /*
    Sorts the given ArrayList from hottest average temperature to coldest average temperature
     */
    public static void SortWeatherData(ArrayList<WeatherData> weatherData) {
        int minIndex;
        for (int i = 0; i < weatherData.size() - 1; i++) {
            minIndex = 1;
            for (int j = i + 1; j < weatherData.size(); j++) {
                if (weatherData.get(j).getAverageTemp() > weatherData.get(minIndex).getAverageTemp()) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                helper(i, minIndex, weatherData);
            }
        }
        PrintWeatherData(weatherData);
    }
// helper method to do switchy swapping
    public static void helper (int begin, int newI, ArrayList<WeatherData> wD)
    {
        WeatherData a= wD.get(begin);
        WeatherData b= wD.get(newI);
        WeatherData c=a;
        wD.set(begin, b);
        wD.set(newI, c);
    }

    /*
    Writes the weather data information into the file with the given path.
    If shouldAppend is false, the function replaces the existing contents of the file
    (if it exists) with the new weatherData. If shouldAppend is true, the function
    adds the weather data to the end of the file.
    If the file cannot be created, the function catches the exception, prints a message
    to the console, and does not try to write to the file.
     */
    public static void WriteFile(String path, boolean shouldAppend, ArrayList<WeatherData> weatherData)
    {
        // remove the line below and implement your function here
        try {
            PrintWriter print= new PrintWriter(new FileOutputStream(new File(path), shouldAppend));
            for (int i=0; i< weatherData.size(); i++){
                print.write(weatherData.toString()+"\n");
            }
            print.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
