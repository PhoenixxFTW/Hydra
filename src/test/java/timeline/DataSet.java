package timeline;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://github.com/FDelporte/JavaOnRaspberryPi/blob/master/Chapter_04_Java/javafx-timeline/src/main/java/be/webtechie/timeline/DataSet.java">...</a>
 */
public enum DataSet {
    LANGUAGE_BIRTHDAYS(1990, 2024, Arrays.asList(
            new Entry(1990, "Ruby\n "),
            new Entry(1991, "Python\n "),
            new Entry(1995, "Java, JavaScript, PHP, Qt\n "),
            new Entry(2000, "C#\n "),
            new Entry(2009, "Go\n "),
            new Entry(2014, "Swift\n "))),
    JAVA_HISTORY(1990, 2024, Arrays.asList(
            new Entry(1995, "Initial Java platform released by\nSun Microsystems"),
            new Entry(2007, "Sun relinces under the\nGNU General Public License"),
            new Entry(2010, "Oracle acquires the Java ecosystem\nas part of Sun Microsystems deal"),
            new Entry(2014, "Oracle releases Java 8 LTS,\nthe latest free public update"),
            new Entry(2018, "Latest free public release\nof Java 8 by Oracle"))),
    // https://www.java.com/releases/
    JAVA_RELEASES(1990, 2024, Arrays.asList(
            new Entry(1995, "JDK Beta\n "),
            new Entry(1996, "JDK 1.0\n "),
            new Entry(1997, "JDK 1.1\n "),
            new Entry(1998, "J2SE 1.2\n "),
            new Entry(2000, "J2SE 1.3\n "),
            new Entry(2002, "J2SE 1.4\n "),
            new Entry(2004, "J2SE 1.5\n "),
            new Entry(2006, "Java SE 6\n "),
            new Entry(2011, "Java SE 7\n "),
            new Entry(2014, "Java SE 8 (LTS)\n "),
            new Entry(2017, "Java SE 9\n "),
            new Entry(2018, "Java SE 10 + 11 (LTS)\n "),
            new Entry(2019, "Java SE 12 + 13 + 14 (EA)\n "),
            new Entry(2020, "Java SE 14 + 15\n "),
            new Entry(2021, "Java SE 16 + 17 (LTS)\n "),
            new Entry(2022, "Java SE 18 + 19\n "),
            new Entry(2023, "Java SE 20 + 21 (LTS)\n "),
            new Entry(2024, "Java SE 22 + 23\n "),
            new Entry(2025, "Java SE 24 + 25 (LTS)\n "))),
    JAVAFX_HISTORY(2005, 2024, Arrays.asList(
            new Entry(2008, "JavaFX 1.0 released by Sun\n "),
            new Entry(2009, "JavaFX 1.1 & 1.2 mobile development, css, charts...\n "),
            new Entry(2010, "JavaFX 1.3 better performance, additional platforms\n "),
            new Entry(2011, "JavaFX 2.0 platform specific runtime, open-sourced\n "),
            new Entry(2012, "JavaFX 2.1 & 2.2 OSX, audio, video, touch events...\n "),
            new Entry(2014, "JavaFX 8 part of Java8, 3D, sensor support\n "),
            new Entry(2017, "JavaFX 9 controls and css modularized\n "),
            new Entry(2018, "JavaFX 11\n "),
            new Entry(2019, "JavaFX 12 and JavaFX 13\n "),
            new Entry(2020, "JavaFX 14 and JavaFX 15\n "),
            new Entry(2021, "JavaFX 16 and JavaFX 17\n "),
            new Entry(2022, "JavaFX 18 and JavaFX 19\n "),
            new Entry(2023, "JavaFX 20 and JavaFX 21\n "),
            new Entry(2024, "JavaFX 22 and JavaFX 23\n ")));

    private final int startYear;
    private final int endYear;
    private final Map<Integer, String> entries;

    DataSet(int startYear, int endYear, List<Entry> entries) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.entries = new HashMap<>();

        for(Entry entry: entries) {
            this.entries.put(entry.year, entry.data);
        }
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public Map<Integer, String> getEntries() {
        return this.entries;
    }
}

