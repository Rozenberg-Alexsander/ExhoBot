package bot.util;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import java.io.InputStream;

public class ResourceFileReader {
    private static final ClassLoader classLoader = ResourceFileReader.class.getClassLoader();

    public static InputFile getInputFileFromResources() {
        InputStream inputStream = classLoader.getResourceAsStream("song.txt");
        if (inputStream == null) {
            throw new IllegalArgumentException("File is not found to " + inputStream.toString());
        }
        return new InputFile(inputStream, "song.txt");
    }

    public static InputFile getInputImageFromResources() {
        InputStream inputStream = classLoader.getResourceAsStream("IMG_20220814_125555.jpg");
        if (inputStream == null) {
            throw new IllegalArgumentException("File is not found to " + inputStream);
        }
        return new InputFile(inputStream, "IMG_20220814_125555.jpg");
    }

    public static InputFile getInputVideoFromResources() {
        InputStream inputStream = classLoader.getResourceAsStream("2017-08-29 12.21.23.mp4");
        if (inputStream == null) {
            throw new IllegalArgumentException("File is not found to " + inputStream);
        }
        return new InputFile(inputStream, "2017-08-29 12.21.23.mp4");
    }

    public static InputFile getInputAudioFromResources() {
        InputStream inputStream = classLoader.getResourceAsStream("Rammstein - Du Hast.mp3");
        if (inputStream == null) {
            throw new IllegalArgumentException("File is not found to " + inputStream);
        }
        return new InputFile(inputStream, "Rammstein - Du Hast.mp3");
    }
}
