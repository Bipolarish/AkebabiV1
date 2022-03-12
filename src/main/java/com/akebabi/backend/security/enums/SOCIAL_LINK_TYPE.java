package com.akebabi.backend.security.enums;

import java.util.stream.Stream;

public enum SOCIAL_LINK_TYPE {
    NON(0), FACE_BOOK(1),TWITTER(2),YOUTUBE(3),INSTAGRAM(4),MEDIUM(5),TELEGRAM(6),WHATSUP(7),TIKTOK(8);
    private int socialLinkType;

    private SOCIAL_LINK_TYPE(int socialLinkType) {
        this.socialLinkType = socialLinkType;
    }

    public int getSocialLinkType() {
        return this.socialLinkType;
    }

    public static SOCIAL_LINK_TYPE of(int socialLinkType) {
        return Stream.of(SOCIAL_LINK_TYPE.values())
                .filter(p -> p.getSocialLinkType() == socialLinkType)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
