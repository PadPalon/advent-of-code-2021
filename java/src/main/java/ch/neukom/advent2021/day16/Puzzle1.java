package ch.neukom.advent2021.day16;

import ch.neukom.advent2021.helper.InputResourceReader;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Puzzle1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            Deque<Character> bits = Util.readBits(reader.getFirstLine());
            List<Packet> packets = new PacketReader(bits).readPackets();

            Deque<Packet> packetsToHandle = new ArrayDeque<>(packets);
            long versionCount = 0;
            while (!packetsToHandle.isEmpty()) {
                Packet currentPacket = packetsToHandle.pop();
                versionCount += currentPacket.getVersion();
                if (currentPacket instanceof OperatorPacket operatorPacket) {
                    packetsToHandle.addAll(operatorPacket.getSubpackets());
                }
            }
            System.out.printf("The sum of all versions is %s", versionCount);
        }
    }
}

