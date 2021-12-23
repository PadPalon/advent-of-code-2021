package ch.neukom.advent2021.day16;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public record OperatorPacket(Integer version,
                             PacketType type,
                             List<Packet> subpackets) implements Packet {
    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public Long getValue() {
        return subpackets.stream().map(Packet::getValue).collect(collectingAndThen(toList(), type.getOperator())).orElseThrow();
    }

    public List<Packet> getSubpackets() {
        return subpackets;
    }
}
