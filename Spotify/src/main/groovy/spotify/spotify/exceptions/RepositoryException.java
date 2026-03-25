package spotify.spotify.exceptions;

public class RepositoryException extends RuntimeException {
    public RepositoryException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
