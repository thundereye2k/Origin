package win.crune.origin.chat.channel;

public interface Format {

    static Format DEFAULT = () -> "{prefix}{player}{suffix}&r> {message}";

    String get();

}
