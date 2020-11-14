package sudoku.solver.util;

import com.google.common.io.Resources;
import sudoku.solver.core.Grid;

import java.io.*;
import java.net.URL;

public class CsvLoader {

    public static Grid load(String resource) {

        try (InputStream stream = Resources
                .getResource(resource)
                .openStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(stream))
        ) {
            Grid result = new Grid();

            for (int y = 8; y >= 0; y--) {

                int x = 0;
                String line = reader
                        .readLine()
                        .strip();
                char[] chars = line.toCharArray();
                for (char character : chars) {
                    switch (character) {
                        case ',':
                            x++;
                            break;
                        case '*':
                            break;
                        default:
                            result.setValue(x, y, Integer.parseInt("" + character));
                            break;
                    }

                }

            }


            return result;

        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }

    }

}
