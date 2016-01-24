package org.panda_lang.panda.lang.net;

import org.panda_lang.panda.core.Particle;
import org.panda_lang.panda.core.syntax.Essence;
import org.panda_lang.panda.core.syntax.Executable;
import org.panda_lang.panda.core.syntax.Method;
import org.panda_lang.panda.core.syntax.Vial;
import org.panda_lang.panda.lang.PBoolean;
import org.panda_lang.panda.lang.PNumber;
import org.panda_lang.panda.lang.PObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class PServerSocketChannel extends PObject {

    static {
        Vial vial = new Vial("ServerSocketChannel");
        vial.group("panda.network");
        vial.constructor(new Executable() {
            @Override
            public Essence run(Particle particle) {
                int port = particle.<PNumber>getValueOfFactor(0).intValue();
                try {
                    return new PServerSocketChannel(port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        vial.method(new Method("accept", new Executable() {
            @Override
            public Essence run(Particle particle) {
                SocketChannel socketChannel = null;
                try {
                    socketChannel = particle.<PServerSocketChannel>getValueOfInstance().getServerSocketChannel().accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new PSocketChannel(socketChannel);
            }
        }));
        vial.method(new Method("configureBlocking", new Executable() {
            @Override
            public Essence run(Particle particle) {
                PServerSocketChannel serverSocketChannel = particle.getValueOfInstance();
                PBoolean flag = particle.getValueOfFactor(0);
                try {
                    serverSocketChannel.getServerSocketChannel().configureBlocking(flag.getBoolean());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }));
    }

    private final ServerSocketChannel serverSocketChannel;

    public PServerSocketChannel(int port) throws IOException {
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.bind(new InetSocketAddress(port));
    }

    public ServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

    @Override
    public Object getJavaValue() {
        return getServerSocketChannel();
    }

}
