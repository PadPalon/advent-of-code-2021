package ch.neukom.advent2021.day16;

import ch.neukom.advent2021.helper.InputResourceReader;

import java.io.IOException;
import java.util.Deque;
import java.util.List;

public class Puzzle2 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            Deque<Character> bits = Util.readBits(reader.getFirstLine());
            List<Packet> packets = new PacketReader(bits).readPackets();
            assert packets.size() == 1;
            System.out.printf("The value of the packet is %s", packets.get(0).getValue());
        }
    }
}

