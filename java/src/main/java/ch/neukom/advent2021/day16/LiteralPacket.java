package ch.neukom.advent2021.day16;

public record LiteralPacket(Integer version, long value) implements Packet {
    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public Long getValue() {
        return value;
    }
}
