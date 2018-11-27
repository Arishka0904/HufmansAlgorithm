package huffmansAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class CompressionResult {
    String bits;
    List<Bit> bitResult;

    public CompressionResult(String bits) {
        this.bits = bits;
    }

    public void setBits(String bits) {
        this.bits = bits;
    }

    public void setBitResult(List<Bit> bitResult) {
        this.bitResult = bitResult;
    }

    public List<Bit> getBitResult() {
        return bitResult;
    }
}

     class ResultBuilder {
        private String bits;
        List<Bit> bitR = new ArrayList<>();

        public ResultBuilder addBit(Bit bit) {
            bitR.add(bit);
            return this;
        }

        public CompressionResult build() {
            CompressionResult result = new CompressionResult(bits);
            result.setBitResult(bitR);
            return result;

        }
    }


