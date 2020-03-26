package com.wgfxer.projectpurpose.domain;

public abstract class PositionKeeper {
    protected int position;

    public void updatePosition(int position) {
        this.position = position;
    }
}
