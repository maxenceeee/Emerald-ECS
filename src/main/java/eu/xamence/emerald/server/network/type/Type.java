package eu.xamence.emerald.server.network.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Type<T> {

    T read(DataInputStream stream) throws IOException;

    void write(DataOutputStream stream, T value) throws IOException;
}
