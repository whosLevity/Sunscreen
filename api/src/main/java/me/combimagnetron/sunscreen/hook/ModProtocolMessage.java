package me.combimagnetron.sunscreen.hook;

public interface ModProtocolMessage<R, W> {

    void read(R reader);

    void write(W writer);

}
