package io.github.sparky983.diorite.net;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

/**
 * A utility class for network related mess.
 *
 * @author Sparky983
 */
@ApiStatus.Internal
public final class Networking {

    /**
     * The maximum valid port number (max unsigned 16-bit integer).
     */
    public static final int MAX_PORT = 65_535;

    /**
     * The minimum valid port number.
     */
    public static final int MIN_PORT = 1;

    private Networking() {

    }

    /**
     * Checks whether the specified port number is valid. Valid ports range from {@link #MAX_PORT}
     * to {@link #MIN_PORT}.
     *
     * @param port The port to check for validity.
     * @return Whether the port is valid.
     */
    @Contract(pure = true)
    public static boolean isValidPort(final int port) {

        return MAX_PORT >= port &&
                port >= MIN_PORT;
    }
}
