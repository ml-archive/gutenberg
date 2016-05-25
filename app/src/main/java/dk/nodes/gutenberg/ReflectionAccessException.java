package dk.nodes.gutenberg;

/**
 * Created by Mario on 25/05/2016.
 */

public class ReflectionAccessException extends RuntimeException {
    @Override
    public String getMessage() {
        return ("Field could not be accesse");
    }

}
