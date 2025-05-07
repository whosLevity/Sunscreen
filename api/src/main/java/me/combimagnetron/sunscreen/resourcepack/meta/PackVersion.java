package me.combimagnetron.sunscreen.resourcepack.meta;

import me.combimagnetron.sunscreen.util.Range;

public interface PackVersion {

    Range<Integer> versionRange();

    static PackVersion version(Range<Integer> versionRange) {
        return new Impl(versionRange);
    }

    record Impl(Range<Integer> versionRange) implements PackVersion {

        @Override
        public Range<Integer> versionRange() {
            return versionRange;
        }

    }

}
