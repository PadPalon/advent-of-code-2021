package ch.neukom.advent2021.day16;

import ch.neukom.advent2021.helper.CharStream;

import java.util.*;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.*;

public record PacketReader(Deque<Character> bits) {
    public List<Packet> readPackets() {
        List<Packet> packets = new ArrayList<>();
        while (bits.contains('1')) {
            packets.add(readPacket());
        }
        return packets;
    }

    private Packet readPacket() {
        Integer version = readVersion();
        PacketType type = readType();
        if (type.equals(PacketType.LITERAL)) {
            return readLiteralPacket(version);
        } else {
            return readOperatorPacket(version, type);
        }
    }

    private Integer readVersion() {
        return getBits(3).map(this::parseBinary).map(Long::intValue).orElseThrow();
    }

    private PacketType readType() {
        return getBits(3)
                .map(this::parseBinary)
                .map(Long::intValue)
                .map(id -> PacketType.values()[id])
                .orElseThrow();
    }

    private LiteralPacket readLiteralPacket(Integer version) {
        String currentGroup;
        StringBuilder binaryString = new StringBuilder();
        do {
            currentGroup = getBits(5).orElseThrow();
            binaryString.append(currentGroup.substring(1));
        } while (currentGroup.startsWith("1"));
        long value = parseBinary(binaryString.toString());
        return new LiteralPacket(version, value);
    }

    private OperatorPacket readOperatorPacket(Integer version, PacketType type) {
        boolean definedByBits = getBits(1).map(i -> i.equals("0")).orElseThrow();
        if (definedByBits) {
            Long bitLength = getBits(15).map(this::parseBinary).orElseThrow();
            return getBits(bitLength)
                    .map(CharStream::of)
                    .map(subBits -> subBits.collect(toCollection(ArrayDeque::new)))
                    .map(PacketReader::new)
                    .map(PacketReader::readPackets)
                    .map(subpackets -> new OperatorPacket(version, type, subpackets))
                    .orElseThrow();
        } else {
            Long packetCount = getBits(11).map(this::parseBinary).orElseThrow();
            return LongStream.range(0, packetCount)
                    .mapToObj(i -> readPacket())
                    .collect(collectingAndThen(toList(), subpackets -> new OperatorPacket(version, type, subpackets)));
        }
    }

    private long parseBinary(String binaryString) {
        return Long.parseLong(binaryString, 2);
    }

    private Optional<String> getBits(long count) {
        if (bits.size() < count) {
            System.err.println("Not enough bits");
        }
        return LongStream.range(0, count)
                .mapToObj(i -> bits.pop())
                .map(Object::toString)
                .collect(collectingAndThen(joining(), Optional::of));
    }
}
